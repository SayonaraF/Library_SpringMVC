package ru.broyaka.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Person.class, id);
    }

    @Transactional
    public void create(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    @Transactional
    public void update(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(person);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.delete(person);
    }

    @Transactional
    public List<Book> personBooks(int personId) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(personId);
        Person person = session.get(Person.class, personId);

        // не работает без этой строчки, пока не понял как исправить
        System.out.println(person.getBooks());
        return person.getBooks();
    }
}
