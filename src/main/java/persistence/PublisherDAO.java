package persistence;

import entities.Publisher;
import interfaces.APDao;
import jakarta.persistence.NoResultException;

import java.util.List;

public class PublisherDAO extends DAO<Publisher> implements APDao<Publisher> {

    @Override
    public void save(Publisher publisher) throws Exception {
        super.save(publisher);
    }

    public Publisher findById(Integer id) throws Exception {
        connect();
        Publisher publisher = em.find(Publisher.class, id);
        disconnect();
        if (!publisher.getActive()) {
            throw new NoResultException("The publisher is not active");
        }
        return publisher;
    }

    public Publisher findByName(String name) {
        connect();
        Publisher publisher = (Publisher) em.createQuery("SLECT p FROM Publiser p WHERE p.name LIKE :name")
                .setParameter("name", name).getSingleResult();
        disconnect();
        if (!publisher.getActive()) {
            throw new NoResultException("The publisher is not active");
        }
        return publisher;
    }

    public List<Publisher> findAll() {
        connect();
        List<Publisher> publishers = em.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
        disconnect();
        return publishers.stream().filter((Publisher::getActive)).toList();
    }


    @Override
    public void delete(Publisher publisher) throws Exception {
        super.delete(publisher);
    }

    @Override
    public void edit(Publisher publisher) throws Exception {
        super.edit(publisher);
    }
}
