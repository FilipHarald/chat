package logger;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Filip on 2016-11-03.
 */
public abstract class AbstractLoggerStrategy implements LoggerStrategy, Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            log((String)arg);
        } else {
            log((Exception)arg);
        }
    }

    @Override
    public abstract void log(String message);

    @Override
    public abstract void log(Exception e);
}
