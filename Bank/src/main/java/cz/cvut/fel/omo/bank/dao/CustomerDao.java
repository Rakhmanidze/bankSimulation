package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CustomerDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(BankDao.class.getName());

    public CustomerDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<Customer> get(Long id) {
        try {
            Optional<Customer> result = Optional.ofNullable(em.find(Customer.class, id));
            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved Customer with id: " + id);
            } else {
                LOGGER.warning("No Customer found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Customer with id " + id, e);
        }
    }

    public List<Customer> getAll() {
        try {
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            LOGGER.info("Successfully retrieved all Customers.");
            return customers;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Customers", e);
        }
    }

    public void create(Customer customer) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(customer);
            transaction.commit();
            LOGGER.info("Successfully created Customer: " + customer.getEmail());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create Customers", e);
        }
    }

    public void update(Customer customer) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(customer);
            transaction.commit();
            LOGGER.info("Successfully updated Customer with id: " + customer.getId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update Customer", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Customer> customer = get(id);
            customer.ifPresent(em::remove);
            transaction.commit();
            LOGGER.info("Successfully deleted Customer with id: " + id);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting Customer with id " + id, e);
        }
    }

    public Optional<Customer> findByEmail(String email) {
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class);
            query.setParameter("email", email);
            Optional<Customer> result = Optional.of(query.getSingleResult());
            LOGGER.info("Successfully found Customer by email: " + email);
            return result;
        } catch (NoResultException e) {
            LOGGER.warning("No Customer found with email: " + email);
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Customer by email: " + email, e);
        }
    }
}