package cz.cvut.fel.omo.bank.strategy.statisticStrategy;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthlyStatisticsStrategy implements StatisticsStrategy {
    @Override
    public void calculateStatistics(Account userAccount) {
        Map<YearMonth, List<Transaction>> transactionsByMonth = userAccount.getTransactionList().stream()
            .collect(Collectors.groupingBy(transaction ->
                YearMonth.from(transaction.getDate())));


        transactionsByMonth.forEach((yearMonth, monthlyTransactions) -> {
            long count = monthlyTransactions.size();

            // Total received for the user's account
            BigDecimal totalReceived = monthlyTransactions.stream()
                .filter(transaction -> transaction.getTargetAccount().equals(userAccount))
                .map(Transaction::getReceivedAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Total spent from the user's account
            BigDecimal totalSpent = monthlyTransactions.stream()
                .filter(transaction -> transaction.getSourceAccount().equals(userAccount))
                .map(transaction -> transaction.getSendAmount().add(transaction.getCommission()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//            System.out.println("Monthly statistics for account: " + userAccount.getAccountNumber());

            System.out.println(yearMonth.getYear() + " " + yearMonth.getMonth() + " month's statistic for account " + userAccount.getAccountNumber() + " : "
            + count + " transactions, received " + totalReceived + "(" + userAccount.getCurrency().toString() +"), spent "
                + totalSpent + "("+ userAccount.getCurrency().toString() +")");
        });
    }
}