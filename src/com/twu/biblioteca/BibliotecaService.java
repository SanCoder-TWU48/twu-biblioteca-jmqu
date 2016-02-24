package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import com.twu.biblioteca.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {

    private List<Book> bookList;

    public BibliotecaService() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("Book1", "Author1", "2003"));
        bookList.add(new Book("Book2", "Author2", "2005"));
        bookList.add(new Book("Book3", "Author3", "2013"));
    }

    public String welcome() {
        return "Welcome to Biblioteca!";
    }

    public String showBookList() {
        return bookList.stream()
                .map(b -> String.format("%s\t%s\t%s", b.getName(), b.getAuthor(), b.getPublishedYear()))
                .reduce((b1, b2) -> b1 + "\n" + b2)
                .get();
    }
}
