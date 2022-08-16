package ru.broyaka.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private static final String SELECT_PEOPLE = "SELECT * FROM Person";
    private static final String SELECT_PERSON = "SELECT * FROM person WHERE id=?";
    private static final String NEW_PERSON = "INSERT INTO Person(name, birthday) VALUES (?, ?)";
    private static final String UPDATE_PERSON = "UPDATE Person SET name=?, birthday=? WHERE id=?";
    private static final String UPDATE_PERSON_ID = "UPDATE Book SET person_id=null WHERE person_id=?";
    private static final String DELETE_PERSON = "DELETE FROM Person WHERE id=?";
    private static final String SELECT_PERSON_BOOKS = "SELECT * FROM Book WHERE person_id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(SELECT_PEOPLE, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query(SELECT_PERSON, new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void create(Person person) {
        jdbcTemplate.update(NEW_PERSON, person.getName(), person.getBirthday());
    }

    public void update(Person person) {
        jdbcTemplate.update(UPDATE_PERSON, person.getName(), person.getBirthday(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update(UPDATE_PERSON_ID, id);
        jdbcTemplate.update(DELETE_PERSON, id);
    }

    public List<Book> personBooks(int personId) {
        return jdbcTemplate.query(SELECT_PERSON_BOOKS, new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
