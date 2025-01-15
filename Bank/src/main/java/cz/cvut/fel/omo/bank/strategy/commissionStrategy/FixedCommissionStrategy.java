package cz.cvut.fel.omo.bank.strategy.commissionStrategy;

import java.math.BigDecimal;
import static cz.cvut.fel.omo.bank.helperMethods.BankData.Commission.MIN_FIXED_COMMISSION_CZK;

public class FixedCommissionStrategy implements CommissionStrategy {
    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        return MIN_FIXED_COMMISSION_CZK;
    }
}