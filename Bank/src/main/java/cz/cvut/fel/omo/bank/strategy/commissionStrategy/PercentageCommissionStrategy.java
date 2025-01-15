package cz.cvut.fel.omo.bank.strategy.commissionStrategy;

import java.math.BigDecimal;
import static cz.cvut.fel.omo.bank.helperMethods.BankData.Commission.*;

public class PercentageCommissionStrategy implements CommissionStrategy {
    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        BigDecimal rate = amount.compareTo(HIGH_PERCENTAGE_THRESHOLD_CZK) > 0
                ? LOWER_PERCENTAGE_COMMISSION_RATE
                : BASIC_PERCENTAGE_COMMISSION_RATE;
        return amount.multiply(rate);
    }
}