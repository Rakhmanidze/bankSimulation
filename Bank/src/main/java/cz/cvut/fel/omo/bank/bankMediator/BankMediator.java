package cz.cvut.fel.omo.bank.bankMediator;

import cz.cvut.fel.omo.bank.model.Address;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.model.Employee;
import cz.cvut.fel.omo.bank.model.EmployeeAction;

import java.util.List;

public interface BankMediator {

    void createCustomer(Employee employee, String name, String surname, int age, int phoneNumber, String email, Address address);

    void deleteCustomer(Employee employee, Customer customer);

    void updateCustomer(Employee employee, Customer customer);

    void activeCustomer(Employee employee, Customer customer);

    void suspendCustomer(Employee employee, Customer customer);

    List<EmployeeAction> getActionsByEmployee(Employee employee);

    void displayActionsByEmployee(Employee employee);

    List<EmployeeAction> getActionsByCustomer(Customer customer);

    void displayActionsByCustomer(Customer customer);

}