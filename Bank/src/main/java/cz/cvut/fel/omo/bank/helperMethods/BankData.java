package cz.cvut.fel.omo.bank.helperMethods;

import java.math.BigDecimal;
import java.util.Map;

public class BankData {
    public static class Commission {
        public static final BigDecimal COMMISSION_STRATEGY_THRESHOLD_CZK = BigDecimal.valueOf(1000);
        public static final BigDecimal HIGH_PERCENTAGE_THRESHOLD_CZK = BigDecimal.valueOf(100000);
        public static final BigDecimal MIN_FIXED_COMMISSION_CZK = BigDecimal.valueOf(10);
        public static final BigDecimal BASIC_PERCENTAGE_COMMISSION_RATE = BigDecimal.valueOf(0.01);
        public static final BigDecimal LOWER_PERCENTAGE_COMMISSION_RATE = BigDecimal.valueOf(0.0005); // 0.05% less
        public static final BigDecimal ZERO_COMMISSION = BigDecimal.ZERO;
    }

    public static class CurrencyRates {
        public static final BigDecimal CONVERT_CZK_TO_USD = new BigDecimal("0.05");
        public static final BigDecimal CONVERT_CZK_TO_EU = new BigDecimal("0.04");
        public static final BigDecimal CONVERT_USD_TO_CZK = new BigDecimal("20.0");
        public static final BigDecimal CONVERT_EU_TO_CZK = new BigDecimal("25.0");
        public static final BigDecimal CONVERT_USD_TO_EU = new BigDecimal("0.9");
        public static final BigDecimal CONVERT_EU_TO_USD = new BigDecimal("1.1");

        public static final Map<String, BigDecimal> EXCHANGE_RATES = Map.of(
                "CZK-USD", CONVERT_CZK_TO_USD,
                "CZK-EU", CONVERT_CZK_TO_EU,
                "USD-CZK", CONVERT_USD_TO_CZK,
                "EU-CZK", CONVERT_EU_TO_CZK,
                "USD-EU", CONVERT_USD_TO_EU,
                "EU-USD", CONVERT_EU_TO_USD
        );
    }
}
