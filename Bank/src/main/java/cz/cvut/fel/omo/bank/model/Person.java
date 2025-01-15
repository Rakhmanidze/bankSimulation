package cz.cvut.fel.omo.bank.model;

import javax.persistence.MappedSuperclass;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 15)
    private int phoneNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Embedded
    private Address address;

    // Constructors

    public Person() {}
    public Person(String name, String surname, int age, int phoneNumber, String email, Address address) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
