## Selective Repeat

### Overview
This project simulates a selective-repeat protocol with a sliding window for reliable data transmission. The implementation consists of a Logger class for logging events, a CircularBuffer class for managing the sliding window, a Frame class for representing data packets, and separate Sender and Receiver classes for handling the transmission process. The Main class ties everything together and allows users to configure and run the simulation.

### Requirements

Java JDK (version X or higher)
Jansi library (version 2.3.4 or higher)
Maven

### Running the Project

Clone the repository:

```
git clone https://github.com/Babatunde50/selective-repeat.git
```

Change to the project directory:

```
cd selective-repeat
```

Compile the project:

```
mvn clean compile
```

Run the project:

```
mvn exec:java -Dexec.mainClass="application.main.Main"
```

The application will start and run the selective-repeat protocol simulation based on the specified window size and packet loss probability.
