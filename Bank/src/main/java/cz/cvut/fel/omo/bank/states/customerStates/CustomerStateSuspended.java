package cz.cvut.fel.omo.bank.states.customerStates;

import cz.cvut.fel.omo.bank.enums.AccountStateEnum;
import cz.cvut.fel.omo.bank.model.Account;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.service.AccountService;

import java.util.logging.Logger;

public class CustomerStateSuspended implements CustomerState {

    private static final Logger LOGGER = Logger.getLogger(CustomerStateSuspended.class.getName());

    @Override
    public boolean createAccount(Account account, AccountService accountService) {
        LOGGER.warning("Attempt to create an account for a suspended customer: " + account.getCustomer().getId());
        return false;
    }

    @Override
    public boolean deleteAccount(Account account, AccountService accountService) {
        LOGGER.warning("Attempt to delete an account for a suspended customer: " + account.getCustomer().getId());
        return false;
    }

    @Override
    public boolean freezeAccount(Account account, AccountService accountService) {
        accountService.changeAccountState(account, AccountStateEnum.FROZEN);
        accountService.updateAccount(account);
        LOGGER.info("Freezing account: " + account.getAccount_id() + " for suspended customer: " + account.getCustomer().getId());
        return true;
    }

    @Override
    public boolean unFreezeAccount(Account account, AccountService accountService) {
        LOGGER.warning("Attempt to unfreeze an account for a suspended customer: " + account.getCustomer().getId());
        return false;
    }

    @Override
    public boolean freezeAllAccount(Customer customer, AccountService accountService) {
        for (Account account : customer.getAccountList()) {
            accountService.changeAccountState(account, AccountStateEnum.FROZEN);
            accountService.updateAccount(account);
        }
        LOGGER.info("Freezing all accounts for suspended customer: " + customer.getId());
        return true;
    }

    @Override
    public boolean unFreezeAllAccount(Customer customer, AccountService accountService) {
        LOGGER.warning("Attempt to unfreeze all accounts for a suspended customer: " + customer.getId());
        return false;
    }
}
