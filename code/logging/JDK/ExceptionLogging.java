import java.util.logging.Logger;
import java.util.logging.Level;

public class ExceptionLogging {

    private static Logger log = Logger.getLogger(ExceptionLogging.class.getName());

    public static void main(String... args) {
        log.info("Application started");

        try {
            log.finest("creating string instance");
            String str = null;
            log.finest("accessing length property of string");
            str.length();
        } catch (NullPointerException e) {
            // look at the log outputs and see the difference!
            log.severe("An error occured: " + e.getStackTrace());  // don't do that
            log.log(Level.SEVERE, "An error occured", e);     // pass exception as paramter instead
        }

        log.info("Bye bye");
    }
}
