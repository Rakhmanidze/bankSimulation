package cz.cvut.fel.omo.bank.states.accountStates;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.PaymentCard;
import cz.cvut.fel.omo.bank.service.PaymentCardService;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class AccountStateFrozen implements AccountState {

    private static final Logger LOGGER = Logger.getLogger(AccountStateFrozen.class.getName());

    @Override
    public boolean createTransaction(BigDecimal amount, BigDecimal convertedAmountInTargetCurrency, BigDecimal commission, Account sourceAccount, Account targetAccount) {
        LOGGER.warning("Transaction attempt failed: the account is in a FROZEN state.");
        return false; // Transactions are not allowed
    }

    @Override
    public boolean createPaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService) {
        LOGGER.warning("Failed to create payment card: the account is in a FROZEN state.");
        return false; // Creating a payment card is not allowed
    }

    @Override
    public boolean deletePaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService) {
        LOGGER.warning("Failed to delete payment card: the account is in a FROZEN state.");
        return false; // Deleting a payment card is not allowed
    }
}
