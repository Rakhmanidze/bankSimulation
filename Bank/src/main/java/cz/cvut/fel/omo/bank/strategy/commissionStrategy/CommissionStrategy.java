package cz.cvut.fel.omo.bank.strategy.commissionStrategy;

import java.math.BigDecimal;

public interface CommissionStrategy {
    BigDecimal calculateCommission(BigDecimal amount);
}
