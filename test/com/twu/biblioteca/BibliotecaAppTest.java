package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppTest {

    OutputStream outputStream;
    private String expectedMenu;
    private String expectedAllBooks;
    private String expectedWelcome;
    private String expectedBye;
    private String expectedAllMovies;
    private String expectedLoggedMenu;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        expectedMenu = String.join("\n",
                "",
                "====================",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(3) Return Book",
                "(4) List Movies",
                "(5) Checkout Movie",
                "(8) Login",
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");
        expectedLoggedMenu = String.join("\n",
                "",
                "====================",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(3) Return Book",
                "(4) List Movies",
                "(5) Checkout Movie",
                "(8) User Information",
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");
        expectedAllBooks = String.join("\n",
                "",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013");
        expectedAllMovies = String.join("\n",
                "",
                "Movie1\tDirector1\t2003\t8",
                "Movie2\tDirector2\t2005\t5",
                "Movie3\tDirector3\t2013\tunrated");
        expectedWelcome = "\nWelcome to Biblioteca!";
        expectedBye = "\nGood bye.\n";
    }

    @Test
    public void biblioteca_should_do_validation_in_menu() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Select a valid option!",
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("0\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_list_books_in_menu() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                expectedAllBooks,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("1\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_checkout_a_available_book() {
        String expectedBooksWithoutBook2 = "\nBook1\tAuthor1\t2003\nBook3\tAuthor3\t2013";
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "Thank you! Enjoy the book",
                expectedMenu,
                expectedBooksWithoutBook2,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("2\nBook2\n1\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_a_invalid_message_when_checkout_a_non_exist_book() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "That book is not available.",
                expectedMenu,
                expectedAllBooks,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("2\nBook4\n1\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_a_invalid_message_when_checkout_a_unavailable_book() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "Thank you! Enjoy the book",
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "That book is not available.",
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("2\nBook2\n2\nBook2\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_return_a_checkouted_book() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "Thank you! Enjoy the book",
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "Thank you for returning the book.",
                expectedMenu,
                expectedAllBooks,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("2\nBook2\n3\nBook2\n1\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_a_invalid_message_when_return_a_non_exist_book() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "That is not a valid book to return.",
                expectedMenu,
                expectedBye);

        String inputSeq = "3\nBook4\n9\n";

        BibliotecaApp.biblioteca(new Scanner(inputSeq), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_list_movies_in_menu() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                expectedAllMovies,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("4\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_checkout_a_available_movie() {
        String expectedMoviesWithoutMovie2 = "\nMovie1\tDirector1\t2003\t8\nMovie3\tDirector3\t2013\tunrated";
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the movie name: ",
                "",
                "Thank you! Enjoy the movie",
                expectedMenu,
                expectedMoviesWithoutMovie2,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("5\nMovie2\n4\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_a_invalid_message_when_checkout_a_non_exist_movie() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the movie name: ",
                "",
                "That movie is not available.",
                expectedMenu,
                expectedAllMovies,
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("5\nMovie4\n4\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_a_invalid_message_when_checkout_a_unavailable_movie() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input the movie name: ",
                "",
                "Thank you! Enjoy the movie",
                expectedMenu,
                "",
                "Please input the movie name: ",
                "",
                "That movie is not available.",
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("5\nMovie2\n5\nMovie2\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_successfully_login_and_show_logged_menu() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input your library number: ",
                "",
                "Please input your password: ",
                "",
                "Login success.",
                expectedLoggedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("8\n111-1111\nadmin\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_give_an_invalid_message_when_login_failed() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input your library number: ",
                "",
                "Please input your password: ",
                "",
                "Login failed.",
                expectedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("8\n111-1111\nwrong_pass\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void biblioteca_should_be_able_to_show_user_info_when_logged() {
        String expectedOutput = String.join("\n",
                expectedWelcome,
                expectedMenu,
                "",
                "Please input your library number: ",
                "",
                "Please input your password: ",
                "",
                "Login success.",
                expectedLoggedMenu,
                "",
                "====================",
                "Name: ADMIN",
                "E-mail: ADDRESS1",
                "Phone: 11111111",
                "--------------------",
                expectedLoggedMenu,
                expectedBye);
        BibliotecaApp.biblioteca(new Scanner("8\n111-1111\nadmin\n8\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }
}
