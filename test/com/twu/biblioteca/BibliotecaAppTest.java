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
    public void getInput_should_print_hint_and_give_the_input_to_callback() {
        InputHandler inputHandler = new InputHandler("test", input -> "this is a input " + input);

        String output = BibliotecaApp.getInput(new Scanner("test"),
                new PrintStream(outputStream),
                inputHandler);

        assertEquals("\ntest\n", outputStream.toString());
        assertEquals("this is a input test", output);
    }

    @Test
    public void biblioteca_should_output_correctly() {
        BibliotecaApp.biblioteca(new Scanner("0\n1\n2\n"), new PrintStream(outputStream));

        String expectedMenu = String.join("\n",
                "",
                "====================",
                "        Menu        ",
                "--------------------",
                "(1) List Books",
                "(2) Quit",
                "--------------------");
        String expectedOutput = String.join("\n",
                "",
                "Welcome to Biblioteca!",
                expectedMenu,
                "",
                "Please choose (item number):",
                "",
                "Select a valid option!",
                expectedMenu,
                "",
                "Please choose (item number):",
                "",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013",
                expectedMenu,
                "",
                "Please choose (item number):"
        ) + "\n";

        assertEquals(expectedOutput, outputStream.toString());
    }
}
