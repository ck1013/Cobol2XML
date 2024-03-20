package logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    private static FileHandler fileHTML;
    private static MyFormatter formatterHTML;

    public static void setup() throws IOException {

        // Get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // Suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);

        // Check if the log files already exist and append to them instead of overwriting
        if (Files.exists(Paths.get("Logging.txt"))) {
            fileTxt = new FileHandler("Logging.txt", true);
        } else {
            fileTxt = new FileHandler("Logging.txt");
        }

        if (Files.exists(Paths.get("Logging.html"))) {
            fileHTML = new FileHandler("Logging.html", true);
        } else {
            fileHTML = new FileHandler("Logging.html");
        }

        // Create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

       
