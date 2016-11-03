import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A strategy for a logging to System.out
 * Each message is logged on a seperate line
 *
 * @author Filip Harald
 */
public class SystemOutLoggerStrategy implements LoggerStrategy {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * {@inheritDoc}
     */
    @Override
    public void logMessage(String message) {
        Calendar cal = Calendar.getInstance();
        System.out.println("[" + DATE_FORMAT.format(cal.getTime()) + "] " + message);
    }
}
