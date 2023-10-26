package persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

public abstract class DAO<T> {
    protected final EntityManagerFactory emf = Persistence.createEntityManagerFactory("NPU");
    protected EntityManager em = emf.createEntityManager();

    protected void connect() {
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
    }

    protected void disconnect() {
        if (em.isOpen()) {
            em.close();
        }
    }

    protected void save(T object) throws Exception {
        connect();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        disconnect();
        System.out.println("Successful transaction");
    }

    protected void edit(T object) throws Exception {
        connect();
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
        disconnect();
        System.out.println("Successful transaction");
    }

    protected void delete(T object) throws Exception {
        connect();
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
        disconnect();
        System.out.println("Successful transaction");
    }
}
