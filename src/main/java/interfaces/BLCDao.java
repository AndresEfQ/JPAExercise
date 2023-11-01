package interfaces;

import entities.Book;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;

public interface BLCDao<T> {
    public void save(T object) throws Exception;

    public T findById(Integer id) throws PersistenceException;

    public List<T> findByProperty(String property, String value) throws PersistenceException;

    public List<T> findAll() throws PersistenceException;

    public void edit(T object) throws Exception;

    public void delete(T object) throws Exception;
}
