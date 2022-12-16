package ru.broyaka.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.broyaka.library.models.Book;
import ru.broyaka.library.models.Person;
import ru.broyaka.library.services.BooksService;
import ru.broyaka.library.services.PeopleService;
import ru.broyaka.library.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    public BookController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/booksIndex";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @PatchMapping("/{id}/add")
    public String choosePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.setPerson(id, person.getId());
        return ("redirect:/books/{id}");
    }

    @DeleteMapping("/{id}/release")
    public String releaseBook(@PathVariable int id) {
        booksService.returnBook(id);
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
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/update";
        }
        booksService.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
