package ru.esplit.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.esplit.models.Person;

@Component
public interface PersonDAO {
    List<Person> getAll();
    Optional<Person> show(int person_id);
    void save(Person person);
    void update(int id, Optional<Person> updatePerson);
    void delete(int id);
}
