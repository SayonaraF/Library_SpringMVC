package ru.broyaka.library.models;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class Book {
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 50, message = "Название не может быть длиннее 50-ти символов")
    private String name;
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 40, message = "Имя автора должно быть от 2 до 40 символов")
    @Pattern(regexp = "(?U)[А-Я]\\w+ [А-Я]\\.", message = "Привер ввода: Сапковский А.")
    private String author;
    @Range(min = 1, max = 2022, message = "Приме ввода: 1997")
    private int year;
    private int personId;
    private int id;

}
