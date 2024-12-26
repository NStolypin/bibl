package ru.esplit.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ru.esplit.dao.BookDAO;
import ru.esplit.dao.BookDAOImpl;
import ru.esplit.dao.PersonDAO;
import ru.esplit.dao.PersonDAOImpl;
import ru.esplit.models.Book;
import ru.esplit.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAOImpl bookDAO, PersonDAOImpl personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/getall";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("person") Person person) {
        Optional<Book> bookO = bookDAO.show(book_id);
        if (bookO.isPresent()) {
            Optional<Person> bookOwner = bookDAO.getBookOwner(book_id);
            if(bookOwner.isPresent()) {
                model.addAttribute("owner", bookOwner.get());
            } else {
                model.addAttribute("people", personDAO.getAll());
            }
            model.addAttribute("book", bookO.get());
            return "books/show";
        } else {
            return "redirect:/books";
        }

        
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("pbook") @Valid Book book,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id") int book_id) {
        Optional<Book> bookO = bookDAO.show(book_id);
        if(bookO.isPresent()) {
            model.addAttribute("book", bookO.get());
            return "books/edit";
        } else {
            return "redirect:/books";
        }
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult, @PathVariable("book_id") int book_id) {
        if(bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id) {
        bookDAO.delete(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
