    package cz.cvut.fel.omo.bank.strategy.statisticStrategy;

    import cz.cvut.fel.omo.bank.model.Account;
    import cz.cvut.fel.omo.bank.model.Transaction;

    import java.math.BigDecimal;
    import java.time.Year;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    public class YearlyStatisticsStrategy implements StatisticsStrategy {

        @Override
        public void calculateStatistics(Account userAccount) {
            Map<Year, List<Transaction>> transactionsByYear = userAccount.getTransactionList().stream()
                .collect(Collectors.groupingBy(transaction ->
                    Year.of(transaction.getDate().getYear())));


            transactionsByYear.forEach((year, yearlyTransactions) -> {
                long count = yearlyTransactions.size();

                // Total received for the user's account
                BigDecimal totalReceived = yearlyTransactions.stream()
                    .filter(transaction -> transaction.getTargetAccount().equals(userAccount))
                    .map(Transaction::getReceivedAmount)
                    .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Total spent from the user's account
                BigDecimal totalSpent = yearlyTransactions.stream()
                    .filter(transaction -> transaction.getSourceAccount().equals(userAccount))
                    .map(transaction -> transaction.getSendAmount().add(transaction.getCommission()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                System.out.println(year + " year's statistic for account " + userAccount.getAccountNumber() + " : " + count +
                    " transactions, received " + totalReceived + "(" + userAccount.getCurrency().toString() + "), spent " + totalSpent + "(" + userAccount.getCurrency().toString() +")");
            });
        }
    }