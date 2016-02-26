package com.twu.biblioteca.model;//Created by SanCoder on 2/24/16.

public class Book {
    private String name;
    private String author;
    private String publishedYear;
    private boolean isAvailable;

    public Book(String name, String author, String publishedYear) {
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
        this.isAvailable = true;
    }

    public void checkout() {
        isAvailable = false;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }
}
