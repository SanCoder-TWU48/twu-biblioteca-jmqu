package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        print(BibliotecaService.welcome());
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
