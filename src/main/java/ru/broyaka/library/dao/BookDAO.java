package ru.broyaka.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.broyaka.library.mapper.BookRowMapper;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_BOOKS = "SELECT * FROM book";
    private static final String SELECT_BOOK_ONE = "SELECT * FROM book WHERE id=?";
    private static final String SELECT_BOOK_NAME = "SELECT * FROM book WHERE name=?";
    private static final String NEW_BOOK = "INSERT INTO book(name, author, year) VALUES(?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE book SET name=?, author=?, year=? WHERE id=?";
    private static final String DELETE_BOOK = "DELETE FROM book WHERE id=?";
    private static final String FIND_OWNER = "SELECT person.id, person.name, birthday from person inner join book on person.id = book.person_id where book.id=?";
    private static final String UPDATE_OWNER = "UPDATE book SET person_id=? WHERE id=?";
    private static final String RELEASE_BOOK = "UPDATE book SET person_id=null WHERE id=?";

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(SELECT_BOOKS, new BookRowMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query(SELECT_BOOK_ONE, new Object[]{id},
                        new BookRowMapper()).stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query(SELECT_BOOK_NAME, new Object[]{name},
                        new BookRowMapper()).stream().findAny();
    }

    public void create(Book book) {
        jdbcTemplate.update(NEW_BOOK, book.getName(), book.getAuthor(),
                book.getYear());
    }

    public void update(Book book) {
        jdbcTemplate.update(UPDATE_BOOK, book.getName(), book.getAuthor(),
                book.getYear(), book.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    public Person findPerson(int id) {
        return jdbcTemplate.query(FIND_OWNER,
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void choosePerson(int id, int personId) {
        jdbcTemplate.update(UPDATE_OWNER, personId, id);
    }

    public void releaseBook(int id) {
        jdbcTemplate.update(RELEASE_BOOK, id);
    }
}
