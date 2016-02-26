package com.twu.biblioteca;

import java.io.PrintStream;
import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) {
        biblioteca(new Scanner(System.in), System.out);
    }

    public static void biblioteca(Scanner scanner, PrintStream ps) {
        BibliotecaService bibliotecaService = new BibliotecaService();

        Action action = bibliotecaService.dispatcher(null);
        while(action != null) {
            print(ps, action.run());
            if(action.isInputAction())
                action.setInput(scanner.nextLine());
            action = bibliotecaService.dispatcher(action);
        }
    }

    private static void print(PrintStream printStream, String message) {
        printStream.println("\n" + message);
    }
}
