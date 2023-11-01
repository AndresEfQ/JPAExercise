package persistence;

import entities.Loan;
import interfaces.BLCDao;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class LoanDAO extends DAO<Loan> implements BLCDao<Loan> {
    @Override
    public void save(Loan loan) throws Exception {
        super.save(loan);
    }

    public Loan findById(Integer id) throws PersistenceException {
        try {
            connect();
            Loan loan = em.find(Loan.class, id);
            disconnect();
            return loan;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any loan with the given id");
            throw e;
        }
    }

    public List<Loan> findByProperty(String property, String value) throws PersistenceException {
        try {
            String query = "SELECT l FROM Loan l WHERE l." + property + " LIKE :property";
            connect();
            List<Loan> loans = em.createQuery(query, Loan.class).setParameter("property", value).getResultList();
            disconnect();
            return loans;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any loans with the given property");
            throw e;
        }
    }

    public List<Loan> findAll() throws PersistenceException {
        try {
            connect();
            List<Loan> loans = em.createQuery("SELECT b FROM Loan b", Loan.class).getResultList();
            disconnect();
            return loans;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any loans, please create them from the loan menu");
            throw e;
        }
    }

    @Override
    public void edit(Loan loan) throws Exception {
        super.edit(loan);
    }

    @Override
    public void delete(Loan loan) throws Exception {
        super.delete(loan);
    }
}
