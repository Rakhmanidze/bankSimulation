package cz.cvut.fel.omo.bank;

import cz.cvut.fel.omo.bank.bankMediator.BankMediator;
import cz.cvut.fel.omo.bank.dao.*;
import cz.cvut.fel.omo.bank.service.*;
import cz.cvut.fel.omo.bank.states.accountStates.AccountStateFrozen;
import cz.cvut.fel.omo.bank.strategy.statisticStrategy.MonthlyStatisticsStrategy;
import cz.cvut.fel.omo.bank.strategy.statisticStrategy.YearlyStatisticsStrategy;
import cz.cvut.fel.omo.bank.enums.*;
import cz.cvut.fel.omo.bank.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Configuration2 {
    private EntityManager em;
    private BankService bankService;
    private CustomerService customerService;
    private AccountService accountService;
    private PaymentCardService paymentCardService;
    private TransactionService transactionService;
    private BankMediator bankMediator;
    private EmployeeService employeeService;

     public Configuration2() {
        // Set up EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bank_system");
        em = emf.createEntityManager();

        // Initialize services and DAOs
        BankDao bankDao = new BankDao(em);
        bankService = new BankService(bankDao);

        CustomerDao customerDao = new CustomerDao(em);
        customerService = new CustomerService(customerDao);

        AccountDao accountDao = new AccountDao(em);
        accountService = new AccountService(accountDao);

        EmployeeDao employeeDao = new EmployeeDao(em);
        employeeService = new EmployeeService(employeeDao);

        PaymentCardDao paymentCardDao = new PaymentCardDao(em);
        paymentCardService = new PaymentCardService(paymentCardDao);

        TransactionDao transactionDao = new TransactionDao(em);
        transactionService = new TransactionService(transactionDao, accountService);

        bankMediator = new cz.cvut.fel.omo.bank.bankMediator.BankMediatorImpl(customerService, employeeService, new EmployeeActionDao(em));
    }

    public void startBankSimulation() {
        // Create Bank
        Address address = new Address(CountryCode.CZ, "Brno", "Moravska", 1111);
        Bank bank = new Bank("CSOB", 2220, address);
        bankService.createBank(bank);

        // Create Employees
        Employee employee1 = new Employee("Emily Davis", "ED", 35, 987654321, "emily.davis@gmail.com", address, Position.ACCOUNT_MANAGER, bank);
        employeeService.createEmployee(employee1);

        // Create Customers
        bankMediator.createCustomer(employee1, "Sophia Carter", "SC", 32, 852741963, "sophia.carter@gmail.com", address);

        // Retrieve customers after creation
        Optional<Customer> customerOptional1 = customerService.getCustomer(1L);
        Customer customer1 = customerOptional1.get();

        // Change customer state to suspended
        bankMediator.suspendCustomer(employee1, customer1);
        // Create Account for suspended Customer
        Account account1 = new Account(customer1, BigDecimal.valueOf(150000), Currency.CZK, AccountType.ADVANCED, bank.getBankCode());
        Account account2 = new Account(customer1, BigDecimal.valueOf(50000), Currency.USD, AccountType.ADVANCED, bank.getBankCode());
        Account account3 = new Account(customer1, BigDecimal.valueOf(50000), Currency.EU, AccountType.ADVANCED, bank.getBankCode());

        accountService.createAccount(account1);

        // update customer
        customer1.setAge(33);
        bankMediator.updateCustomer(employee1, customer1);
        Address newAddress = new Address(CountryCode.CZ, "Prague", "Strahov", 1600);
        customer1.setAddress(newAddress);
        bankMediator.updateCustomer(employee1, customer1);

        // Change customer state to active
        bankMediator.activeCustomer(employee1, customer1);
        // Create Account for active Customer
        accountService.createAccount(account1);
        accountService.createAccount(account2);
        accountService.createAccount(account3);

        // Change account state to frozen
        accountService.changeAccountState(account1, AccountStateEnum.FROZEN);
        // Perform Transaction from Frozen account
        transactionService.createTransaction(BigDecimal.valueOf(2000), account1, account2, LocalDate.of(2023, 1, 15), "gift");
        // Create Payment Cards for Frozen account
        PaymentCard paymentCard1 = new PaymentCard(account1);
        PaymentCard paymentCard2 = new PaymentCard(account2);
        PaymentCard paymentCard3 = new PaymentCard(account3);
        paymentCardService.createPaymentCard(paymentCard1);

        // Change account state to active
        accountService.changeAccountState(account1, AccountStateEnum.ACTIVE);

        transactionService.createTransaction(BigDecimal.valueOf(2000), account1, account2, LocalDate.of(2023, 1, 15), "gift");
        paymentCardService.createPaymentCard(paymentCard1);

        // Freeze all accounts of customer
        accountService.freezeAllAccount(customer1);

        paymentCardService.createPaymentCard(paymentCard1);
        paymentCardService.createPaymentCard(paymentCard2);
        paymentCardService.createPaymentCard(paymentCard3);

        accountService.unFreezeAllAccount(customer1);
        paymentCardService.createPaymentCard(paymentCard2);
        paymentCardService.createPaymentCard(paymentCard3);



        // Create Transaction
        accountService.freezeAllAccount(customer1);

        transactionService.createTransaction(BigDecimal.valueOf(2000), account1, account2, LocalDate.of(2024, 1, 15), "gift");
        transactionService.createTransaction(BigDecimal.valueOf(2000), account2, account3, LocalDate.of(2024, 1, 15), "gift");
        transactionService.createTransaction(BigDecimal.valueOf(2000), account3, account1, LocalDate.of(2024, 1, 15), "gift");

        accountService.unFreezeAllAccount(customer1);

        transactionService.createTransaction(BigDecimal.valueOf(2000), account1, account2, LocalDate.of(2024, 1, 15), "gift");
        transactionService.createTransaction(BigDecimal.valueOf(2000), account2, account3, LocalDate.of(2024, 1, 15), "gift");
        transactionService.createTransaction(BigDecimal.valueOf(2000), account3, account1, LocalDate.of(2024, 1, 15), "gift");

//        accountService.deleteAccount(account1);
    }
}
