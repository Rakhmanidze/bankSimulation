package cz.cvut.fel.omo.bank.states.accountStates;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.PaymentCard;
import cz.cvut.fel.omo.bank.service.PaymentCardService;

import java.math.BigDecimal;

public interface AccountState {

    boolean createTransaction(BigDecimal amount, BigDecimal convertedAmountInTargetCurrency, BigDecimal commission, Account sourceAccount, Account targetAccount);

    boolean createPaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService);

    boolean deletePaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService);
}