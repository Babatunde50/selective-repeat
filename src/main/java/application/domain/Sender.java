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
	private int maxFrameToSend;
	private int totalFramesSent = 0;

	private Receiver receiver;
	private ScheduledExecutorService retransmissionTimer;

	public Sender(int windowSize, Receiver receiver, int maxFrameToSend) {
		this.windowSize = windowSize;
		this.buffer = new CircularBuffer(windowSize);
		this.currentSequenceNumber = 0;
		this.receiver = receiver;
		this.maxFrameToSend = maxFrameToSend;

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

		Logger.logBuffer(buffer, windowSize, currentSequenceNumber, maxFrameToSend, totalFramesSent);
		frame.setRetransmissionTimer(System.currentTimeMillis()); // Record the send time
		receiver.receiveFrame(frame);
		totalFramesSent += 1;
	}

	public void resendFrame(int sequenceNumber) {
		Frame frame = buffer.getFrameBySequenceNumber(sequenceNumber);
		if (frame != null) {
			Logger.printMessage("Resending frame with sequence number: " + frame.getSequenceNumber()
					+ " and payload: " + frame.getPayload(), Ansi.Color.BLUE);

			Random random = new Random();

			Logger.logBuffer(buffer, windowSize, currentSequenceNumber, maxFrameToSend, totalFramesSent);
			frame.setRetransmissionTimer(System.currentTimeMillis()); // Record the send time
			int randomNumber = random.nextInt(100) + 1;
			if (randomNumber > 70) {
				frame.setCorrupted(true);
			} else {
				frame.setCorrupted(false);
			}
			receiver.receiveFrame(frame);
		}
	}

	public void receiveAcknowledgment(int sequenceNumber) {
		Frame frame = buffer.getFrameBySequenceNumber(sequenceNumber);
		if (frame != null) {
			frame.setAcknowledged(true);
			Logger.printMessage("Acknowledgment received for sequence number: " + sequenceNumber, Ansi.Color.GREEN);
			Logger.logBuffer(buffer, windowSize, currentSequenceNumber, maxFrameToSend, totalFramesSent);
		}
	}

	public void run() {
		Random random = new Random();

		try {
			while (totalFramesSent <= maxFrameToSend) {
				if (!buffer.isFull()) {
					// generate a random number between 1 and 100 to simulate the loss probability
					int randomNumber = random.nextInt(100) + 1;
					Frame frame;
					if (randomNumber > 70) {
						frame = new Frame(currentSequenceNumber, "Payload " + currentSequenceNumber, true);
					} else {
						frame = new Frame(currentSequenceNumber, "Payload " + currentSequenceNumber, false);
					}

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
				Thread.sleep(random.nextInt(1000) + 1);
			}
		} catch (InterruptedException e) {

			Logger.printMessage("Sender interrupted", Ansi.Color.RED);
		}
	}

}