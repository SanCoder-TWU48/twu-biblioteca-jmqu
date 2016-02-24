package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaServiceTest {

    @Test
    public void welcome_should_give_a_welcome_message() {
        assertEquals("Welcome to Biblioteca!", BibliotecaService.welcome());
    }

    @Test
    public void showBookList_should_give_a_list_of_books() {
        assertEquals("Book1\n" +
                "Book2\n" +
                "Book3", BibliotecaService.showBookList());
    }
}
