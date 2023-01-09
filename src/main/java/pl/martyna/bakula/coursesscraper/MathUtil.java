package pl.martyna.bakula.coursesscraper;

public class MathUtil {

    public static double convertTimeToDecimal(String timeToConvert) {
        double minutes = Double.parseDouble(timeToConvert.substring(timeToConvert.indexOf(".") + 1));
        double minutesToDecimal = Math.round((minutes * 100) / 60);
        double minutesToDecimalHours = minutesToDecimal / 100;
        double timeToHalfHour = Math.round(minutesToDecimalHours * 2) / 2.0;
        double fullHour = Double.parseDouble(timeToConvert.substring(0, timeToConvert.indexOf(".")));
        double timeToDecimal = Double.parseDouble(String.valueOf(fullHour + timeToHalfHour).replace(".0", ""));

        return timeToDecimal;
    }

    private MathUtil() {
    }
}
