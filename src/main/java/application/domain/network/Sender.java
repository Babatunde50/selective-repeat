package application.domain.network;

import application.domain.Frame;
import application.util.Util;

/**
 * Represents a sender, that will send
 * a frame to a given receiver over a network.
 * 
 * @author Adriana R.F.
 * @version 1.0 Single
 */
public class Sender {
	
	// Max. number of frames to be sent
	private int max_allowed;
	
	/*
	 * --------------- CONSTRUCTOR
	 */
	
	public Sender(int max_allowed, Receiver receiver) {
		this.max_allowed = max_allowed;
	}
	
	/*
	 * --------------- PUBLIC METHODS
	 */
	
	/**
	 * A sender sends a given newly-generated frame over the network,
	 * and it will subsequently arrive to a given receiver.
	 * 
	 * @param network itself, that will process said frame
	 * @throws InterruptedException // for waiting thread
	 */
	public void send(Network network) throws InterruptedException {
		
		// Send frame to network
		network.process(generateNewFrame());
		
		// Show SENDER's window
		// #TODO
		// ...............
		
		
		// Sleep for a bit after sending
		Thread.sleep(Util.generateRandomMillis());
	}
	
	/**
	 * Generates a new frame, corrupted or not, with a given payload.
	 * 
	 * @return new frame
	 */
	private Frame generateNewFrame() {
		Frame f = new Frame(Util.generateRandomPayload());
		f.setCorrupted((Util.generateRandomNumber()>70) ? true : false);
		return f;
	}

	
	/**
	 * @return max. number of frames allowed to be sent
	 */
	public int getMaxAllowed() {
		return max_allowed;
	}
	
}
