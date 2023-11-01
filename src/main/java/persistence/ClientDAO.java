package persistence;

import entities.Client;
import interfaces.BLCDao;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class ClientDAO extends DAO<Client> implements BLCDao<Client> {
    @Override
    public void save(Client client) throws Exception {
        super.save(client);
    }

    public Client findById(Integer id) throws PersistenceException {
        try {
            connect();
            Client client = em.find(Client.class, id);
            disconnect();
            if (!client.getActive()) {
                throw new NoResultException("The client is not active");
            }
            return client;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any client with the given id");
            throw e;
        }
    }

    public List<Client> findByProperty(String property, String value) throws PersistenceException {
        try {
            String query = "SELECT c FROM Client c WHERE c." + property + " LIKE :property";
            connect();
            List<Client> clients = em.createQuery(query, Client.class).setParameter("property", value).getResultList();
            disconnect();
            return clients.stream().filter(Client::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any clients with the given property");
            throw e;
        }
    }

    public List<Client> findAll() throws PersistenceException {
        try {
            connect();
            List<Client> clients = em.createQuery("SELECT b FROM Client b", Client.class).getResultList();
            disconnect();
            return clients.stream().filter(Client::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any clients, please create them from the client menu");
            throw e;
        }
    }

    @Override
    public void edit(Client client) throws Exception {
        super.edit(client);
    }

    @Override
    public void delete(Client client) throws Exception {
        super.delete(client);
    }
}
