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
public class PersonDAOImpl implements PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Optional<Person> show(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class), person_id)
                .stream().findAny();
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name) VALUES(?)", person.getName());
    }

    @Override
    public void update(int person_id, Optional<Person> updatePerson) {
        if(updatePerson.isPresent()) {
            jdbcTemplate.update("UPDATE Person SET name=? WHERE person_id=?", updatePerson.get().getName(), person_id);
        }
    }

    @Override
    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", person_id);
    }

    @Override
    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new BeanPropertyRowMapper<>(Book.class), id);
    }
}
