package application.domain;

import java.util.ArrayList;
import java.util.Optional;

public class CircularBuffer {
    private ArrayList<Frame> buffer;
    private int windowSize;

    public CircularBuffer(int windowSize) {
        this.windowSize = windowSize;
        this.buffer = new ArrayList<Frame>(windowSize);
    }

    public void addFrame(Frame frame) {
        if (buffer.size() < windowSize) {
            buffer.add(frame);
        } else {
            throw new IllegalStateException("Buffer is full, cannot add more frames");
        }
    }

    public Frame getFrameBySequenceNumber(int sequenceNumber) {
        for (Frame frame : buffer) {
            if (frame.getSequenceNumber() == sequenceNumber) {
                return frame;
            }
        }
        return null;
    }

    public Frame getFrameByIndex(int index) {
        return buffer.get(index);
    }

    public boolean removeFrame(int sequenceNumber) {
        Frame frame = getFrameBySequenceNumber(sequenceNumber);
        if (frame != null) {
            return buffer.remove(frame);
        }
        return false;
    }

    public Optional<Frame> getFirstUnacknowledgedFrame() {
        for (Frame frame : buffer) {
            if (!frame.isAcknowledged()) {
                return Optional.of(frame);
            }
        }
        return Optional.empty();
    }

    public int size() {
        return buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean isFull() {
        return buffer.size() == windowSize;
    }

    public void clear() {
        buffer.clear();
    }
}