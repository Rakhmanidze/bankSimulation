package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.enums.AccountType;
import cz.cvut.fel.omo.bank.enums.AccountStateEnum;
import cz.cvut.fel.omo.bank.enums.Currency;
import cz.cvut.fel.omo.bank.generators.NumberGenerators;
import cz.cvut.fel.omo.bank.states.accountStates.AccountStateActive;
import cz.cvut.fel.omo.bank.states.accountStates.AccountState;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentCard> paymentCardList = new ArrayList<>();

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionList = new ArrayList<>();

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false, unique = true)
    private int accountNumber;

    @Column(nullable = false)
    private int bankCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private AccountStateEnum state;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Transient
    private AccountState accountStateImpl;


    // Constructors

    public Account() {}

    public Account(Customer customer, BigDecimal balance, Currency currency, AccountType type, int bankCode) {
        this.customer = customer;
        this.balance = balance;
        this.currency = currency;
        this.bankCode = bankCode;
        this.state = AccountStateEnum.ACTIVE;
        this.type = type;
        this.accountNumber = NumberGenerators.generateAccountNumber();
        this.accountStateImpl = new AccountStateActive();
    }

    // Getters and Setters

    public AccountStateEnum getState() {
        return state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<PaymentCard> getPaymentCardList() {
        return paymentCardList;
    }
    public void addPaymentCard(PaymentCard paymentCard){
        paymentCardList.add(paymentCard);
    }

    public void removePaymentCard(PaymentCard paymentCard){
        paymentCardList.remove(paymentCard);
    }

    public void setPaymentCardList(List<PaymentCard> paymentCardList) {
        this.paymentCardList = paymentCardList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }
    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public void setState(AccountStateEnum state){
        this.state = state;
    }

    public AccountType getType(){
        return type;
    }

    public void setType(AccountType type){
        this.type = type;
    }

    public void setStateImpl(AccountState state){
        this.accountStateImpl = state;
    }

    public AccountState getStateImpl() {
        return accountStateImpl;
    }
}