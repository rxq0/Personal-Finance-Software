package Dashboard.Model;

// Simple model to store the alarm properties if the user sets one
public class Alarm {
    public static boolean active = false; // Will be set to true when the user sets an alarm
    public static String currencyCode; // USD, EUR etc.
    public static String rateType; // Buying or selling rate
    public static Float exchangeRateUpperLimit; // We will fire up the alarm if the rate exceeds this amount
    public static Float exchangeRateLowerLimit; // We will fire up the alarm if the rate gets below this amount

}
