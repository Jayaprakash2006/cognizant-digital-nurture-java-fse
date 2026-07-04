import java.util.HashMap;
import java.util.Map;

public class FinancialForecast {

    public static double calculateFutureValue(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        return calculateFutureValue(presentValue * (1 + growthRate), growthRate, years - 1);
    }

    private static Map<Integer, Double> memo = new HashMap<>();

    public static double calculateFutureValueMemoized(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        if (memo.containsKey(years)) {
            return memo.get(years);
        }
        double result = calculateFutureValueMemoized(presentValue * (1 + growthRate), growthRate, years - 1);
        memo.put(years, result);
        return result;
    }

    public static double calculateFutureValueIterative(double presentValue, double growthRate, int years) {
        double value = presentValue;
        for (int i = 0; i < years; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }
}
