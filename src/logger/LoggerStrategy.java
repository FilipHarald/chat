package logger;

/**
 * A strategy for a logger
 *
 * @author Filip Harald
 */
public interface LoggerStrategy {


    /**
     * Logs the specified message
     *
     * @param message
     */
    void log(String message);

    /**
     * Logs the specified error
     * @param error
     */
    void logError(String error);
}
