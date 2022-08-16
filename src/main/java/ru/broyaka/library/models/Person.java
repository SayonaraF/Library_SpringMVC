package ru.broyaka.library.models;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class Person {
    @NotBlank(message = "ФИО не может быть пустым")
    @Size(min = 2, max = 40, message = "ФИО должно быть от 2 до 40 символов")
    @Pattern(regexp = "(?U)[А-Я]\\w+ [А-Я]\\w+ [А-Я]\\w+", message = "Пример ввода: Иванов Иван Иванович")
    private String name;
    @Range(min = 1900, max = 2022, message = "Пример ввода: 1997")
    private int birthday;
    private int id;

}
