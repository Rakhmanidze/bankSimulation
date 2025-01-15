package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.enums.Position;

import javax.persistence.*;

@Entity
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;


    // Constructors

    public Employee() {}

    public Employee(String name, String surname, int age, int phoneNumber, String email, Address address, Position position, Bank bank) {
        super(name, surname, age, phoneNumber, email, address);
        this.position = position;
        this.bank = bank;
    }

    // Getters and Setters

    public Long getId() {
        return employee_id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}