package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.dao.PaymentCardDao;
import cz.cvut.fel.omo.bank.model.PaymentCard;

import java.util.List;
import java.util.Optional;

public class PaymentCardService {

    private final PaymentCardDao dao;

    public PaymentCardService(PaymentCardDao paymentCardDao) {
        dao = paymentCardDao;
    }

    public Optional<PaymentCard> getPaymentCard(Long id) {
        return dao.get(id);
    }

    public void createPaymentCard(PaymentCard paymentCard) {
        if (!isPaymentCardExists(paymentCard)) {
            paymentCard.getAccount().getStateImpl()
                .createPaymentCard(paymentCard, this);
        }
    }

    public boolean persistPaymentCard(PaymentCard paymentCard){
            dao.create(paymentCard);
            return true;
    }

    public void updatePaymentCard(PaymentCard paymentCard) {
            dao.update(paymentCard);
    }

    public void deletePaymentCard(PaymentCard paymentCard) {
        if (isPaymentCardExists(paymentCard)) {
            paymentCard.getAccount().getStateImpl()
                .deletePaymentCard(paymentCard, this);
        }
    }

    public boolean isPaymentCardExists(PaymentCard paymentCard) {
        return dao.findByCardNumber(paymentCard.getCardNumber()).isPresent();
    }

    public void removePaymentCard(Long id) {
            dao.delete(id);
    }

    public List<PaymentCard> getAllPaymentCards() {
        return dao.getAll();
    }

    public Optional<PaymentCard> getPaymentCardByCardNumber(int cardNumber) {
        return dao.findByCardNumber(cardNumber);
    }

    public void displayPaymentCardInfo(PaymentCard paymentCard) {
        System.out.println("Card number: " + paymentCard.getCardNumber());
        System.out.println("CVC: " + paymentCard.getCvc());
        System.out.println("Expiration date: " + paymentCard.getExpirationDate());
    }
}