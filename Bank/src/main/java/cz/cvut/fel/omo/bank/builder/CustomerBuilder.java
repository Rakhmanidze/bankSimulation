package cz.cvut.fel.omo.bank.builder;

import cz.cvut.fel.omo.bank.model.Address;
import cz.cvut.fel.omo.bank.model.Customer;

public class CustomerBuilder {
    private String name;
    private String surname;
    private int age;
    private int phoneNumber;
    private String email;
    private Address address;

    public Customer build() {
        if (name == null || surname == null || age <= 0 ||  email == null || address == null || phoneNumber <= 0) {
            throw new IllegalArgumentException("Required fields cannot be null or invalid");
        }
        return new Customer(this);
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public CustomerBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public CustomerBuilder setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}