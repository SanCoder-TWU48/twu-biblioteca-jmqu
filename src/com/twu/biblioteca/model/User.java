package com.twu.biblioteca.model;//Created by SanCoder on 2/26/16.

public class User {
    private String libraryNum;
    private String password;
    private String name;
    private String email;
    private String phoneNum;

    public User(String libraryNum, String password, String name, String email, String phoneNum) {
        this.libraryNum = libraryNum;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public boolean validate(String libraryNum, String password) {
        return this.libraryNum.equals(libraryNum) && this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
