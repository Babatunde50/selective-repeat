package application.domain;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.Ansi.Color;

public class Logger {
    public static void logBuffer(CircularBuffer buffer, int windowSize, int currentSequenceNumber) {
        AnsiConsole.systemInstall();

        StringBuilder sb = new StringBuilder();

        sb.append(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a("Sender Buffer:").reset().a("\n"));
        for (int i = 0; i < 8; i++) {
            Frame frame = buffer.getFrameBySequenceNumber(i);
            if (frame != null) {
                sb.append("Seq: ").append(frame.getSequenceNumber())
                        .append(", Payload: ").append(frame.getPayload())
                        .append(", Ack: ").append(frame.isAcknowledged());
            } else {
                sb.append("Seq: ").append(i).append(" (Empty)");
            }

            if (i == currentSequenceNumber) {
                sb.append(Ansi.ansi().fg(Ansi.Color.GREEN).a(" <-- Current").reset());
            }

            if (i >= (currentSequenceNumber - windowSize + 8) % 8 && i < currentSequenceNumber) {
                sb.append(Ansi.ansi().fg(Ansi.Color.YELLOW).a(" (In Window)").reset());
            }

            sb.append("\n");
        }

        AnsiConsole.out().println(sb.toString());
        AnsiConsole.systemUninstall();
    }

    public static void printMessage(String message, Color color) {
        AnsiConsole.systemInstall();
        Ansi ansiMessage = Ansi.ansi().fg(color).a(message).reset();
        AnsiConsole.out().println(ansiMessage);
        AnsiConsole.systemUninstall();
    }
}
