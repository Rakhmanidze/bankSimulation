package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransactionDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(TransactionDao.class.getName());

    public TransactionDao(EntityManager em) {
        this.em = em;
    }

    public Optional<Transaction> get(long id) {
        try {
            Optional<Transaction> result = Optional.ofNullable(em.find(Transaction.class, id));
            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved Transaction with id: " + id);
            } else {
                LOGGER.warning("No Transaction found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Transaction with id " + id, e);
        }
    }

    public List<Transaction> getAll() {
        try {
            List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
            LOGGER.info("Successfully retrieved all Transactions. Total count: " + transactions.size());
            return transactions;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Transactions", e);
        }
    }

    public void create(Transaction transaction) {
        EntityTransaction transactionE = em.getTransaction();
        try {
            transactionE.begin();
            em.persist(transaction);
            transactionE.commit();
            LOGGER.info("Successfully created Transaction with id: " + transaction.getTransaction_id());
        } catch (Exception e) {
            if (transactionE.isActive()) {
                transactionE.rollback();
            }
            throw new RuntimeException("Failed to create Transaction", e);
        }
    }

    public void delete(long id) {
        EntityTransaction transactionE = em.getTransaction();
        try {
            transactionE.begin();

            Optional<Transaction> transaction = get(id);

            if (transaction.isPresent()) {
                em.remove(transaction.get());
                transactionE.commit();
                LOGGER.info("Successfully deleted Transaction with id: " + id);
            } else {
                LOGGER.warning("Attempted to delete non-existent Transaction with id: " + id);
            }


        } catch (Exception e) {
            if (transactionE.isActive()) {
                transactionE.rollback();
            }
            throw new RuntimeException("Error deleting Transaction with id " + id, e);
        }
    }
}
