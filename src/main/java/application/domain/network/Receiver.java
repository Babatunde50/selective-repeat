package application.domain.network;

import application.domain.Frame;
import application.util.Util;

/**
 * Represents a receiver, that will receive
 * a frame by a given sender.
 * 
 * @author Adriana R.F.
 * @version 1.0 Single
 */
public class Receiver {

	// Last received frame
	private Frame lastReceived;
	
	
	/*
	 * --------------- PUBLIC METHODS
	 */
	
	public void receive(Frame frame) throws InterruptedException {
		if (frame == null)
			throw new IllegalArgumentException("[RECEIVER] ERROR: Last received frame is null.");
		this.lastReceived = frame;
		
		// Show SENDER's window
		// #TODO
		// ...............
		
		// Sleep for a bit
		Thread.sleep(Util.generateRandomMillis());
	}
	
	public Frame getLastReceived() {
		return lastReceived;
	}
}
