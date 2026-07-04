/**
 * Logger - Singleton Design Pattern Implementation
 *
 * This class ensures only one instance of Logger exists throughout
 * the application lifecycle. It uses the thread-safe "double-checked
 * locking" approach to guarantee safety in multi-threaded environments.
 */
public class Logger {

    // The single static instance, marked volatile for thread safety
    private static volatile Logger instance;

    // Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger instance created.");
    }

    /**
     * Returns the single instance of Logger.
     * Uses double-checked locking for thread-safe lazy initialization.
     *
     * @return the singleton Logger instance
     */
    public static Logger getInstance() {
        if (instance == null) {                      // First check (no locking)
            synchronized (Logger.class) {
                if (instance == null) {              // Second check (with locking)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    /**
     * Logs a message to the console with a [LOG] prefix.
     *
     * @param message the message to log
     */
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
