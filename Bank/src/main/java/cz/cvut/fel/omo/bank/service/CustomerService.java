package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.builder.CustomerBuilder;
import cz.cvut.fel.omo.bank.dao.CustomerDao;
import cz.cvut.fel.omo.bank.enums.CustomerStateEnum;
import cz.cvut.fel.omo.bank.model.Address;
import cz.cvut.fel.omo.bank.model.Customer;
import cz.cvut.fel.omo.bank.states.customerStates.CustomerState;
import cz.cvut.fel.omo.bank.states.customerStates.CustomerStateActive;
import cz.cvut.fel.omo.bank.states.customerStates.CustomerStateSuspended;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private final CustomerDao dao;

    public CustomerService(CustomerDao customerDao) {
        this.dao = customerDao;
    }

    public Optional<Customer> getCustomer(Long id) {
        return dao.get(id);
    }

    public void createCustomer(String name, String surname, int age, int phoneNumber, String email, Address address) {
        Optional<Customer> existingCustomer = dao.findByEmail(email);

        if (existingCustomer.isEmpty()) {
            Customer customer = new CustomerBuilder()
                    .setName(name)
                    .setSurname(surname)
                    .setAge(age)
                    .setPhoneNumber(phoneNumber)
                    .setEmail(email)
                    .setAddress(address)
                    .build();

            dao.create(customer);
        }
    }

    public boolean activeCustomer(Customer customer) {
        if(!isCustomerExists(customer)) {
            return false;
        }

        changeCustomerState(customer, CustomerStateEnum.ACTIVE);
        return true;
    }

    public boolean suspendCustomer(Customer customer) {
        if(!isCustomerExists(customer)) {
            return false;
        }

        changeCustomerState(customer, CustomerStateEnum.SUSPENDED);
        return true;
    }

    private boolean isCustomerExists(Customer customer) {
        Optional<Customer> existingAccount = dao.findByEmail(customer.getEmail());
        return existingAccount.isPresent();
    }

    public void updateCustomer(Customer customer) {
      dao.update(customer);
    }

    public void removeCustomer(Long id) {
        dao.delete(id);
    }

    public List<Customer> getAllCustomers() {
        return dao.getAll();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return dao.findByEmail(email);
    }

    public void changeCustomerState(Customer customer, CustomerStateEnum newState) {
        customer.setState(newState);
        CustomerState customerStateImpl = resolveState(newState);
        customer.setStateIml(customerStateImpl);
        updateCustomer(customer);
    }

    private CustomerState resolveState(CustomerStateEnum state) {
        return switch (state) {
            case ACTIVE -> new CustomerStateActive();
            case SUSPENDED -> new CustomerStateSuspended();
        };
    }

    public void displayCustomerInfo(Customer customer) {
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Customer surname: " + customer.getSurname());
        System.out.println("Customer age: " + customer.getAge());
        System.out.println("Customer phone number: " + customer.getPhoneNumber());
        System.out.println("Customer email: " + customer.getEmail());
        System.out.println("Customer address: " + customer.getAddress());
    }
}