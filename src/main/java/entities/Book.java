package entities;

import jakarta.persistence.*;

@Entity(name = "Book")
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Long isbn;
    private String title;
    private Integer year;
    private Integer instances;
    private Integer lendInstances;
    private Integer leftInstances;
    private Boolean isActive;
    @ManyToOne
    private Author author;
    @ManyToOne
    private Publisher publisher;


    public Book() {
        this.isActive = true;
    }

    public Book(Integer id, Long isbn, String title, Integer year, Integer instances, Integer lendInstances, Integer leftInstances, Author author, Publisher publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.instances = instances;
        this.lendInstances = lendInstances;
        this.leftInstances = leftInstances;
        this.isActive = true;
        //this.author = author;
        //this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getInstances() {
        return instances;
    }

    public void setInstances(Integer instances) {
        this.instances = instances;
    }

    public Integer getLendInstances() {
        return lendInstances;
    }

    public void setLendInstances(Integer lendInstances) {
        this.lendInstances = lendInstances;
    }

    public Integer getLeftInstances() {
        return leftInstances;
    }

    public void setLeftInstances(Integer leftInstances) {
        this.leftInstances = leftInstances;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
/*
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

 */
}