package cvut.fit.web_lib.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuthor;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> writtenBooks = new HashSet<>();


    public Author() {

    }

    public Author(Long idAuthor, String name, String surname, String nationality, int age) {
        this.idAuthor = idAuthor;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.age = age;
    }


    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }
    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    private String name;
    private String surname;
    private String nationality;
    private Integer age;
}
