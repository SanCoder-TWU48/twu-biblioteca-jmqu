package com.twu.biblioteca.model;//Created by SanCoder on 2/26/16.

public class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean validate(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}
