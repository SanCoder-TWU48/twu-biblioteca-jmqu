package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.LibraryResource;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BibliotecaService {

    private HashMap<String, User> userList;
    private List<Book> bookList;
    private List<Movie> movieList;

    public BibliotecaService() {
        initData();
    }

    private void initData() {
        userList = new HashMap<String, User>();
        userList.put("111-1111", new User("111-1111", "admin", "ADMIN", "ADDRESS1", "11111111"));
        userList.put("222-2222", new User("222-2222", "user", "USER", "ADDRESS2", "22222222"));

        bookList = new ArrayList<Book>();
        bookList.add(new Book("Book1", "Author1", "2003"));
        bookList.add(new Book("Book2", "Author2", "2005"));
        bookList.add(new Book("Book3", "Author3", "2013"));

        movieList = new ArrayList<Movie>();
        movieList.add(new Movie("Movie1", "Director1", "2003", "8"));
        movieList.add(new Movie("Movie2", "Director2", "2005", "5"));
        movieList.add(new Movie("Movie3", "Director3", "2013", "unrated"));
    }


    public Action dispatcher(Action action) {
        if (action == null)
            return new Action("welcome", Action.OUTPUT_ONLY, this::welcome);

        switch (action.getType()) {
            case "welcome":
            case "list_books":
            case "list_movies":
            case "checkout_success":
            case "checkout_fail":
            case "return_success":
            case "return_fail":
            case "invalid_menu_item":
            case "login_success":
            case "login_fail":
            case "user_info":
            case "ask_login":
                if (isLogged(action))
                    return new Action("show_menu", Action.OUTPUT_INPUT, () -> showMenu(true), action.getAllContext());
                else
                    return new Action("show_menu", Action.OUTPUT_INPUT, () -> showMenu(false), action.getAllContext());
            case "show_menu":
                switch (action.getInput()) {
                    case "1":
                        return new Action("list_books", Action.OUTPUT_ONLY, this::listBooks, action.getAllContext());
                    case "2":
                        if (isLogged(action))
                            return new Action("checkout_book", Action.OUTPUT_INPUT, this::bookCheckoutHint, action.getAllContext());
                        else
                            return new Action("ask_login", Action.OUTPUT_ONLY, this::askLogin, action.getAllContext());
                    case "3":
                        if (isLogged(action))
                            return new Action("return_book", Action.OUTPUT_INPUT, this::returnHint, action.getAllContext());
                        else
                            return new Action("ask_login", Action.OUTPUT_ONLY, this::askLogin, action.getAllContext());
                    case "4":
                        return new Action("list_movies", Action.OUTPUT_ONLY, this::listMovies, action.getAllContext());
                    case "5":
                        if (isLogged(action))
                            return new Action("checkout_movie", Action.OUTPUT_INPUT, this::movieCheckoutHint, action.getAllContext());
                        else
                            return new Action("ask_login", Action.OUTPUT_ONLY, this::askLogin, action.getAllContext());
                    case "8":
                        if (isLogged(action))
                            return new Action("user_info", Action.OUTPUT_ONLY, () -> userInfo(action.getContext("library_number")), action.getAllContext());
                        else
                            return new Action("login", Action.OUTPUT_INPUT, this::loginHint, action.getAllContext());
                    case "9":
                        return new Action("quit", Action.OUTPUT_ONLY, this::sayGoodbye, action.getAllContext());
                    default:
                        return new Action("invalid_menu_item", Action.OUTPUT_ONLY, this::invalidMenuItem, action.getAllContext());
                }
            case "checkout_book":
                return checkoutBook(action.getInput()) ?
                        new Action("checkout_success", Action.OUTPUT_ONLY, this::bookCheckoutSuccess, action.getAllContext()) :
                        new Action("checkout_fail", Action.OUTPUT_ONLY, this::bookCheckoutFail, action.getAllContext());
            case "return_book":
                return returnBook(action.getInput()) ?
                        new Action("return_success", Action.OUTPUT_ONLY, this::bookReturnSuccess, action.getAllContext()) :
                        new Action("return_fail", Action.OUTPUT_ONLY, this::bookReturnFail, action.getAllContext());
            case "checkout_movie":
                return checkoutMovie(action.getInput()) ?
                        new Action("checkout_success", Action.OUTPUT_ONLY, this::movieCheckoutSuccess, action.getAllContext()) :
                        new Action("checkout_fail", Action.OUTPUT_ONLY, this::movieCheckoutFail, action.getAllContext());
            case "login":
                return new Action("password", Action.OUTPUT_INPUT, this::passwordHint, action.getAllContext())
                        .setContext("library_number", action.getInput());
            case "password":
                if (userValidate(action.getContext("library_number"), action.getInput()))
                    return new Action("login_success", Action.OUTPUT_ONLY, this::loginSuccess, action.getAllContext())
                            .setContext("logged", "true");
                else
                    return new Action("login_fail", Action.OUTPUT_ONLY, this::loginFail, action.getAllContext())
                            .removeContext("library_number");
            case "quit":
            default:
                return null;
        }
    }

    private String userInfo(String libraryNumber) {
        User user = userList.get(libraryNumber);
        return String.join("\n",
                showDoubleSplitter(),
                "Name: " + user.getName(),
                "E-mail: " + user.getEmail(),
                "Phone: " + user.getPhoneNum(),
                showSingleSplitter());
    }

    private boolean isLogged(Action action) {
        return action.getContext("logged").equals("true");
    }

    private boolean userValidate(String library_number, String password) {
        return userList.values().stream()
                .filter(u -> u.validate(library_number, password))
                .count() > 0;
    }

    private String welcome() {
        return "Welcome to Biblioteca!";
    }

    private String showMenu(boolean isLogged) {
        return String.join("\n",
                showDoubleSplitter(),
                showMenuBody(isLogged),
                showSingleSplitter(),
                "Please choose (item number):");
    }

    private String showSingleSplitter() {
        return "--------------------";
    }

    private String showMenuBody(boolean isLogged) {
        String menu = String.join("\n",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(3) Return Book",
                "(4) List Movies",
                "(5) Checkout Movie") + "\n";
        menu += isLogged ? "(8) User Information\n" : "(8) Login\n";
        return menu + "(9) Quit";
    }

    private String showDoubleSplitter() {
        return "====================";
    }

    private String listBooks() {
        return listResource(bookList.stream(), b ->
                String.format("%s\t%s\t%s", b.getName(), b.getAuthor(), b.getPublishedYear()));
    }

    private String listMovies() {
        return listResource(movieList.stream(), m ->
                String.format("%s\t%s\t%s\t%s", m.getName(), m.getDirector(), m.getPublishedYear(), m.getRating()));
    }

    private <T extends LibraryResource> String listResource(Stream<T> resourceStream, Function<T, String> formatter) {
        return resourceStream
                .filter(LibraryResource::isAvailable)
                .map(formatter)
                .reduce((b1, b2) -> b1 + "\n" + b2)
                .get();
    }

    private boolean checkoutBook(String bookName) {
        return checkoutResource(bookList.stream(), b -> b.getName().equals(bookName));
    }

    private boolean checkoutMovie(String movieName) {
        return checkoutResource(movieList.stream(), m -> m.getName().equals(movieName));
    }

    private <T extends LibraryResource> boolean checkoutResource(Stream<T> resourceStream, Predicate<T> predicate) {
        return resourceStream
                .filter(LibraryResource::isAvailable)
                .filter(predicate)
                .peek(LibraryResource::checkout)
                .count() > 0;
    }

    private boolean returnBook(String bookName) {
        return bookList.stream()
                .filter(book -> book.getName().equals(bookName))
                .peek(Book::checkin)
                .count() > 0;
    }

    private String bookCheckoutHint() {
        return "Please input the book name: ";
    }

    private String movieCheckoutHint() {
        return "Please input the movie name: ";
    }

    private String returnHint() {
        return "Please input the book name: ";
    }

    private String bookCheckoutSuccess() {
        return "Thank you! Enjoy the book";
    }

    private String bookCheckoutFail() {
        return "That book is not available.";
    }

    private String movieCheckoutSuccess() {
        return "Thank you! Enjoy the movie";
    }

    private String movieCheckoutFail() {
        return "That movie is not available.";
    }

    private String bookReturnSuccess() {
        return "Thank you for returning the book.";
    }

    private String bookReturnFail() {
        return "That is not a valid book to return.";
    }

    private String invalidMenuItem() {
        return "Select a valid option!";
    }

    private String loginHint() {
        return "Please input your library number: ";
    }

    private String passwordHint() {
        return "Please input your password: ";
    }

    private String loginSuccess() {
        return "Login success.";
    }

    private String loginFail() {
        return "Login failed.";
    }

    private String askLogin() {
        return "Please login first.";
    }

    private String sayGoodbye() {
        return "Good bye.";
    }
}
