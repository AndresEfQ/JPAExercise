package entities;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;

@Entity(name = "Loan")
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Date lentDate;
    private Date dueDate;
    @OneToMany
    private Book[] books;
    @OneToOne
    private Client client;
    private Boolean isActive;

    public Loan() {
        this.isActive = true;
    }

    public Loan(Date lentDate, Date dueDate, Book[] books, Client client) {
        this.lentDate = lentDate;
        this.dueDate = dueDate;
        this.books = books;
        this.client = client;
        this.isActive = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLentDate() {
        return lentDate;
    }

    public void setLentDate(Date lentDate) {
        this.lentDate = lentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", lentDate=" + lentDate +
                ", dueDate=" + dueDate +
                ", books=" + Arrays.toString(books) +
                ", client=" + client +
                ", isActive=" + isActive +
                " }";
    }
}