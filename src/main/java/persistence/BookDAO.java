package persistence;

import entities.Book;
import interfaces.BLCDao;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class BookDAO extends DAO<Book> implements BLCDao<Book> {

    @Override
    public void save(Book book) throws Exception {
        super.save(book);
    }

    public Book findById(Integer id) throws PersistenceException {
        try {
            connect();
            Book book = em.find(Book.class, id);
            disconnect();
            if (!book.getActive()) {
                throw new NoResultException("The book is not active");
            }
            return book;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any book with the given id");
            throw e;
        }
    }

    public List<Book> findByProperty(String property, String value) throws PersistenceException {
        try {
            String query = "SELECT b FROM Book b WHERE b." + property + " LIKE :property";
            connect();
            List<Book> books = em.createQuery(query, Book.class).setParameter("property", value).getResultList();
            disconnect();
            return books.stream().filter(Book::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any books with the given property");
            throw e;
        }
    }

    public List<Book> findAll() throws PersistenceException {
        try {
            connect();
            List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
            disconnect();
            return books.stream().filter(Book::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any books, please create them from the book menu");
            throw e;
        }
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
