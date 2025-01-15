package cz.cvut.fel.omo.bank.strategy.currencyConversionStrategy;

import cz.cvut.fel.omo.bank.enums.Currency;
import java.math.BigDecimal;
import static cz.cvut.fel.omo.bank.helperMethods.BankData.CurrencyRates.EXCHANGE_RATES;

public class FixedRateConversionStrategy implements CurrencyConversionStrategy {
    @Override
    public BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency){
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        String key = fromCurrency.toString() + "-" + toCurrency.toString();
        BigDecimal rate = EXCHANGE_RATES.get(key);
        if (rate == null) {
            throw new IllegalArgumentException("Unsupported currency conversion: " + key);
        }
        return amount.multiply(rate);
    }
}