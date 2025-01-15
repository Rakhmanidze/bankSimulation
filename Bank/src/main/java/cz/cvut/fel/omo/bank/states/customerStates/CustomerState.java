package cz.cvut.fel.omo.bank.states.customerStates;

import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.service.AccountService;

public interface CustomerState {
    boolean createAccount(Account account, AccountService accountService);

    boolean deleteAccount(Account account, AccountService accountService);

    boolean freezeAccount(Account account, AccountService accountService);

    boolean unFreezeAccount(Account account, AccountService accountService);

    boolean freezeAllAccount(Customer customer, AccountService accountService);

    boolean unFreezeAllAccount(Customer customer, AccountService accountService);

}
