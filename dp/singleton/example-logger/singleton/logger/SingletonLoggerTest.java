package designpatterns.singleton.logger;

public class SingletonLoggerTest {
    public static void main(String[] args) {

        // Start 10 threads to each log 20 messages concurrently
        for (int i = 0; i < 10; i++) {
            new LoggingThread().start();
        }
    }
}
