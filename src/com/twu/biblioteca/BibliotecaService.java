package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.LibraryResource;
import com.twu.biblioteca.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BibliotecaService {

    private List<Book> bookList;
    private List<Movie> movieList;

    public BibliotecaService() {
        initData();
    }

    private void initData() {
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
                return new Action("show_menu", Action.OUTPUT_INPUT, this::showMenu);
            case "show_menu":
                switch (action.getInput()) {
                    case "1":
                        return new Action("list_books", Action.OUTPUT_ONLY, this::listBooks);
                    case "2":
                        return new Action("checkout_book", Action.OUTPUT_INPUT, this::checkoutHint);
                    case "3":
                        return new Action("return_book", Action.OUTPUT_INPUT, this::returnHint);
                    case "4":
                        return new Action("list_movies", Action.OUTPUT_ONLY, this::listMovies);
                    case "9":
                        return new Action("quit", Action.OUTPUT_ONLY, this::sayGoodbye);
                    default:
                        return new Action("invalid_menu_item", Action.OUTPUT_ONLY, this::invalidMenuItem);
                }
            case "checkout_book":
                return checkoutBook(action.getInput()) ?
                        new Action("checkout_success", Action.OUTPUT_ONLY, this::bookCheckoutSuccess) :
                        new Action("checkout_fail", Action.OUTPUT_ONLY, this::bookCheckoutFail);
            case "return_book":
                return returnBook(action.getInput()) ?
                        new Action("return_success", Action.OUTPUT_ONLY, this::bookReturnSuccess) :
                        new Action("return_fail", Action.OUTPUT_ONLY, this::bookReturnFail);
            case "checkout_movie":
                return checkoutMovie(action.getInput()) ?
                        new Action("checkout_success", Action.OUTPUT_ONLY, this::movieCheckoutSuccess) :
                        new Action("checkout_fail", Action.OUTPUT_ONLY, this::movieCheckoutFail);
            case "quit":
            default:
                return null;
        }
    }

    private String welcome() {
        return "Welcome to Biblioteca!";
    }

    private String showMenu() {
        return String.join("\n",
                showDoubleSplitter(),
                showMenuBody(),
                showSingleSplitter(),
                "Please choose (item number):");
    }

    private String showSingleSplitter() {
        return "--------------------";
    }

    private String showMenuBody() {
        return String.join("\n",
                "        Menu        ",
                "(1) List Books",
                "(2) Checkout Book",
                "(3) Return Book",
                "(4) List Movies",
                "(9) Quit");
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

    private <T extends LibraryResource>boolean checkoutResource(Stream<T> resourceStream, Predicate<T> predicate) {
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

    private String checkoutHint() {
        return "Please input the book name: ";
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

    private String sayGoodbye() {
        return "Good bye.";
    }
}
