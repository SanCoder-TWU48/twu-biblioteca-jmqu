package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();
        print(bibliotecaService.welcome());
        print(bibliotecaService.showMenu());
        getInput("Please choose (item number):");
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void getInput(String message) {
        System.out.println(message);
    }
}
