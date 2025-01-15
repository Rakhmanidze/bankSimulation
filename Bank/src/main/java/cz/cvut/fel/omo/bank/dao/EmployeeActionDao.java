package cz.cvut.fel.omo.bank.dao;

import cz.cvut.fel.omo.bank.model.EmployeeAction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeActionDao {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(EmployeeActionDao.class.getName());

    public EmployeeActionDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<EmployeeAction> get(Long id) {
        try {
            Optional<EmployeeAction> result = Optional.ofNullable(em.find(EmployeeAction.class, id));
            if (result.isPresent()) {
                LOGGER.info("Successfully retrieved EmployeeAction with id: " + id);
            } else {
                LOGGER.warning("No EmployeeAction found with id: " + id);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving EmployeeAction with id " + id, e);
        }
    }

    public List<EmployeeAction> getAll() {
        try {
            List<EmployeeAction> actions = em.createQuery("SELECT a FROM EmployeeAction a", EmployeeAction.class).getResultList();
            LOGGER.info("Successfully retrieved all EmployeeActions. Total count: " + actions.size());
            return actions;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving EmployeeActions ", e);
        }
    }

    public void create(EmployeeAction action) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(action);
            transaction.commit();
            LOGGER.info("Successfully created EmployeeAction for employee: " + action.getEmployeeEmail() + ", customer: " + action.getCustomerEmail());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create EmployeeAction", e);
        }
    }

    public void update(EmployeeAction action) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(action);
            transaction.commit();
            LOGGER.info("Successfully updated EmployeeAction with id: " + action.getActionId());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update EmployeeAction", e);
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Optional<EmployeeAction> action = get(id);
            if (action.isPresent()) {
                em.remove(action.get());
                transaction.commit();
                LOGGER.info("Successfully deleted EmployeeAction with id: " + id);

            } else {
                LOGGER.warning("Attempted to delete non-existent EmployeeAction with id: " + id);
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting EmployeeAction with id " + id, e);
        }
    }

    public List<EmployeeAction> getByEmployeeEmail(String employeeEmail) {
        try {
            TypedQuery<EmployeeAction> query = em.createQuery(
                    "SELECT a FROM EmployeeAction a WHERE a.employeeEmail = :employeeEmail", EmployeeAction.class);
            query.setParameter("employeeEmail", employeeEmail);
            List<EmployeeAction> actions = query.getResultList();
            LOGGER.info("Successfully retrieved EmployeeActions for employee email: " + employeeEmail + ". Total: " + actions.size());
            return actions;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving EmployeeActions by employee email " + employeeEmail, e);
        }
    }

    public List<EmployeeAction> getByCustomerEmail(String customerEmail) {
        try {
            TypedQuery<EmployeeAction> query = em.createQuery(
                    "SELECT a FROM EmployeeAction a WHERE a.customerEmail = :customerEmail", EmployeeAction.class);
            query.setParameter("customerEmail", customerEmail);
            List<EmployeeAction> actions = query.getResultList();
            LOGGER.info("Successfully retrieved EmployeeActions for customer email: " + customerEmail + ". Total: " + actions.size());
            return actions;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving EmployeeActions by customer email " + customerEmail, e);
        }
    }

    public void updateCustomerEmail(String oldEmail, String newEmail) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            int updatedCount = em.createQuery(
                            "UPDATE EmployeeAction a SET a.customerEmail = :newEmail WHERE a.customerEmail = :oldEmail")
                    .setParameter("newEmail", newEmail)
                    .setParameter("oldEmail", oldEmail)
                    .executeUpdate();
            transaction.commit();
            LOGGER.info("Successfully updated customer email from " + oldEmail + " to " + newEmail + " in " + updatedCount + " EmployeeActions.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating CustomerEmail in EmployeeActions table", e);
        }
    }
}
