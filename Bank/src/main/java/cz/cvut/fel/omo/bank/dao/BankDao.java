package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.Bank;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BankDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(BankDao.class.getName());

    public BankDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<Bank> get(Long id) {
        try {
            Optional<Bank> result = Optional.ofNullable(em.find(Bank.class, id));

            if (result.isPresent()) {
                LOGGER.info(String.format("Successfully retrieved Bank with id: ", id));
            } else {
                LOGGER.warning("No Bank found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Bank with id " + id, e);
        }
    }

    public List<Bank> getAll() {
        try {
            List<Bank> banks = em.createQuery("SELECT b FROM Bank b", Bank.class).getResultList();
            LOGGER.info("Successfully retrieved all Banks.");
            return banks;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Banks", e);
        }
    }

    public void create(Bank bank) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(bank);
            transaction.commit();
            LOGGER.info("Successfully created Bank: " + bank.getName());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create Bank", e);
        }
    }

    public void update(Bank bank) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(bank);
            transaction.commit();
            LOGGER.info("Successfully updated Bank with id: " + bank.getId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update Bank", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Bank> bank = get(id);
            bank.ifPresent(em::remove);
            transaction.commit();
            LOGGER.info("Successfully deleted Bank with id: " + id);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting Bank with id " + id, e);
        }
    }

    public Optional<Bank> findByName(String name) {
        try {
            TypedQuery<Bank> query = em.createQuery("SELECT b FROM Bank b WHERE b.name = :name", Bank.class);
            query.setParameter("name", name);
            Optional<Bank> result = Optional.of(query.getSingleResult());

            LOGGER.info("Successfully found Bank by name: " + name);
            return result;
        } catch (NoResultException e) {
            LOGGER.warning("No Bank found with name: " + name);
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Bank by name: " + name, e);
        }
    }
}
