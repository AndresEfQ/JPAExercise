package entities;

import jakarta.persistence.*;

@Entity(name = "Author")
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Boolean isActive;

    public Author() {
        this.isActive = true;
    }

    public Author(String name) {
        this.name = name;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "{ id: " + id +
                ", name: '" + name + '\'' +
                ", isActive: " + isActive +
                " }";
    }
}