package cz.cvut.fel.omo.bank.strategy.commissionStrategy;

import java.math.BigDecimal;
import static cz.cvut.fel.omo.bank.helperMethods.BankData.Commission.ZERO_COMMISSION;

public class NoCommissionStrategy implements CommissionStrategy {
    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        return ZERO_COMMISSION;
    }
}