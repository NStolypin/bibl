package ru.esplit.controllers;

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
import ru.esplit.models.Book;
import ru.esplit.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAOImpl bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/getall";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model) {
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/show";
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
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
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
}
