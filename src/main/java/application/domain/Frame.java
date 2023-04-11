package application.domain;

public class Frame {

	private int sequenceNumber;
	private String payload;
	private boolean isAcknowledged;
	private Long retransmissionTimer;
	private boolean isCorrupted;

	/*
	 * --------------- CONSTRUCTOR
	 */

	public Frame(int sequenceNumber, String payload, boolean isCorrupted) {

		if (payload == null || (payload != null && payload.isBlank())) {
			throw new IllegalArgumentException("[FRAME] ERROR: frame payload content is empty or null.");
		}

		this.sequenceNumber = sequenceNumber;
		this.payload = payload;
		this.isAcknowledged = false;
		this.retransmissionTimer = null;
		this.isCorrupted = isCorrupted;

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

	public boolean isCorrupted() {
		return isCorrupted;
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

	public void setCorrupted(boolean isCorrupted) {
		this.isCorrupted = isCorrupted;
	}

}
