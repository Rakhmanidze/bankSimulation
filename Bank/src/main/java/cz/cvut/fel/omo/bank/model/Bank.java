package cz.cvut.fel.omo.bank.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bank_id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "bank_code", nullable = false)
    private int bankCode;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employeeList = new ArrayList<>();

    // Constructors

    public Bank() {}


    public Bank(String name, int bankCode, Address address) {
        this.name = name;
        this.bankCode = bankCode;
        this.address = address;
    }

    // Getters and Setters

    public Long getId() {
        return bank_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Employee> getPersonList() {
        return employeeList;
    }

    public void setPersonList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}