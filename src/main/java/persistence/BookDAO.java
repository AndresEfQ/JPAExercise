package persistence;

import entities.Book;

import java.util.List;

public class BookDAO extends DAO<Book> {

    @Override
    public void save(Book book) throws Exception {
        super.save(book);
    }

    public Book findById(Integer id) throws Exception {
        connect();
        Book book = em.find(Book.class, id);
        disconnect();
        return book;
    }

    public List<Book> findByParameter(String parameter, String value) throws Exception {
        String query = "SELECT b FROM Book b WHERE b." + parameter + " LIKE :parameter";
        connect();
        List<Book> books = em.createQuery(query, Book.class).setParameter("parameter", value).getResultList();
        disconnect();
        return books;
    }

    public List<Book> findAll() throws Exception {
        connect();
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        disconnect();
        return books;
    }

    @Override
    public void edit(Book book) throws Exception {
        super.edit(book);
    }

    @Override
    public void delete(Book book) throws Exception {
        super.delete(book);
    }
}
