public class Main {

    public static void main(String[] args) {
        System.out.println("=== SingletonPatternExample Application ===\n");

        // Simulate OrderService using the logger
        System.out.println("-- OrderService starting --");
        Logger orderLogger = Logger.getInstance();
        orderLogger.log("Order #1001 received.");
        orderLogger.log("Order #1001 validated.");

        System.out.println("\n-- PaymentService starting --");
        Logger paymentLogger = Logger.getInstance();
        paymentLogger.log("Processing payment for Order #1001.");
        paymentLogger.log("Payment successful for Order #1001.");

        System.out.println("\n-- Instance Verification --");
        System.out.println("orderLogger   == paymentLogger : " + (orderLogger == paymentLogger));
        System.out.println("orderLogger   hashCode         : " + System.identityHashCode(orderLogger));
        System.out.println("paymentLogger hashCode         : " + System.identityHashCode(paymentLogger));

        System.out.println("\nApplication finished.");
    }
}
