package persistence;

import entities.Book;
import jakarta.persistence.NoResultException;

import java.util.List;

public class BookDAO extends DAO<Book> {

    @Override
    public void save(Book book) throws Exception {
        super.save(book);
    }

    public Book findById(Integer id) throws Exception {
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

    public List<Book> findByParameter(String parameter, String value) throws Exception {
        try {
            String query = "SELECT b FROM Book b WHERE b." + parameter + " LIKE :parameter";
            connect();
            List<Book> books = em.createQuery(query, Book.class).setParameter("parameter", value).getResultList();
            disconnect();
            return books.stream().filter(Book::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any books with the given parameter");
            throw e;
        }
    }

    public List<Book> findAll() throws Exception {
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
