package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.enums.CountryCode;

import javax.persistence.*;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Enumerated(EnumType.STRING)
    @Column(name = "country_code", nullable = false)
    private CountryCode countryCode;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "street", nullable = false, length = 150)
    private String street;

    @Column(name = "number", nullable = false)
    private int number;

    // Constructors

    public Address() {

    }

    public Address(CountryCode countryCode,String city, String street, int number) {
        this.countryCode = countryCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    // Getters and Setters

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}