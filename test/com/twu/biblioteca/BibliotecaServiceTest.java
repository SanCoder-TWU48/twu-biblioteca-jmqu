package com.twu.biblioteca;//Created by SanCoder on 2/23/16.

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaServiceTest {

    @Test
    public void welcome_should_give_a_welcome_message() {
        assertEquals("Welcome to Biblioteca!", BibliotecaService.welcome());
    }
}
