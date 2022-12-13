package ru.broyaka.library.dao;

import lombok.Lombok;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Book.class, id);
    }

    // не хочет работать с Optional, пока не разобрался что делать, когда возвращает null
    @Transactional(readOnly = true)
    public Optional<Book> showSameName(String name) {
        Session session = sessionFactory.getCurrentSession();
        // пока не разобрался как написать без деприкейтед метода поиск не по праймари кей
        Criteria criteria = session.createCriteria(Book.class);
        criteria.add(Restrictions.eq("name", name));

        return Optional.ofNullable((Book) criteria.uniqueResult());
    }

    @Transactional
    public void create(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.delete(book);
    }

    @Transactional
    public void choosePerson(int id, int personId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person person = session.get(Person.class, personId);
        book.setOwner(person);
    }

    @Transactional
    public void releaseBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }
}
