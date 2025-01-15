package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.builder.TransactionBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @Column(nullable = false)
    private BigDecimal sendAmount;

    @Column(nullable = false)
    private BigDecimal receivedAmount;

    @Column(nullable = false)
    private BigDecimal commission;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 255)
    private String message;

    // Constructors

    public Transaction() {}

    public Transaction(TransactionBuilder builder) {
        this.sendAmount = builder.getSendAmount();
        this.receivedAmount = builder.getReceivedAmount();
        this.commission = builder.getCommission();
        this.sourceAccount = builder.getSourceAccount();
        this.targetAccount = builder.getTargetAccount();
        this.date = builder.getDate();
        this.message = builder.getMessage();
    }

    // Getters

    public Long getTransaction_id() {
        return transaction_id;
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}