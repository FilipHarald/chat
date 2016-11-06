package logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Filip on 2016-11-03.
 */
public abstract class AbstractLoggerStrategy implements LoggerStrategy, Observer {
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public void update(Observable o, Object arg) {
        Calendar cal = Calendar.getInstance();
        String prefix = "[" + DATE_FORMAT.format(cal.getTime()) + "] ";
        if (arg instanceof String) {
            log(prefix + arg);
        } else {
            logError(prefix + "[ERROR] " + arg);
        }
    }

    @Override
    public abstract void log(String message);

    @Override
    public abstract void logError(String error);
}
