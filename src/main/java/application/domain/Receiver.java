package application.domain;

import java.util.function.Consumer;
import java.util.Random;
import org.fusesource.jansi.Ansi;

public class Receiver {
	private Consumer<Integer> onAcknowledgmentReceived;

	public Receiver(Consumer<Integer> onAcknowledgmentReceived) {
		this.onAcknowledgmentReceived = onAcknowledgmentReceived;

	}

	public void receiveFrame(Frame frame) {
		Logger.printMessage("Receiver received frame with sequence number: " + frame.getSequenceNumber()
				+ "and payload: " + frame.getPayload(), Ansi.Color.BLUE);

		Random random = new Random();

		try {
			Thread.sleep(random.nextInt(1000) + 1);
			sendAcknowledgment(frame.getSequenceNumber(), frame.isCorrupted());
		} catch (InterruptedException e) {

			Logger.printMessage("Receiver interrupted " + e.getMessage(), Ansi.Color.RED);
			// Restore the interrupted status
			Thread.currentThread().interrupt();

		}

	}

	private void sendAcknowledgment(int sequenceNumber, boolean isCorrupted) {
		// Check if the frame is not corrupted
		if (!isCorrupted) {
			onAcknowledgmentReceived.accept(sequenceNumber);
		} else {
			// Simulate packet loss by not sending the acknowledgment
			Logger.printMessage("Acknowledgment for sequence number " + sequenceNumber + " was lost", Ansi.Color.RED);
		}
	}
}
