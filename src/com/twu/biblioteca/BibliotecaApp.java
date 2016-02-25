package com.twu.biblioteca;

import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();
        print(bibliotecaService.welcome());
        print(bibliotecaService.showMenu());
        getInput(new InputHandler("Please choose (item number):", input -> bibliotecaService.showBookList()));
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void getInput(InputHandler inputHandler) {
        System.out.println(inputHandler.getHint());
        System.out.println(inputHandler.run(new Scanner(System.in).next()));
    }
}
