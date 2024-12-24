package ru.esplit.models;

import jakarta.validation.constraints.NotEmpty;

public class Person {
    private int person_id;

    @NotEmpty(message = "name should not be empty")
    private String name;

    public Person() {}
    
    public Person(int id, String name) {
        this.name = name;
    }

    public int getPerson_id() {
        return this.person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
