package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.CommissionStrategyFactory.CommissionStrategyFactory;
import cz.cvut.fel.omo.bank.builder.TransactionBuilder;
import cz.cvut.fel.omo.bank.dao.TransactionDao;
import cz.cvut.fel.omo.bank.enums.Currency;
import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Transaction;
import cz.cvut.fel.omo.bank.strategy.commissionStrategy.CommissionStrategy;
import cz.cvut.fel.omo.bank.strategy.currencyConversionStrategy.CurrencyConversionStrategy;
import cz.cvut.fel.omo.bank.strategy.currencyConversionStrategy.FixedRateConversionStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TransactionService {

    private final TransactionDao dao;
    private final AccountService accountService;
    private CommissionStrategy commissionStrategy;
    private final CommissionStrategyFactory strategyFactory;
    private final CurrencyConversionStrategy conversionStrategy;

    public TransactionService(TransactionDao transactionDao, AccountService accountService) {
        dao = transactionDao;
        this.accountService = accountService;
        this.strategyFactory = new CommissionStrategyFactory();
        this.conversionStrategy = new FixedRateConversionStrategy();
    }

    public Optional<Transaction> getTransaction(Long id) {
        return dao.get(id);
    }

    public void createTransaction(BigDecimal amount, Account sourceAccount, Account targetAccount, LocalDate localDate, String message) {
        LocalDate transactionDate = (localDate != null) ? localDate : LocalDate.now();

        try {
            if (amount == null || sourceAccount == null || targetAccount == null) {
                throw new IllegalArgumentException("Required fields for transaction cannot be null");
            }

            BigDecimal convertedAmountInTargetCurrency = conversionStrategy.convert(amount, sourceAccount.getCurrency(), targetAccount.getCurrency());
            BigDecimal convertedAmountToCZK = conversionStrategy.convert(amount, sourceAccount.getCurrency(), Currency.CZK);

            BigDecimal commission = calculateCommission(convertedAmountToCZK, sourceAccount);
            commission = conversionStrategy.convert(commission, Currency.CZK, sourceAccount.getCurrency());

            if (!sourceAccount.getStateImpl().createTransaction(amount, convertedAmountInTargetCurrency, commission, sourceAccount, targetAccount)) {
                return;
            }

             Transaction transaction = new TransactionBuilder()
                    .setSendAmount(amount)
                    .setReceivedAmount(convertedAmountInTargetCurrency)
                    .setCommission(commission)
                    .setSourceAccount(sourceAccount)
                    .setTargetAccount(targetAccount)
                    .setDate(transactionDate)
                    .setMessage(message)
                    .build();

            persistTransaction(transaction, sourceAccount, targetAccount);
        }  catch (IllegalArgumentException e) {
            System.out.println("Transaction creation failed: " + e.getMessage());
        }
    }

    private BigDecimal calculateCommission(BigDecimal amount, Account sourceAccount) {
        commissionStrategy = strategyFactory.getStrategy(amount, sourceAccount);
        return commissionStrategy.calculateCommission(amount);
    }

    private void persistTransaction(Transaction transaction, Account sourceAccount, Account targetAccount) {
        sourceAccount.addTransaction(transaction);
        targetAccount.addTransaction(transaction);
        dao.create(transaction);

        accountService.updateAccount(sourceAccount);
        accountService.updateAccount(targetAccount);
    }

    public void deleteTransaction(Long id) {
            dao.delete(id);
    }

    public List<Transaction> getAllTransactions() {
        return dao.getAll();
    }
}