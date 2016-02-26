package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaServiceTest {

    private BibliotecaService bibliotecaService;

    @Before
    public void setUp() {
        bibliotecaService = new BibliotecaService();
    }

    @Test
    public void should_give_a_welcome_action_when_action_is_null() {
        assertEquals("welcome", bibliotecaService.dispatcher(null).getType());
    }

    @Test
    public void welcome_action_should_show_welcome() {
        Action action = bibliotecaService.dispatcher(null);
        assertEquals("Welcome to Biblioteca!", action.run());
    }

    @Test
    public void should_give_a_menu_action_when_action_is_welcome() {
        Action action = bibliotecaService.dispatcher(new Action("welcome", Action.OUTPUT_ONLY, null));
        assertEquals("show_menu", action.getType());
    }

    @Test
    public void menu_action_should_show_a_menu_and_ask_to_choose() {
        String expectedOutput = String.join("\n",
                "====================",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");
        Action menu = bibliotecaService.dispatcher(new Action("welcome", Action.OUTPUT_ONLY, null));
        assertEquals(expectedOutput, menu.run());
    }

    @Test
    public void should_give_a_invalid_menu_item_action_when_action_is_menu_and_input_is_invalid() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("0");
        assertEquals("invalid_menu_item", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void invalid_menu_item_action_should_show_a_error_message() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("0");
        assertEquals("Select a valid option!", bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_give_a_list_books_action_when_action_is_menu_and_input_is_1() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("1");
        assertEquals("list_books", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void list_books_action_should_show_all_available_books_with_detail() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("1");
        String expectedOutput = String.join("\n",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013");
        assertEquals(expectedOutput, bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_give_a_checkout_book_action_when_action_is_menu_and_input_is_2() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("2");
        assertEquals("checkout_book", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void checkout_book_action_should_ask_for_book_name() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("2");
        assertEquals("Please input the book name: ", bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_checkout_the_input_book_and_give_a_menu_action_when_action_is_a_checkout_book_action() {
        String expectedOutput = String.join("\n",
                "Book1\tAuthor1\t2003",
                "Book3\tAuthor3\t2013");
        Action action = new Action("checkout_book", Action.OUTPUT_INPUT, null);
        action.setInput("Book2");
        Action menuAction = bibliotecaService.dispatcher(action);
        menuAction.setInput("1");
        assertEquals("show_menu", menuAction.getType());
        assertEquals(expectedOutput, bibliotecaService.dispatcher(menuAction).run());
    }

    @Test
    public void should_give_a_quit_action_when_action_is_menu_and_input_is_9() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("9");
        assertEquals("quit", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void quit_action_should_say_good_bye() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("9");
        assertEquals("Good bye.", bibliotecaService.dispatcher(action).run());
    }
}
