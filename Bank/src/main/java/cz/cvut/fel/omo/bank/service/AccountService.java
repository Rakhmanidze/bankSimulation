package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.strategy.statisticStrategy.StatisticsStrategy;
import cz.cvut.fel.omo.bank.dao.AccountDao;
import cz.cvut.fel.omo.bank.enums.AccountStateEnum;
import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.states.accountStates.AccountState;
import cz.cvut.fel.omo.bank.states.accountStates.AccountStateActive;
import cz.cvut.fel.omo.bank.states.accountStates.AccountStateFrozen;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final AccountDao dao;
    private  StatisticsStrategy statisticsStrategy;

    public AccountService(AccountDao accountDao) {
        dao =accountDao;
    }

    public Optional<Account> getAccount(Long id) {
        return dao.get(id);
    }

    // method from CustomerState implementation

    public void createAccount(Account account) {
        if (!isAccountExists(account)) {
            account.getCustomer().getStateIml().createAccount(account, this);
        }
    }

    public void deleteAccount(Account account) {
        if (isAccountExists(account)) {
            account.getCustomer().getStateIml().deleteAccount(account, this);
        }
    }

    public void freezeAccount(Account account) {
        if (isAccountExists(account)) {
            account.getCustomer().getStateIml().freezeAccount(account, this);
        }
    }

    public void unFreezeAccount(Account account) {
        if (isAccountExists(account)) {
            account.getCustomer().getStateIml().unFreezeAccount(account, this);
        }
    }

    public void freezeAllAccount(Customer customer) {
        for (Account account : customer.getAccountList()) {
            if (isAccountExists(account)) {
                customer.getStateIml().freezeAllAccount(customer, this);
            }
        }
    }

    public void unFreezeAllAccount(Customer customer) {
        for (Account account : customer.getAccountList()) {
            if (isAccountExists(account)) {
                customer.getStateIml().unFreezeAllAccount(customer, this);
            }
        }
    }

    private boolean isAccountExists(Account account) {
        return dao.findByAccountNumber(account.getAccountNumber()).isPresent();
    }

    // CRUD and special get method

    public boolean persistAccount(Account account) {
        dao.create(account);
        return true;
    }

    public void updateAccount(Account account) {
        dao.update(account);
    }

    public void removeAccount(Long id) {
        dao.delete(id);
    }

    public List<Account> getAllAccounts() {
        return dao.getAll();
    }

    public Optional<Account> getAccountByAccountNumber(int accountNumber) {
       return dao.findByAccountNumber(accountNumber);
    }

    // method for Account state change
    private AccountState resolveState(AccountStateEnum state){
        return switch (state) {
            case ACTIVE -> new AccountStateActive();
            case FROZEN -> new AccountStateFrozen();
        };
    }

    public void changeAccountState(Account account, AccountStateEnum state) {
        if(isAccountExists(account)){
            account.setState(state);
            AccountState accountStateImpl = resolveState(state);
            account.setStateImpl(accountStateImpl);
            updateAccount(account);
        }

    }

    public void doStatistic(Account account){
        if(isAccountExists(account)){
            if (statisticsStrategy != null){
                statisticsStrategy.calculateStatistics(account);
            }
        }
    }

    public void setStatisticsStrategy(StatisticsStrategy strategy){
        this.statisticsStrategy = strategy;
    }

    public void displayAccountInfo(Account account) {
        System.out.println("Account number: " + account.getAccountNumber());
        System.out.println("Account balance: " + account.getBalance());
        System.out.println("Account currency: " + account.getCurrency());
        System.out.println("Account bank code: " + account.getBankCode());
        System.out.println("Account owner: " + account.getCustomer().getName() + " " + account.getCustomer().getSurname());
    }
}