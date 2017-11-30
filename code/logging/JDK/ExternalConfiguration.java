import java.util.logging.Logger;
import java.util.logging.Level;


public class ExternalConfiguration {

    private static Logger log = Logger.getLogger(ExternalConfiguration.class.getName());

    public static void main(String... args) {
        log.fine("by default not visible");
        log.info("hello world!");
        log.severe("oh no, an error occured :-(");
        log.log(Level.SEVERE, "look at that: ", new NullPointerException());
    }
}
