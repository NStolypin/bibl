package ru.esplit.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.esplit.models.Book;
import ru.esplit.models.Person;

@Component
public interface BookDAO {
    List<Book> getAll();
    Optional<Book> show(int book_id);
    void save(Book book);
    void update(int book_id, Book updateBook);
    void delete(int book_id);
    void assign(int book_id, Person selectedPerson);
    void release(int book_id);
    Optional<Person> getBookOwner(int id);
}
