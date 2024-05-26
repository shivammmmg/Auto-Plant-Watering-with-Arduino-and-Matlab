

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Logger class handles logging functionality.
 * It allows logging messages with timestamps and provides methods to retrieve the log.
 */
public class Logger {
    private static String log = "";

    /**
     * Logs a message with a timestamp.
     *
     * @param message The message to be logged.
     */
    public static void log(String message) {
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String logEntry = dateFormat.format(now) + "   " + message+"\n";
        log += logEntry;
        System.out.print(message);
    }

    /**
     * Retrieves the log.
     *
     * @return The log as a string.
     */
    public static String getLog() {
        return log;
    }
    public static void emptyLog()
    {
        log = "";
    }
}
