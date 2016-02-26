package com.twu.biblioteca.model;

import org.junit.Test;

import static org.junit.Assert.*;

//Created by SanCoder on 2/26/16.
public class BookTest {

    @Test
    public void checkout_should_make_book_not_available() {
        Book book = new Book("name", "auther", "year");
        book.checkout();
        assertFalse(book.isAvailable());
    }
}