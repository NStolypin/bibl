package ru.esplit.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.esplit.models.Book;

@Component
public interface BookDAO {
    List<Book> getAll();
    Book show(int book_id);
    void save(Book book);
    void update(int book_id, Book updateBook);
    void delete(int book_id);
    void takeTheBook(int book_id);
    void returnTheBook(int book_id);
}
