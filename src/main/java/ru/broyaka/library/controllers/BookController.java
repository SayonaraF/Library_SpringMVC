package ru.broyaka.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.broyaka.library.DAO.BookDAO;
import ru.broyaka.library.DAO.PersonDAO;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;
import ru.broyaka.library.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/booksIndex";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("owner", bookDAO.findPerson(id));
        return "books/show";
    }

    @PatchMapping("/{id}/add")
    public String choosePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.choosePerson(id, person.getId());
        return ("redirect:/books/{id}");
    }

    @DeleteMapping("/{id}/release")
    public String releaseBook(@PathVariable int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";
        bookDAO.create(book);
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/update";
        bookDAO.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
