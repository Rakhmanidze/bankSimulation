package cz.cvut.fel.omo.bank.states.customerStates;

import cz.cvut.fel.omo.bank.enums.AccountStateEnum;
import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.service.AccountService;

public class CustomerStateActive implements CustomerState {
    @Override
    public boolean createAccount(Account account, AccountService accountService) {
        if(accountService.persistAccount(account)){
            account.getCustomer().addAccount(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAccount(Account account, AccountService accountService) {
        account.getCustomer().removeAccount(account);
        accountService.removeAccount(account.getAccount_id());
        return true;
    }

    @Override
    public boolean freezeAccount(Account account, AccountService accountService) {
        accountService.changeAccountState(account, AccountStateEnum.FROZEN);
        accountService.updateAccount(account);
        return true;
    }

    @Override
    public boolean unFreezeAccount(Account account, AccountService accountService) {
        accountService.changeAccountState(account, AccountStateEnum.ACTIVE);
        accountService.updateAccount(account);

        return true;
    }

    @Override
    public boolean freezeAllAccount(Customer customer, AccountService accountService) {
        for (Account account: customer.getAccountList()) {
            accountService.changeAccountState(account, AccountStateEnum.FROZEN);
            accountService.updateAccount(account);
        }
        return true;
    }

    @Override
    public boolean unFreezeAllAccount(Customer customer, AccountService accountService) {
        for (Account account: customer.getAccountList()) {
            accountService.changeAccountState(account, AccountStateEnum.ACTIVE);
            accountService.updateAccount(account);
        }
        return true;
    }
}