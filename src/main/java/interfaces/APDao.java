package interfaces;

import entities.Author;

import java.util.List;

public interface APDao<T> {
    void save(T object) throws Exception;
    T findById(Integer id) throws Exception;
    T findByName(String name) throws Exception;

    List<T> findAll() throws Exception;
    void edit(T object) throws Exception;
    void delete(T object) throws Exception;
}
