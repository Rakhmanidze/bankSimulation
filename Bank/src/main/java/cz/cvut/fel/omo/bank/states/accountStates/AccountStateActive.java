package cz.cvut.fel.omo.bank.states.accountStates;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.PaymentCard;
import cz.cvut.fel.omo.bank.service.PaymentCardService;

import java.math.BigDecimal;

public class AccountStateActive implements AccountState {
    @Override
    public boolean createTransaction(BigDecimal amount, BigDecimal convertedAmountInTargetCurrency, BigDecimal commission, Account sourceAccount, Account targetAccount) {
        BigDecimal totalDebit = amount.add(commission);
        if (sourceAccount.getBalance().compareTo(totalDebit) >= 0) {
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(totalDebit));
            targetAccount.setBalance(targetAccount.getBalance().add(convertedAmountInTargetCurrency));
            return true;
        }
        return false;
    }

    public boolean createPaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService) {
        if(paymentCardService.persistPaymentCard(paymentCard)){
            paymentCard.getAccount().addPaymentCard(paymentCard);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePaymentCard(PaymentCard paymentCard, PaymentCardService paymentCardService) {
        paymentCard.getAccount().removePaymentCard(paymentCard);
        paymentCardService.removePaymentCard(paymentCard.getPaymentCard_id());
        return true;
    }
}