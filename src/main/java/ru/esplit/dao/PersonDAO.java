package ru.esplit.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.esplit.models.Person;

@Component
public interface PersonDAO {
    List<Person> getAll();
    Person show(int id);
    void save(Person person);
    void update(int id, Person updatePerson);
    void delete(int id);
}
