package persistence;

import entities.Author;
import interfaces.APDao;
import jakarta.persistence.NoResultException;

import java.util.List;

public class AuthorDAO extends DAO<Author> implements APDao<Author> {

    public void testMethod() {
        connect();
        System.out.println("delegate: " + em.getDelegate());
        System.out.println("criteria builder: " + em.getCriteriaBuilder());
        System.out.println("metamodel: " + em.getMetamodel());
        System.out.println("delegate: " + em.getClass());
        disconnect();
    }
    @Override
    public void save(Author author) throws Exception {
        super.save(author);
    }

    public Author findById(Integer id) throws Exception {
        try {
            connect();
            Author author = em.find(Author.class, id);
            disconnect();
            if (!author.getActive()) {
                throw new NoResultException("The author is not active");
            }
            return author;
        } catch (NoResultException e) {
            System.out.println("Couldn't find any Author with the given id");
            throw e;
        }
    }

    @Override
    public Author findByName(String name) throws Exception {
        try {
            connect();
            Author author = (Author) em.createQuery("SELECT a FROM Author a WHERE a.name LIKE :name")
                    .setParameter("name", name).getSingleResult();
            disconnect();
            if (!author.getActive()) {
                throw new NoResultException("The author is not active");
            }
            return author;
        } catch (NoResultException e) {
            System.out.println("The Author is not present in the database, please create it in the Author menu");
            throw e;
        }
    }

    public List<Author> findAll() throws Exception {
        try {
            connect();
            List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
            disconnect();
            return authors.stream().filter(Author::getActive).toList();
        } catch (NoResultException e) {
            System.out.println("Couldn't find any Authors, please create them in the Author menu");
            throw e;
        }
    }

    @Override
    public void edit(Author author) throws Exception {
        super.edit(author);
    }

    @Override
    public void delete(Author author) throws Exception {
        super.delete(author);
    }
}
