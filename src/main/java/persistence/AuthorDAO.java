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
        connect();
        Author author = em.find(Author.class, id);
        disconnect();
        if (!author.getActive()) {
            throw new NoResultException("The author is not active");
        }
        return author;
    }

    @Override
    public Author findByName(String name) throws Exception {
        connect();
        Author author = (Author) em.createQuery("SELECT a FROM Author a WHERE a.name LIKE :name")
                .setParameter("name", name).getSingleResult();
        disconnect();
        if (!author.getActive()) {
            throw new NoResultException("The author is not active");
        }
        return author;
    }

    public List<Author> findAll() throws Exception {
        connect();
        List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        disconnect();
        return authors.stream().filter(Author::getActive).toList();
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
