package cvut.fit.web_lib.DTO;

import cvut.fit.web_lib.entities.Author;
import lombok.Data;

@Data
public class AuthorDTO {
    private Long idAuthor;
    private String name;
    private String surname;
    private String nationality;
    private Integer age;

    public AuthorDTO(Author author) {
        this.idAuthor = author.getIdAuthor();
        this.name = author.getName();
        this.surname = author.getSurname();
        this.nationality = author.getNationality();
        this.age = author.getAge();
    }

    public AuthorDTO() {

    }
}
