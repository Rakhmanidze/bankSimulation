package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AccountDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(AccountDao.class.getName());

    public AccountDao(EntityManager em) {
        this.em = em;
    }

    public Optional<Account> get(Long id) {
        try {
            Optional<Account> result = Optional.ofNullable(em.find(Account.class, id));

            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved Account with id: " + id);
            } else {
                LOGGER.warning("No Account found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Account with id " + id, e);
        }
    }

    public List<Account> getAll() {
        try {
            List<Account> accounts = em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
            LOGGER.info("Successfully retrieved all Accounts.");
            return accounts;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Accounts", e);
        }
    }

    public void create(Account account) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(account);
            transaction.commit();
            LOGGER.info("Successfully created Account with id: " + account.getAccount_id());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create Account", e);
        }
    }

    public void update(Account account) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(account);
            transaction.commit();
            LOGGER.info("Successfully updated Account with id: " + account.getAccount_id());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update Account", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<Account> account = get(id);
            account.ifPresent(em::remove);
            transaction.commit();
            LOGGER.info("Successfully deleted Account with id: " + id);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting Account with id " + id, e);
        }
    }

    public Optional<Account> findByAccountNumber(int accountNumber) {
        try {
            TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber", Account.class);
            query.setParameter("accountNumber", accountNumber);
            Optional<Account> result = Optional.of(query.getSingleResult());

            LOGGER.info("Successfully found Account by account number: " + accountNumber);
            return result;
        } catch (NoResultException e) {
            LOGGER.warning("No Account found with account number: " + accountNumber);
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Account by account number: " + accountNumber, e);
        }
    }
}