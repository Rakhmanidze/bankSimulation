package cz.cvut.fel.omo.bank.strategy.statisticStrategy;

import cz.cvut.fel.omo.bank.model.Account;

public interface StatisticsStrategy {
    void calculateStatistics(Account account);
}
