package cz.cvut.fel.omo.bank.strategy.currencyConversionStrategy;

import cz.cvut.fel.omo.bank.enums.Currency;

import java.math.BigDecimal;

public interface CurrencyConversionStrategy {
    BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency);
}
