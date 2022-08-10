package ru.broyaka.library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birthday) VALUES (?, ?)", person.getName(), person.getBirthday());
    }

    public void update(Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE id=?", person.getName(), person.getBirthday(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE person_id=?", id);
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> personBooks(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
