package cz.cvut.fel.omo.bank.builder;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionBuilder {
    private BigDecimal sendAmount;
    private BigDecimal receivedAmount;
    private BigDecimal commission;
    private Account sourceAccount;
    private Account targetAccount;
    private LocalDate date;
    private String message;

    public Transaction build() {
        if (sendAmount == null || receivedAmount == null || commission == null || targetAccount == null || sourceAccount == null) {
            throw new IllegalArgumentException("Required fields cannot be null");
        }
        if (sendAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (commission.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Commission cannot be negative");
        }
        if (message == null) {
            message = "No message";
        }

        return new Transaction(this);
    }

    public TransactionBuilder setSendAmount(BigDecimal amount) {
        this.sendAmount = amount;
        return this;
    }

    public TransactionBuilder setReceivedAmount(BigDecimal amount) {
        this.receivedAmount = amount;
        return this;
    }

    public TransactionBuilder setCommission(BigDecimal commission) {
        this.commission = commission;
        return this;
    }

    public TransactionBuilder setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
        return this;
    }

    public TransactionBuilder setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    public TransactionBuilder setDate(LocalDate localDate) {
        this.date = localDate;
        return this;
    }

    public TransactionBuilder setMessage(String message) {
        this.message = message;
        return this;
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