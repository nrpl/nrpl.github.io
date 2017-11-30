import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;

/**
 * Logger and Handler both are initialized with a loglevel.
 * Logmessages have a loglevel aswell.
 * The Logger passes only messages with a loglevel equal or higher
 * to the loglevel which the logger is configured with.
 * The handler is using a loglevel too.
 * Both loglevels have to match to the messages log level.
 */
public class LogLevel {

    private static final Logger log = Logger.getLogger(LogLevel.class.getName());

    public static void main(String... args) {
        log.setLevel(Level.INFO);  // not enough, the standard console logger defaults to Level.INFO
        
        // add ConsoleHandler with defined log level FINEST
        // but this leads to duplicated messages, caused by the
        // default console logger.
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        log.addHandler(handler);

        // set log level of all configured handlers...
        for(Handler h : log.getHandlers()) {
            h.setLevel(Level.FINEST);
        }

        log.finest("finest");
        log.finer("finer");
        log.fine("fine");
        log.info("info");
        log.config("config");
        log.warning("warning");
        log.severe("severe");

        log.log(Level.INFO, "another way to log");
    }
}
