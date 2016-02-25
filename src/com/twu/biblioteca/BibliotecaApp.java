package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();
        print(bibliotecaService.welcome());
        print(bibliotecaService.showMenu());
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
