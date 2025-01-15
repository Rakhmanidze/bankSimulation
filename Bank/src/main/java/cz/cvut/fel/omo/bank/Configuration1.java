package cz.cvut.fel.omo.bank;

import cz.cvut.fel.omo.bank.bankMediator.BankMediator;
import cz.cvut.fel.omo.bank.bankMediator.BankMediatorImpl;
import cz.cvut.fel.omo.bank.strategy.statisticStrategy.MonthlyStatisticsStrategy;
import cz.cvut.fel.omo.bank.strategy.statisticStrategy.YearlyStatisticsStrategy;
import cz.cvut.fel.omo.bank.dao.*;
import cz.cvut.fel.omo.bank.enums.*;
import cz.cvut.fel.omo.bank.model.*;
import cz.cvut.fel.omo.bank.service.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Configuration1 {

    private EntityManager em;
    private BankService bankService;
    private CustomerService customerService;
    private AccountService accountService;
    private PaymentCardService paymentCardService;
    private TransactionService transactionService;
    private BankMediator bankMediator;
    private EmployeeService employeeService;

    public Configuration1() {
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

        bankMediator = new BankMediatorImpl(customerService, employeeService, new EmployeeActionDao(em));
    }

    public void startBankSimulation() {
        // Create Bank
        Address address = new Address(CountryCode.CZ, "Prague", "Dejvicka", 2200);
        Bank bank = new Bank("UniCredit", 2250, address);
        bankService.createBank(bank);

        // Create Employees
        Employee employee1 = new Employee("John Doe", "JD", 30, 123123123, "john.doe@gmail.com", address, Position.ACCOUNT_MANAGER, bank);
        Employee employee2 = new Employee("Jane Smith", "JS", 28, 321321321, "jane.smith@gmail.com", address, Position.RECEPTIONIST, bank);
        employeeService.createEmployee(employee1);
        employeeService.createEmployee(employee2);

        // Create Customers
        bankMediator.createCustomer(employee1, "Alice", "Anderson", 25, 456456456, "alice.anderson@gmail.com", address);
        bankMediator.createCustomer(employee2, "Bob", "Brown", 27, 789789789, "bob.brown@gmail.com", address);
        bankMediator.createCustomer(employee2, "Tom", "Trump", 24, 123121444, "tom.hard@gmail.com", address);

        // Retrieve customers after creation
        Optional<Customer> customerOptional1 = customerService.getCustomer(1L);
        Customer customer1 = customerOptional1.get();

        Optional<Customer> customerOptional2 = customerService.getCustomer(2L);
        Customer customer2 = customerOptional2.get();

        Optional<Customer> customerOptional3 = customerService.getCustomer(3L);
        Customer customer3 = customerOptional3.get();

        // Create Accounts for Customers
        Account account1 = new Account(customer1, BigDecimal.valueOf(100000), Currency.CZK, AccountType.BASIC, bank.getBankCode());
        Account account2 = new Account(customer1, BigDecimal.valueOf(100000), Currency.USD, AccountType.BASIC, bank.getBankCode());
        Account account3 = new Account(customer2, BigDecimal.valueOf(100000), Currency.EU, AccountType.BASIC, bank.getBankCode());
        Account account4 = new Account(customer3, BigDecimal.valueOf(100000), Currency.EU, AccountType.ADVANCED, bank.getBankCode());

        accountService.createAccount(account1);
        accountService.createAccount(account2);
        accountService.createAccount(account3);
        accountService.createAccount(account4);

        //Display Account Info
        accountService.displayAccountInfo(account1);
        accountService.displayAccountInfo(account2);
        accountService.displayAccountInfo(account3);
        accountService.displayAccountInfo(account4);

        // Create Payment Cards
        PaymentCard paymentCard1 = new PaymentCard(account1);
        paymentCardService.createPaymentCard(paymentCard1);
        PaymentCard paymentCard2 = new PaymentCard(account3);
        paymentCardService.createPaymentCard(paymentCard2);

        //Display Payment Info
        paymentCardService.displayPaymentCardInfo(paymentCard1);
        paymentCardService.displayPaymentCardInfo(paymentCard2);

        // Perform Transaction between
        transactionService.createTransaction(BigDecimal.valueOf(2000), account1, account2, LocalDate.of(2024, 1, 15), "gift");

        transactionService.createTransaction(BigDecimal.valueOf(2000), account3, account1, LocalDate.of(2020, 3, 15),null);

        transactionService.createTransaction(BigDecimal.valueOf(3000),account2,account1,LocalDate.of(2024, 11, 15),null);

        transactionService.createTransaction(BigDecimal.valueOf(400), account2, account3, LocalDate.of(2023, 11, 15),  null);

        transactionService.createTransaction(BigDecimal.valueOf(5000), account1, account3, LocalDate.of(2021, 4, 15), null);

        // Print Statistic
        accountService.setStatisticsStrategy(new MonthlyStatisticsStrategy());
        accountService.doStatistic(account1);
        accountService.doStatistic(account2);
        accountService.doStatistic(account3);
        accountService.doStatistic(account4);

        accountService.setStatisticsStrategy(new YearlyStatisticsStrategy());
        accountService.doStatistic(account1);
        accountService.doStatistic(account2);
        accountService.doStatistic(account3);
        accountService.doStatistic(account4);

//        //Display Employee Action info
        bankMediator.displayActionsByEmployee(employee1);
        bankMediator.displayActionsByEmployee(employee2);

//        //Display Customer Action info
        bankMediator.displayActionsByCustomer(customer1);
        bankMediator.displayActionsByCustomer(customer2);

        paymentCardService.deletePaymentCard(paymentCard1);
        paymentCardService.deletePaymentCard(paymentCard2);

        accountService.deleteAccount(account4);

        bankMediator.deleteCustomer(employee1,customer3);

        employeeService.deleteEmployee(employee1.getId());
        employeeService.deleteEmployee(employee2.getId());

        bankService.deleteBank(bank.getId());
    }
}