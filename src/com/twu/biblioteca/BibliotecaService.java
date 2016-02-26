package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import com.twu.biblioteca.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {

    private List<Book> bookList;

    public BibliotecaService() {
        initData();
    }

    private void initData() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("Book1", "Author1", "2003"));
        bookList.add(new Book("Book2", "Author2", "2005"));
        bookList.add(new Book("Book3", "Author3", "2013"));
    }


    public Action dispatcher(Action action) {
        if (action == null)
            return new Action("welcome", Action.OUTPUT_ONLY, this::welcome);

        switch (action.getType()) {
            case "welcome":
            case "list_books":
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
                    case "9":
                        return new Action("quit", Action.OUTPUT_ONLY, this::sayGoodbye);
                    default:
                        return new Action("invalid_menu_item", Action.OUTPUT_ONLY, this::invalidMenuItem);
                }
            case "checkout_book":
                return checkoutBook(action.getInput()) ?
                        new Action("checkout_success", Action.OUTPUT_ONLY, this::checkoutSuccess) :
                        new Action("checkout_fail", Action.OUTPUT_ONLY, this::checkoutFail);
            case "return_book":
                return returnBook(action.getInput()) ?
                        new Action("return_success", Action.OUTPUT_ONLY, this::returnSuccess) :
                        new Action("return_fail", Action.OUTPUT_ONLY, this::returnFail);
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
        return bookList.stream()
                .filter(Book::isAvailable)
                .map(b -> String.format("%s\t%s\t%s", b.getName(), b.getAuthor(), b.getPublishedYear()))
                .reduce((b1, b2) -> b1 + "\n" + b2)
                .get();
    }

    private boolean checkoutBook(String bookName) {
        return bookList.stream()
                .filter(Book::isAvailable)
                .filter(book -> book.getName().equals(bookName))
                .peek(Book::checkout)
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

    private String checkoutSuccess() {
        return "Thank you! Enjoy the book";
    }

    private String checkoutFail() {
        return "That book is not available.";
    }

    private String returnSuccess() {
        return "Thank you for returning the book.";
    }

    private String returnFail() {
        return "That is not a valid book to return.";
    }

    private String invalidMenuItem() {
        return "Select a valid option!";
    }

    private String sayGoodbye() {
        return "Good bye.";
    }
}
