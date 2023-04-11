package application.domain;


public class Frame {

	private int sequenceNumber;
	private String payload;
	private boolean isAcknowledged;
	private Long retransmissionTimer;

	/*
	 * --------------- CONSTRUCTOR
	 */

	public Frame(int sequenceNumber, String payload) {

		if (payload == null || (payload != null && payload.isBlank())) {
			throw new IllegalArgumentException("[FRAME] ERROR: frame payload content is empty or null.");
		}

		this.sequenceNumber = sequenceNumber;
		this.payload = payload;
		this.isAcknowledged = false;
		this.retransmissionTimer = null;

	}

	// Getters
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getPayload() {
		return payload;
	}

	public boolean isAcknowledged() {
		return isAcknowledged;
	}

	public Long getRetransmissionTimer() {
		return retransmissionTimer;
	}

	// Setters
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setAcknowledged(boolean isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public void setRetransmissionTimer(Long retransmissionTimer) {
		this.retransmissionTimer = retransmissionTimer;
	}

}
