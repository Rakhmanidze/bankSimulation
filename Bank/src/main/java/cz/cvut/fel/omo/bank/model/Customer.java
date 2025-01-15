package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.builder.CustomerBuilder;
import cz.cvut.fel.omo.bank.states.customerStates.CustomerStateActive;
import cz.cvut.fel.omo.bank.states.customerStates.CustomerState;
import cz.cvut.fel.omo.bank.enums.CustomerStateEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accountList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private CustomerStateEnum state;

    @Transient
    private CustomerState customerStateImpl;


    // Constructors
    public Customer() {}

    public Customer(CustomerBuilder customerBuilder) {
        super(customerBuilder.getName(), customerBuilder.getSurname(), customerBuilder.getAge(),
                customerBuilder.getPhoneNumber(), customerBuilder.getEmail(), customerBuilder.getAddress());

        this.customerStateImpl = new CustomerStateActive();
        this.state = CustomerStateEnum.ACTIVE;
    }

    public CustomerState getStateIml() {
        return customerStateImpl;
    }

    public void setStateIml(CustomerState state) {
        this.customerStateImpl = state;
    }

    public void setState(CustomerStateEnum state) {
        this.state = state;
    }

    public CustomerStateEnum getState() {
        return state;
    }


    // Getters and Setters

    public Long getId() {
        return customer_id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }
}