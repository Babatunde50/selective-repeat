package application.domain.network;

import application.domain.Frame;

/**
 * Represents a network
 * for the communication between a sender and a receiver.
 * 
 * @author Adriana R.F.
 * @version 1.0 Single
 */
public class Network {

	// Uni-directional communication
	private Sender sender;
	private Receiver receiver;
	// Sending frame
	private Frame toBeSent;
	
	public Network(Sender sender, Receiver receiver) {
		if (sender == null || receiver == null)
			throw new IllegalArgumentException("[NETWORK] ERROR: There must be both sender and receiver.");
		this.sender = sender;
		this.receiver = receiver;
	}
	
	/**
	 * Processes the frame that is sent by a sender,
	 * in order for it to be received by a receiver.
	 * 
	 * @param sent frame
	 */
	public void process(Frame frame) {
		// Do whatever with the frame
		if (frame == null)
			throw new IllegalArgumentException("[NETWORK] ERROR: Frame to process is null.");
		this.toBeSent = frame;
	}
	
	/**
	 * Frame is effectively received by the receiver.
	 * 
	 * @param sent frame
	 * @throws InterruptedException 
	 */
	public void receive() throws InterruptedException {
		receiver.receive(this.toBeSent);
	}

	
	
}
