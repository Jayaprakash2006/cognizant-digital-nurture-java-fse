public class Main {

    public static void main(String[] args) {
        double presentValue = 10000.0;
        double growthRate = 0.08;
        int years = 10;

        System.out.println("=== Financial Forecasting Tool ===");
        System.out.printf("Present Value  : $%.2f%n", presentValue);
        System.out.printf("Annual Growth  : %.0f%%%n", growthRate * 100);
        System.out.printf("Years          : %d%n%n", years);

        double recursiveResult = FinancialForecast.calculateFutureValue(presentValue, growthRate, years);
        System.out.printf("Recursive Future Value       : $%.2f%n", recursiveResult);

        double memoizedResult = FinancialForecast.calculateFutureValueMemoized(presentValue, growthRate, years);
        System.out.printf("Memoized Future Value        : $%.2f%n", memoizedResult);

        double iterativeResult = FinancialForecast.calculateFutureValueIterative(presentValue, growthRate, years);
        System.out.printf("Iterative Future Value       : $%.2f%n", iterativeResult);

        System.out.println("\n=== Forecasts for Different Time Horizons ===");
        int[] horizons = {1, 5, 10, 20, 30};
        for (int y : horizons) {
            double fv = FinancialForecast.calculateFutureValue(presentValue, growthRate, y);
            System.out.printf("After %2d year(s) : $%.2f%n", y, fv);
        }

        System.out.println("\n=== Time Complexity Analysis ===");
        System.out.println("Recursive (no memo) : O(n) time | O(n) space (call stack)");
        System.out.println("Recursive (memoized): O(n) time | O(n) space (memo table)");
        System.out.println("Iterative           : O(n) time | O(1) space");
        System.out.println("\nThe iterative approach is optimal — same time complexity with constant space.");
        System.out.println("Memoization prevents redundant recomputation in overlapping recursive calls.");
    }
}
