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
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");
        expectedAllBooks = String.join("\n",
                "",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013");
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
}
