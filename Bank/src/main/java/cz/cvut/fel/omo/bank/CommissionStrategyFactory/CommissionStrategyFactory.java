package cz.cvut.fel.omo.bank.CommissionStrategyFactory;

import cz.cvut.fel.omo.bank.enums.AccountType;
import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.strategy.commissionStrategy.CommissionStrategy;
import cz.cvut.fel.omo.bank.strategy.commissionStrategy.FixedCommissionStrategy;
import cz.cvut.fel.omo.bank.strategy.commissionStrategy.NoCommissionStrategy;
import cz.cvut.fel.omo.bank.strategy.commissionStrategy.PercentageCommissionStrategy;
import java.math.BigDecimal;
import static cz.cvut.fel.omo.bank.helperMethods.BankData.Commission.COMMISSION_STRATEGY_THRESHOLD_CZK;

public class CommissionStrategyFactory {
    public CommissionStrategy getStrategy(BigDecimal amount, Account account) {
        if (account.getType() == AccountType.ADVANCED) {
            return new NoCommissionStrategy();
        } else if (amount.compareTo(COMMISSION_STRATEGY_THRESHOLD_CZK) <= 0) {
            return new FixedCommissionStrategy();
        } else {
            return new PercentageCommissionStrategy();
        }
    }
}