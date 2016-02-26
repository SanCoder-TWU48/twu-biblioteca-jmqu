package com.twu.biblioteca.model;//Created by SanCoder on 2/26/16.

public class LibraryResource {
    protected boolean isAvailable;

    public LibraryResource() {
        this.isAvailable = true;
    }

    public void checkout() {
        isAvailable = false;
    }

    public void checkin() {
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
