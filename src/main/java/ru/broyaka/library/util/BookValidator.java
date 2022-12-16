package ru.broyaka.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (booksService.findByName(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "Книга с таким названием уже существует");
        }
    }
}
