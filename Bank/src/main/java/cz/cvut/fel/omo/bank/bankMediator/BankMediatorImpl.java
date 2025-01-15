package cz.cvut.fel.omo.bank.bankMediator;

import cz.cvut.fel.omo.bank.dao.EmployeeActionDao;
import cz.cvut.fel.omo.bank.enums.EmployeeActionEnum;
import cz.cvut.fel.omo.bank.model.Address;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.model.Employee;
import cz.cvut.fel.omo.bank.model.EmployeeAction;
import cz.cvut.fel.omo.bank.service.CustomerService;
import cz.cvut.fel.omo.bank.service.EmployeeService;

import java.util.List;

public class BankMediatorImpl implements BankMediator{
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final EmployeeActionDao employeeActionDao;

    public BankMediatorImpl(CustomerService customerService, EmployeeService employeeService, EmployeeActionDao employeeActionDao) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.employeeActionDao = employeeActionDao;
    }

    @Override
    public void createCustomer(Employee employee, String name, String surname, int age, int phoneNumber, String email, Address address) {

        customerService.createCustomer(name, surname, age, phoneNumber, email, address);

        EmployeeAction action = new EmployeeAction(
                employee.getEmail(),
                email,
                EmployeeActionEnum.CREATE
        );
        employeeActionDao.create(action);
    }

    @Override
    public void deleteCustomer(Employee employee, Customer customer) {
        customerService.removeCustomer(customer.getId());

        EmployeeAction action = new EmployeeAction(
                employee.getEmail(),
                customer.getEmail(),
                EmployeeActionEnum.DELETE
        );
        employeeActionDao.create(action);
    }

    @Override
    public void updateCustomer(Employee employee, Customer customer) {

        String potentialNewEmail = customer.getEmail();

        String oldEmail = customerService.getCustomer(customer.getId()).get().getEmail();
        if(!potentialNewEmail.equals(oldEmail)) {
            employeeActionDao.updateCustomerEmail(oldEmail, potentialNewEmail);
        }

        customerService.updateCustomer(customer);

        EmployeeAction action = new EmployeeAction(
                employee.getEmail(),
                customer.getEmail(),
                EmployeeActionEnum.UPDATE
        );
        employeeActionDao.create(action);
    }

    @Override
    public void activeCustomer(Employee employee, Customer customer) {
        if(customerService.activeCustomer(customer)){
            EmployeeAction action = new EmployeeAction(
                    employee.getEmail(),
                    customer.getEmail(),
                    EmployeeActionEnum.ACTIVE
            );
            employeeActionDao.create(action);
        }
    }

    @Override
    public void suspendCustomer(Employee employee, Customer customer) {
        if(customerService.suspendCustomer(customer)){
            EmployeeAction action = new EmployeeAction(
                    employee.getEmail(),
                    customer.getEmail(),
                    EmployeeActionEnum.SUSPEND
            );
            employeeActionDao.create(action);
        }
    }

    @Override
    public List<EmployeeAction> getActionsByEmployee(Employee employee) {
        return employeeActionDao.getByEmployeeEmail(employee.getEmail());
    }

    @Override
    public void displayActionsByEmployee(Employee employee) {
        List<EmployeeAction> actions = employeeActionDao.getByEmployeeEmail(employee.getEmail());
        if (actions.isEmpty()) {
            System.out.println("No actions found for employee with email: " + employee.getEmail());
            return;
        }
        System.out.println("Actions for employee with email: " + employee.getEmail());
        for (EmployeeAction action : actions) {
            System.out.println("Action ID: " + action.getActionId()
                    + ", Customer Email: " + action.getCustomerEmail()
                    + ", Action: " + action.getAction()
                    + ", Date: " + action.getDate());
        }

    }

    @Override
    public List<EmployeeAction> getActionsByCustomer(Customer customer) {
        return employeeActionDao.getByCustomerEmail(customer.getEmail());
    }

    @Override
    public void displayActionsByCustomer(Customer customer) {
        List<EmployeeAction> actions = employeeActionDao.getByCustomerEmail(customer.getEmail());

        if (actions.isEmpty()) {
            System.out.println("No actions found for customer with email: " + customer.getEmail());
            return;
        }

        System.out.println("Actions for customer with email: " + customer.getEmail());
        for (EmployeeAction action : actions) {
            System.out.println("Action ID: " + action.getActionId()
                    + ", Employee Email: " + action.getEmployeeEmail()
                    + ", Action: " + action.getAction()
                    + ", Date: " + action.getDate());
        }
    }
}