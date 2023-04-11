package application.domain;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.fusesource.jansi.Ansi;

public class Sender {
	private CircularBuffer buffer;
	private int windowSize;
	private int currentSequenceNumber; // should be 3 bits 0-7

	private Receiver receiver;
	private ScheduledExecutorService retransmissionTimer;

	public Sender(int windowSize, Receiver receiver) {
		this.windowSize = windowSize;
		this.buffer = new CircularBuffer(windowSize);
		this.currentSequenceNumber = 0;
		this.receiver = receiver;

		this.retransmissionTimer = Executors.newSingleThreadScheduledExecutor();
		this.retransmissionTimer.scheduleAtFixedRate(this::checkForExpiredTimers, 100, 100, TimeUnit.MILLISECONDS);
	}

	private void checkForExpiredTimers() {
		long currentTime = System.currentTimeMillis();

		for (int i = 0; i < buffer.size(); i++) {
			Frame frame = buffer.getFrameByIndex(i);
			if (!frame.isAcknowledged() && currentTime - frame.getRetransmissionTimer() > 1000) { // 1000 ms timeout
				resendFrame(frame.getSequenceNumber());
			}
		}
	}

	public void sendFrame(Frame frame) {
		Logger.printMessage("Sending frame with sequence number " + frame.getSequenceNumber() + " and payload: "
				+ frame.getPayload(), Ansi.Color.BLUE);

		Logger.logBuffer(buffer, windowSize, currentSequenceNumber);
		frame.setRetransmissionTimer(System.currentTimeMillis()); // Record the send time
		receiver.receiveFrame(frame);
	}

	public void resendFrame(int sequenceNumber) {
		Frame frame = buffer.getFrameBySequenceNumber(sequenceNumber);
		if (frame != null) {
			Logger.printMessage("Resending frame with sequence number: " + frame.getSequenceNumber()
					+ " and payload: " + frame.getPayload(), Ansi.Color.BLUE);

			Logger.logBuffer(buffer, windowSize, currentSequenceNumber);
			frame.setRetransmissionTimer(System.currentTimeMillis()); // Record the send time
			receiver.receiveFrame(frame);
		}
	}

	public void receiveAcknowledgment(int sequenceNumber) {
		Frame frame = buffer.getFrameBySequenceNumber(sequenceNumber);
		if (frame != null) {
			frame.setAcknowledged(true);
			Logger.printMessage("Acknowledgment received for sequence number: " + sequenceNumber, Ansi.Color.GREEN);
			Logger.logBuffer(buffer, windowSize, currentSequenceNumber);
		}
	}

	public void run() {
		Random random = new Random();

		try {
			while (true) {
				if (!buffer.isFull()) {
					Frame frame = new Frame(currentSequenceNumber, "Payload " + currentSequenceNumber);
					buffer.addFrame(frame);
					sendFrame(frame);
					currentSequenceNumber = (currentSequenceNumber + 1) % 8;
				} else {
					// Simulate receiving acknowledgments
					int receivedSequenceNumber = random.nextInt(windowSize);
					receiveAcknowledgment(receivedSequenceNumber);

					// Check for unacknowledged frames and resend them
					Optional<Frame> unacknowledgedFrame = buffer.getFirstUnacknowledgedFrame();
					if (unacknowledgedFrame.isPresent()) {
						resendFrame(unacknowledgedFrame.get().getSequenceNumber());
					}

					// Remove acknowledged frames from the buffer
					Frame firstFrame = buffer.getFrameBySequenceNumber((currentSequenceNumber - windowSize + 8) % 8);
					if (firstFrame != null && firstFrame.isAcknowledged()) {
						buffer.removeFrame(firstFrame.getSequenceNumber());
					}
				}

				// Add a delay to slow down the execution
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {

			Logger.printMessage("Sender interrupted", Ansi.Color.RED);
		}
	}

}