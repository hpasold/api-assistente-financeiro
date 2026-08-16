package dio.budgeting.application.output;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CategorySummaryOutput(String category, double total) {

    public static CategorySummaryOutput from(String category, long total) {
        return new CategorySummaryOutput(category,
                BigDecimal.valueOf(total)
                        .divide(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue());
    }
}