package application.domain;

/**
 * Representation of a frame, with a given:
 * 	- sequence number
 * 	- payload [message content]
 * 	- flag [whether the frame is corrupted or not]
 * 
 * @author Adriana R.F.
 * @version 1.0 Single
 */
public class Frame {
	
	private final static int SEQ_BITS = 3; // n=3, for sliding window impl.
	private String payload; // Content of the frame itself
	private boolean flag = false; // Uncorrupted by default
	
	
	/*
	 * --------------- CONSTRUCTOR
	 */
	
	public Frame(String payload) {
		if (payload == null || (payload != null && payload.isBlank())) {
			throw new IllegalArgumentException("[FRAME] ERROR: frame payload content is empty or null.");
		}
		this.payload = payload;
	}
	
	
	/*
	 * --------------- PUBLIC METHODS
	 */

	
	/**
	 * Establishes whether a given frame is corrupted or not.
	 * 
	 * @param corrupted (true/false -> flag)
	 */
	public void setCorrupted(boolean corrupted) {
		this.flag = corrupted;
	}

	/**
	 * @return payload content of the frame
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * @return n (number of bits in seq. number -> window size - 1)
	 */
	public int getSeqNumberBits() {
		return SEQ_BITS;
	}

	/**
	 * @return frame flag (if set, frame is corrupted)
	 */
	public boolean isCorrupted() {
		return flag;
	}	
	
	
}
