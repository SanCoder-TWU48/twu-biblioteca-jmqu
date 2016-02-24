package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppTest {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void print_should_output_the_message() {
        String message = "test";
        BibliotecaApp.print(message);
        assertEquals(message + '\n', outputStream.toString());
    }

    @Test
    public void main_should_output_correctly() {
        BibliotecaApp.main(new String[0]);
        String expectedOutput = String.join("\n",
                "Welcome to Biblioteca!",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013"
                ) + "\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @After
    public void clearUp() {
        System.setOut(null);
    }
}
