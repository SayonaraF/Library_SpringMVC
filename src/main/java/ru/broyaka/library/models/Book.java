package ru.broyaka.library.models;


import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "project1", name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 50, message = "Название не может быть длиннее 50-ти символов")
    private String name;

    @Column(name = "author")
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 40, message = "Имя автора должно быть от 2 до 40 символов")
    @Pattern(regexp = "(?U)[А-Я]\\w+ [А-Я]\\.", message = "Пример ввода: Сапковский А.")
    private String author;

    @Column(name = "year")
    @Range(min = 1, max = 2022, message = "Пример ввода: 1997")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public boolean hasOwner() {
        return getOwner() != null;
    }
}
