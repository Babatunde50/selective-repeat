package application.main;

import application.domain.Receiver;
import application.domain.Sender;

public class Main {

	private static class SenderHolder {
		private Sender sender;
	}

	public static void main(String[] args) {
		int windowSize = 4;
		int maxFriameToSend = 100;

		SenderHolder senderHolder = new SenderHolder();

		// Create the Receiver instance and define its behavior
		Receiver receiver = new Receiver(sequenceNumber -> {
			// Handle received acknowledgments
			if (senderHolder.sender != null) {
				senderHolder.sender.receiveAcknowledgment(sequenceNumber);
			}
		});

		// Initialize the Sender instance and store it in the SenderHolder
		senderHolder.sender = new Sender(windowSize, receiver, maxFriameToSend);

		// Start the Sender
		senderHolder.sender.run();
	}

}
