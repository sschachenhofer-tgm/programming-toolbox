package designpatterns.singleton.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SingletonLogger {
    // class variable for the singleton instance
    private static volatile SingletonLogger instance;

    // instance variable
    private PrintWriter fileOutput;

    /**
     * Constructor declared private to prevent instancing the class multiple times
     * @param logFilePath The path to the file the logging messages should be written to
     */
    private SingletonLogger(String logFilePath) throws IOException {
        File file = new File(logFilePath);

        if (file.isFile()) {
            // The file already exists
            this.fileOutput = new PrintWriter(file);
        } else {
            if (file.createNewFile()) {
                this.fileOutput = new PrintWriter(file);
            }
        }
    }

    /**
     * The method that can be used to access the instance of the SingletonLogger class
     * @param logFilePath If a new instance is created, the path of the log file is set to this path. If the instance
     *                    already exists, this parameter is ignored.
     * @return The singleton instance of the SingletonLogger class
     */
    public static SingletonLogger getInstance(String logFilePath) throws IOException {
        if (instance == null) {
            synchronized (SingletonLogger.class) {
                if (instance == null) {
                    instance = new SingletonLogger(logFilePath);
                }
            }
        }

        return instance;
    }

    /**
     * Log an error to the log file
     * @param message The error message to log
     */
    public void logErrorMessage(String message) {
        this.fileOutput.println(String.format("[ERROR] %s", message));
        this.fileOutput.flush();
    }
}
