package ru.broyaka.library.models;

import javax.validation.constraints.*;

public class Person {
    @NotEmpty(message = "ФИО не может быть пустым")
    @Size(min = 2, max = 40, message = "ФИО должно быть от 2 до 40 символов")
    private String name;
    @Max(value = 2023, message = "Год не может превышать текущий")
    @Min(value = 0, message = "Год не может быть отрицательным")
    private int birthday;
    private int id;

    public Person() {
    }

    public Person(String name, int birthday, int id) {
        this.name = name;
        this.birthday = birthday;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }
}
