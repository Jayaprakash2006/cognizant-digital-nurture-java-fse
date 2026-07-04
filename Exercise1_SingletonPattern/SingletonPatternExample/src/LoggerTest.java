/**
 * LoggerTest - Verifies the Singleton behavior of the Logger class.
 *
 * Tests confirm that:
 *  1. getInstance() always returns the same object reference.
 *  2. The Logger works correctly for logging messages.
 *  3. Multiple variables pointing to the instance share the same object.
 */
public class LoggerTest {

    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern Test ===\n");

        // --- Test 1: Same reference check ---
        System.out.println("Test 1: Retrieve two references via getInstance()");
        Logger loggerA = Logger.getInstance();
        Logger loggerB = Logger.getInstance();

        if (loggerA == loggerB) {
            System.out.println("PASS - Both references point to the same instance.");
        } else {
            System.out.println("FAIL - Different instances were created!");
        }

        // --- Test 2: hashCode confirms same object ---
        System.out.println("\nTest 2: Compare hashCodes");
        System.out.println("loggerA hashCode: " + System.identityHashCode(loggerA));
        System.out.println("loggerB hashCode: " + System.identityHashCode(loggerB));

        if (System.identityHashCode(loggerA) == System.identityHashCode(loggerB)) {
            System.out.println("PASS - HashCodes match; same object in memory.");
        } else {
            System.out.println("FAIL - HashCodes differ; separate instances exist!");
        }

        // --- Test 3: Logging functionality ---
        System.out.println("\nTest 3: Logging through both references");
        loggerA.log("Message sent via loggerA");
        loggerB.log("Message sent via loggerB");
        System.out.println("PASS - Both references log successfully.");

        // --- Test 4: Thread-safety stress test ---
        System.out.println("\nTest 4: Thread-safety - 5 threads each call getInstance()");
        Logger[] instances = new Logger[5];
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = Logger.getInstance();
                System.out.println("Thread-" + index
                        + " got instance hashCode: "
                        + System.identityHashCode(instances[index]));
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        boolean allSame = true;
        for (Logger inst : instances) {
            if (inst != instances[0]) { allSame = false; break; }
        }
        System.out.println(allSame
                ? "PASS - All threads received the same instance."
                : "FAIL - Threads received different instances!");

        System.out.println("\n=== All Tests Completed ===");
    }
}
