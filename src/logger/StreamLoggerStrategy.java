package logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A strategy for a logging to System.out
 * Each message is logged on a seperate line
 *
 * @author Filip Harald
 */
public class StreamLoggerStrategy extends AbstractLoggerStrategy {
    private OutputStream _messageStream, _errorStream;


    public StreamLoggerStrategy(OutputStream messageStream, OutputStream errorStream) {
        _messageStream = messageStream;
        _errorStream = errorStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        writeToStream(_messageStream, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logError(String error) {
        writeToStream(_errorStream, error);
    }

    /**
     * Writes log entries to the specified stream.
     *
     * @param outputStream
     * @param logEntry
     */
    private void writeToStream(OutputStream outputStream, String logEntry) {
        logEntry += "\n";
        try {
            outputStream.write(logEntry.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
