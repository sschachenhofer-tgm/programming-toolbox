package designpatterns.singleton.logger;

import java.io.IOException;

public class LoggingThread extends Thread {

    @Override
    public void run() {
        SingletonLogger logger;

        try {
            logger = SingletonLogger.getInstance("D:\\Desktop\\logfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Log 20 messages
        for (int i = 0; i < 20; i++) {
            logger.logErrorMessage(String.format("This is message %d by thread %s", i, Thread.currentThread().getName()));
        }
    }
}
