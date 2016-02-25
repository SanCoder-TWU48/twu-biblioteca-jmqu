package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaServiceTest {

    private BibliotecaService bibliotecaService = new BibliotecaService();

    @Test
    public void welcome_should_give_a_welcome_message() {
        assertEquals("Welcome to Biblioteca!", bibliotecaService.welcome());
    }

    @Test
    public void showMenu_should_give_a_menu() {
        String expectedOutput = String.join("\n",
                "====================",
                "        Menu        ",
                "--------------------",
                "(1) List Books",
                "(2) Quit",
                "--------------------");
        assertEquals(expectedOutput, bibliotecaService.showMenu());
    }

    @Test
    public void dispatcher_should_validate_input() {
        assertEquals("Select a valid option!", bibliotecaService.dispatcher("0"));
    }

    @Test
    public void dispatcher_should_be_able_to_give_a_list_of_books_with_detail() {
        assertEquals(
                "Book1\tAuthor1\t2003\n" +
                "Book2\tAuthor2\t2005\n" +
                "Book3\tAuthor3\t2013",
                bibliotecaService.dispatcher("1"));
    }

    @Test
    public void dispatcher_should_be_able_to_quit() {
        assertEquals(null, bibliotecaService.dispatcher("2"));
    }
}
