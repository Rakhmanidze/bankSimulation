package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.generators.NumberGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PaymentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentCard_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, unique = true)
    private long cardNumber;

    @Column(nullable = false)
    private int cvc;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expirationDate;

    // Constructors

    public PaymentCard() {}

    public PaymentCard(Account account){
        this.account = account;
        this.cardNumber = NumberGenerators.generateCardNumber();
        this.cvc = NumberGenerators.generateCVC();
        this.expirationDate = NumberGenerators.generateExpirationDate();
    }

    // Getters and Setters

    public Long getPaymentCard_id() {
        return paymentCard_id;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}