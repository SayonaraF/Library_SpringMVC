package ru.broyaka.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;
import ru.broyaka.library.repositories.BooksRepository;
import ru.broyaka.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book> findByName(String name) {
        return booksRepository.findByName(name);
    }

    public void setPerson(int id, int personId) {
        Book book = booksRepository.findById(id).orElse(null);
        Person person = peopleRepository.findById(personId).orElse(null);

        if (book != null) {
            book.setOwner(person);
        }
    }

    public void returnBook(int id) {
        booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }

    public void save(Book book) {
        booksRepository.save(book);
    }

    public void update(Book book) {
        booksRepository.save(book);
    }

    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
