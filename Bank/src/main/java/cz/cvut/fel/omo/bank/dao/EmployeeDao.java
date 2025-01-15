package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(EmployeeDao.class.getName());

    public EmployeeDao(EntityManager em) {
        this.em = em;
    }

    public Optional<Employee> get(Long id) {
        try {
            Optional<Employee> result = Optional.ofNullable(em.find(Employee.class, id));
            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved Employee with id: " + id);
            } else {
                LOGGER.warning("No Employee found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Employee with id " + id, e);
        }
    }

    public List<Employee> getAll() {
        try {
            List<Employee> employees = em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            LOGGER.info("Successfully retrieved all Employees. Total count: " + employees.size());
            return employees;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Employees", e);
        }
    }

    public void create(Employee employee) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(employee);
            transaction.commit();
            LOGGER.info("Successfully created Employee with email: " + employee.getEmail());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create Employee", e);
        }
    }

    public void update(Employee employee) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(employee);
            transaction.commit();
            LOGGER.info("Successfully updated Employee with id: " + employee.getId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update Employee", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Employee> employee = get(id);
            if (employee.isPresent()) {
                em.remove(employee.get());
                transaction.commit();
                LOGGER.info("Successfully deleted Employee with id: " + id);
            } else {
                transaction.commit();
                LOGGER.warning("Attempted to delete non-existent Employee with id: " + id);
            }

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting Employee with id " + id, e);
        }
    }

    public Optional<Employee> findByEmail(String email) {
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class);
            query.setParameter("email", email);
            Optional<Employee> result = Optional.of(query.getSingleResult());
            LOGGER.info("Successfully found Employee with email: " + email);
            return result;
        } catch (NoResultException e) {
            LOGGER.warning("No Employee found with email: " + email);
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Employee by email: " + email, e);
        }
    }
}
