# selective-repeatSelective Repeat Sliding Window Protocol in Java

This project is an implementation of the sliding window protocol using selective repeat in Java. It simulates the transmission of frames between a sender and a receiver, demonstrating the sender and receiver window updates.

## Overview
The project contains the following main components:

Frame class: Represents the frame structure, containing the sequence number, payload, and a flag for corrupted frames.

Sender class: Handles the sliding window protocol for the sender side, including frame generation, corruption simulation, and random sleep duration.

Receiver class: Handles the sliding window protocol for the receiver side, including frame processing, and random sleep duration.

SelectiveRepeat class: Simulates the transmission of frames between the sender and receiver, coordinating the sending and receiving processes.

Main class: Contains the main() method that starts the simulation.

## Usage
To run the simulation, simply compile and run the Main class. The output will display the sender and receiver window updates as frames are sent and received.

## Implementation Details

Frame Class The Frame class should contain the following fields:

Sequence number
Payload
Corrupted flag
Sender Class

The Sender class should include the following methods:

sendFrames(): Generates a random number of frames between 1 and max_allowed_to_send, and sends them.
sendFrame(Frame frame): Simulates sending a frame, setting the corrupted flag if the random number is >70.
sleepRandom(): Makes the sender sleep for a random duration of time.
Receiver Class

The Receiver class should include the following methods:

receiveFrame(Frame frame): Receives a frame and processes it based on the sliding window protocol.
sleepRandom(): Makes the receiver sleep for a random duration of time.
SelectiveRepeat Class

The SelectiveRepeat class should include the following methods:

simulate(): Iterates through the maximum number of frames to send and simulates the sending and receiving process.

Main Class
The Main class should contain the main() method that creates an instance of the SelectiveRepeat class and starts the simulation.

## Tips
Use the Thread.sleep() method to make the sender and receiver sleep for a random duration.
Use the java.util.Random class to generate random numbers for frame corruption and sleep durations.
Synchronize the sending and receiving methods to avoid race conditions.
Properly handle exceptions (e.g., InterruptedException) when using Thread.sleep().
