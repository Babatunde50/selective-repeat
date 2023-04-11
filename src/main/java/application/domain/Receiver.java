package application.domain;

import java.util.function.Consumer;
import java.util.Random;
import org.fusesource.jansi.Ansi;

public class Receiver {
	private Consumer<Integer> onAcknowledgmentReceived;

	private final double lossProbability;
	private final Random random;

	public Receiver(Consumer<Integer> onAcknowledgmentReceived, double lossProbability) {
		this.onAcknowledgmentReceived = onAcknowledgmentReceived;
		this.lossProbability = lossProbability;
		this.random = new Random();
	}

	public void receiveFrame(Frame frame) {
		Logger.printMessage("Receiver received frame with sequence number: " + frame.getSequenceNumber()
				+ "and payload: " + frame.getPayload(), Ansi.Color.BLUE);

		sendAcknowledgment(frame.getSequenceNumber());
	}

	private void sendAcknowledgment(int sequenceNumber) {
		// Check if the acknowledgment should be sent or not
		if (random.nextDouble() >= lossProbability) {
			onAcknowledgmentReceived.accept(sequenceNumber);
		} else {
			// Simulate packet loss by not sending the acknowledgment
			Logger.printMessage("Acknowledgment for sequence number " + sequenceNumber + " was lost", Ansi.Color.RED);
		}
	}
}
