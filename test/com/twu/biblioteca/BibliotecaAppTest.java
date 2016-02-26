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

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void biblioteca_should_output_correctly() {
        String expectedMenu = String.join("\n",
                "",
                "====================",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");

        String expectedAllBooks = String.join("\n",
                "",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013");

        String expectedBooksWithoutBook2 = String.join("\n",
                "",
                "Book1\tAuthor1\t2003",
                "Book3\tAuthor3\t2013");

        String expectedOutput = String.join("\n",
                "",
                "Welcome to Biblioteca!",
                expectedMenu,
                "",
                "Select a valid option!",
                expectedMenu,
                expectedAllBooks,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "That book is not available.",
                expectedMenu,
                expectedAllBooks,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "Thank you! Enjoy the book",
                expectedMenu,
                expectedBooksWithoutBook2,
                expectedMenu,
                "",
                "Please input the book name: ",
                "",
                "That book is not available.",
                expectedMenu,
                expectedBooksWithoutBook2,
                expectedMenu,
                "",
                "Good bye."
        ) + "\n";

        BibliotecaApp.biblioteca(new Scanner("0\n1\n2\nBook4\n1\n2\nBook2\n1\n2\nBook2\n1\n9\n"), new PrintStream(outputStream));
        assertEquals(expectedOutput, outputStream.toString());
    }
}
