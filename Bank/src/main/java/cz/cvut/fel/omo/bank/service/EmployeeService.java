package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.dao.EmployeeDao;
import cz.cvut.fel.omo.bank.model.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeService {

    private final EmployeeDao dao;

    public EmployeeService(EmployeeDao employeeDao) {
        dao =employeeDao;
    }

    public Optional<Employee> getEmployee(Long id) {
        return dao.get(id);
    }

    public void createEmployee(Employee employee) {
        Optional<Employee> existingEmployee = dao.findByEmail(employee.getEmail());
        if (existingEmployee.isEmpty()) {
            dao.create(employee);
        }
    }

    public void updateEmployee(Employee employee) {
            dao.update(employee);
    }

    public void deleteEmployee(Long id) {
        dao.delete(id);
    }

    public List<Employee> getAllEmployees() {
        return dao.getAll();
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return dao.findByEmail(email);
    }
}