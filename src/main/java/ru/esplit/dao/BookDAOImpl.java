package ru.esplit.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.esplit.models.Book;
import ru.esplit.models.Person;

@Component
public class BookDAOImpl implements BookDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Optional<Book> show(int book_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), book_id)
                .stream().findAny();
    }

    @Override
    public void save(Book book) {
        // TODO Auto-generated method stub
        jdbcTemplate.update("INSERT INTO Book(title) VALUES(?)", book.getTitle());
    }

    @Override
    public void update(int book_id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET title=? WHERE book_id=?", updateBook.getTitle(), book_id);
    }

    @Override
    public void delete(int book_id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }

    @Override
    public void assign(int book_id, Person selectedPerson) {
       jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", selectedPerson.getPerson_id(), book_id);
    }

    @Override
    public void release(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", book_id);
    }

    @Override
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.person_id " +
                "WHERE Book.book_id=?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }
}
