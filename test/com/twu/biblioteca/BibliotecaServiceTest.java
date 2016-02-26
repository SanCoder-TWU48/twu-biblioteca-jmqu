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
                "(3) Return Book",
                "(4) List Movies",
                "(5) Checkout Movie",
                "(8) Login",
                "(9) Quit",
                "--------------------",
                "Please choose (item number):");
        Action menu = bibliotecaService.dispatcher(new Action("welcome", Action.OUTPUT_ONLY, null));
        assertEquals(expectedOutput, menu.run());
    }

    @Test
    public void menu_action_should_show_a_logged_menu_and_ask_to_choose_when_user_logged() {
        String expectedOutput = String.join("\n",
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
        Action menu = bibliotecaService.dispatcher(new Action("welcome", Action.OUTPUT_ONLY, null).setContext("logged", "true"));
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
    public void should_checkout_the_input_book_and_give_a_checkout_success_action_when_action_is_a_checkout_book_action() {
        String expectedOutput = String.join("\n",
                "Book1\tAuthor1\t2003",
                "Book3\tAuthor3\t2013");
        Action action = new Action("checkout_book", Action.OUTPUT_INPUT, null);
        action.setInput("Book2");
        Action successAction = bibliotecaService.dispatcher(action);
        assertEquals("Thank you! Enjoy the book", successAction.run());
        Action menuAction = bibliotecaService.dispatcher(successAction);
        menuAction.setInput("1");
        assertEquals(expectedOutput, bibliotecaService.dispatcher(menuAction).run());
    }

    @Test
    public void should_give_a_checkout_fail_action_when_book_is_not_available() {
        Action oldCheckoutAction = new Action("checkout_book", Action.OUTPUT_INPUT, null);
        oldCheckoutAction.setInput("Book2");
        Action successAction = bibliotecaService.dispatcher(oldCheckoutAction);
        Action menuAction = bibliotecaService.dispatcher(successAction);
        menuAction.setInput("2");
        Action checkoutAction = bibliotecaService.dispatcher(menuAction);
        checkoutAction.setInput("Book2");
        Action failAction = bibliotecaService.dispatcher(checkoutAction);
        assertEquals("checkout_fail", failAction.getType());
        assertEquals("That book is not available.", failAction.run());
    }

    @Test
    public void should_give_a_return_book_action_when_action_is_menu_and_input_is_3() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("3");
        assertEquals("return_book", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void return_book_action_should_ask_for_book_name() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("3");
        assertEquals("Please input the book name: ", bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_return_the_input_book_and_give_a_return_success_action_when_action_is_a_checkout_book_action() {
        Action checkoutBook = new Action("checkout_book", Action.OUTPUT_INPUT, null);
        checkoutBook.setInput("Book2");
        bibliotecaService.dispatcher(checkoutBook);

        String expectedOutput = String.join("\n",
                "Book1\tAuthor1\t2003",
                "Book2\tAuthor2\t2005",
                "Book3\tAuthor3\t2013");
        Action returnBook = new Action("return_book", Action.OUTPUT_INPUT, null);
        returnBook.setInput("Book2");
        Action returnSuccess = bibliotecaService.dispatcher(returnBook);
        assertEquals("return_success", returnSuccess.getType());
        assertEquals("Thank you for returning the book.", returnSuccess.run());
        Action menuAction = bibliotecaService.dispatcher(returnSuccess);
        menuAction.setInput("1");
        assertEquals(expectedOutput, bibliotecaService.dispatcher(menuAction).run());
    }

    @Test
    public void should_give_a_return_fail_action_when_input_book_name_do_not_exist() {
        Action action = new Action("return_book", Action.OUTPUT_INPUT, null);
        action.setInput("Book4");
        Action returnFail = bibliotecaService.dispatcher(action);
        assertEquals("return_fail", returnFail.getType());
        assertEquals("That is not a valid book to return.", returnFail.run());
    }

    @Test
    public void should_give_a_list_movies_action_when_action_is_menu_and_input_is_4() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("4");
        assertEquals("list_movies", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void list_books_action_should_show_all_available_movies_with_detail() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("4");
        String expectedOutput = String.join("\n",
                "Movie1\tDirector1\t2003\t8",
                "Movie2\tDirector2\t2005\t5",
                "Movie3\tDirector3\t2013\tunrated");
        assertEquals(expectedOutput, bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_give_a_login_action_when_action_is_menu_and_input_is_8() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("8");
        assertEquals("login", bibliotecaService.dispatcher(action).getType());
    }

    @Test
    public void login_action_should_show_hint_and_ask_library_number() {
        Action action = new Action("show_menu", Action.OUTPUT_INPUT, null);
        action.setInput("8");
        assertEquals("Please input your library number: ", bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_save_input_as_context_and_give_a_password_action_when_action_is_login() {
        Action action = new Action("login", Action.OUTPUT_INPUT, null);
        action.setInput("111-1111");
        assertEquals("password", bibliotecaService.dispatcher(action).getType());
        assertEquals("111-1111", bibliotecaService.dispatcher(action).getContext("library_number"));
    }

    @Test
    public void password_action_should_show_hint_and_ask_library_number() {
        Action action = new Action("login", Action.OUTPUT_INPUT, null);
        action.setInput("111-1111");
        assertEquals("Please input your password: ", bibliotecaService.dispatcher(action).run());
    }

    @Test
    public void should_validate_user_input_and_save_logged_as_context_and_give_a_login_success_action_when_action_is_password() {
        Action action = new Action("password", Action.OUTPUT_INPUT, null).setContext("library_number", "111-1111");
        action.setInput("admin");
        assertEquals("login_success", bibliotecaService.dispatcher(action).getType());
        assertEquals("true", bibliotecaService.dispatcher(action).getContext("logged"));
    }

    @Test
    public void should_remove_library_number_in_context_and_give_a_login_fail_action_when_action_is_password_but_password_is_wrong() {
        Action action = new Action("password", Action.OUTPUT_INPUT, null).setContext("library_number", "111-1111");
        action.setInput("wrong_pass");
        assertEquals("login_fail", bibliotecaService.dispatcher(action).getType());
        assertEquals("", bibliotecaService.dispatcher(action).getContext("logged"));
        assertEquals("", bibliotecaService.dispatcher(action).getContext("library_number"));
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
