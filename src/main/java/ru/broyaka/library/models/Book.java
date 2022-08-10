package ru.broyaka.library.models;

import javax.validation.constraints.*;

public class Book {
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(max = 50, message = "Название не может быть длиннее 50-ти символов")
    private String name;
    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 40, message = "Имя автора должно быть от 2 до 40 символов")
    private String author;
    @Max(value = 2023, message = "Год не может превышать текущий")
    @Positive(message = "Год не может быть отрицательным")
    private int year;
    private int id;
    private int person_id;

    public Book() {
    }

    public Book(String name, String author, int year, int id, int person_id) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.id = id;
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
