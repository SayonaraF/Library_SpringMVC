package ru.broyaka.library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookRowMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                        new BookRowMapper()).stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM book WHERE name=?", new Object[]{name},
                        new BookRowMapper()).stream().findAny();
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(),
                book.getYear());
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? WHERE id=?", book.getName(), book.getAuthor(),
                book.getYear(), book.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public Person findPerson(int id) {
        return jdbcTemplate.query("SELECT person.id, person.name, birthday from person inner join book on person.id = book.person_id where book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void choosePerson(int id, int person_id) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person_id, id);
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }
}
