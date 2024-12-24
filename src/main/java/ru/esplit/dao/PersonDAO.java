package ru.esplit.dao;

import java.util.List;

import ru.esplit.models.Person;

public interface PersonDAO {
    List<Person> getAll();
    Person show(int id);
    void save(Person person);
    void update(int id, Person updatePerson);
    void delete(int id);
}
