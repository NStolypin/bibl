package ru.esplit.models;

import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int book_id;

    @NotEmpty(message="Title shoul not empty")
    private String title;

    public Book() {}
    
    public Book(String title) {
        this.title = title;
    }

    public int getBook_id() {
        return this.book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
