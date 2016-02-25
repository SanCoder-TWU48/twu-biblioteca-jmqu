package com.twu.biblioteca;

import java.io.PrintStream;
import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) {
        biblioteca(new Scanner(System.in), System.out);
    }

    public static void biblioteca(Scanner scanner, PrintStream ps) {
        BibliotecaService bibliotecaService = new BibliotecaService();
        print(ps, bibliotecaService.welcome());
        print(ps, bibliotecaService.showMenu());
        String output = getInput(scanner, ps, new InputHandler("Please choose (item number):",
                input -> bibliotecaService.dispatcher(input)));
        while (output != null) {
            print(ps, output);
            print(ps, bibliotecaService.showMenu());
            output = getInput(scanner, ps, new InputHandler("Please choose (item number):",
                    input -> bibliotecaService.dispatcher(input)));
        }
    }

    private static void print(PrintStream printStream, String message) {
        printStream.println("\n" + message);
    }

    public static String getInput(Scanner scanner, PrintStream ps, InputHandler inputHandler) {
        print(ps, inputHandler.getHint());
        return inputHandler.run(scanner.nextLine());
    }
}
