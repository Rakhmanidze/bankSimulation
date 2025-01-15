package cz.cvut.fel.omo.bank.model;

import cz.cvut.fel.omo.bank.enums.EmployeeActionEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class EmployeeAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actionId;

    @Column(nullable = false)
    private String employeeEmail;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private EmployeeActionEnum action;

    @Column(nullable = false)
    private LocalDate date;

    // Constructors
    public EmployeeAction() {}

    public EmployeeAction(String employeeEmail, String customerEmail, EmployeeActionEnum action) {
        this.employeeEmail = employeeEmail;
        this.customerEmail = customerEmail;
        this.action = action;
        this.date = LocalDate.now();
    }

    public Long getActionId() {
        return actionId;
    }

    // Getters and Setters

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public EmployeeActionEnum getAction() {
        return action;
    }

    public void setAction(EmployeeActionEnum action) {
        this.action = action;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
