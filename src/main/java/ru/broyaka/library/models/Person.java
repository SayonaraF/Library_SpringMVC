package ru.broyaka.library.models;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


// класс не работает с lombok, создается цикл в методе toString и генерит stackOverFlow, пока не разобрался в причине
@Data
@ToString(exclude = "books")
@Entity
@Table(schema = "project1", name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "ФИО не может быть пустым")
    @Size(min = 2, max = 40, message = "ФИО должно быть от 2 до 40 символов")
    @Pattern(regexp = "(?U)[А-Я]\\w+ [А-Я]\\w+ [А-Я]\\w+", message = "Пример ввода: Иванов Иван Иванович")
    private String name;

    @Column(name = "birthday")
    @Range(min = 1900, max = 2022, message = "Пример ввода: 1997")
    private int birthday;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && birthday == person.birthday && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday);
    }
}
