package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.PaymentCard;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PaymentCardDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(PaymentCardDao.class.getName());

    public PaymentCardDao(EntityManager em) {
        this.em = em;
    }

    public Optional<PaymentCard> get(Long id) {
        try {
            Optional<PaymentCard> result = Optional.ofNullable(em.find(PaymentCard.class, id));
            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved PaymentCard with id: " + id);
            } else {
                LOGGER.warning("No PaymentCard found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving PaymentCard with id " + id, e);
        }
    }


    public List<PaymentCard> getAll() {
        try {
            List<PaymentCard> paymentCards = em.createQuery("SELECT p FROM PaymentCard p", PaymentCard.class).getResultList();
            LOGGER.info("Successfully retrieved all PaymentCards.");
            return paymentCards;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all PaymentCards", e);
        }
    }

    public void create(PaymentCard paymentCard) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(paymentCard);
            transaction.commit();
            LOGGER.info("Successfully created PaymentCard with card number: " + paymentCard.getCardNumber());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create PaymentCard", e);
        }
    }

    public void update(PaymentCard paymentCard) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(paymentCard);
            transaction.commit();
            LOGGER.info("Successfully updated PaymentCard with card number: " + paymentCard.getCardNumber());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update PaymentCard", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<PaymentCard> paymentCard = get(id);
            paymentCard.ifPresent(em::remove);
            transaction.commit();
            LOGGER.info("Successfully deleted PaymentCard with id: " + id);
        }
        catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting PaymentCard with id " + id, e);
        }
    }

    public Optional<PaymentCard> findByCardNumber(long cardNumber) {
        try {
            TypedQuery<PaymentCard> query = em.createQuery("SELECT a FROM PaymentCard a WHERE a.cardNumber = :cardNumber", PaymentCard.class);
            query.setParameter("cardNumber", cardNumber);
            Optional<PaymentCard> result = Optional.of(query.getSingleResult());
            LOGGER.info("Successfully found PaymentCard with card number: " + cardNumber);
            return result;
        } catch (NoResultException e) {
            LOGGER.warning("No PaymentCard found with card number: " + cardNumber);
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding PaymentCard by card number: " + cardNumber, e);
        }
    }
}