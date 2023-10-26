package persistence;

import entities.Publisher;

import java.util.List;

public class PublisherDAO extends DAO<Publisher>{

    @Override
    public void save(Publisher publisher) throws Exception {
        super.save(publisher);
    }

    public Publisher findById(Integer id) throws Exception {
        connect();
        Publisher publisher = em.find(Publisher.class, id);
        disconnect();
        return publisher;
    }

    public Publisher findByName(String name) {
        connect();
        Publisher publisher = (Publisher) em.createQuery("SLECT p FROM Publiser p WHERE p.name LIKE :name")
                .setParameter("name", name).getSingleResult();
        disconnect();
        return publisher;
    }

    public List<Publisher> findAll() {
        connect();
        List<Publisher> publishers = em.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
        disconnect();
        return publishers;
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
