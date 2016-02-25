package com.twu.biblioteca;//Created by SanCoder on 2/25/16.

import java.util.function.Function;

public class InputHandler {

    private String hintMessage;
    private Function<String, String> callback;

    public InputHandler(String hintMessage, Function<String, String> callback) {
        this.hintMessage = hintMessage;
        this.callback = callback;
    }

    public String getHint() {
        return hintMessage;
    }

    public String run(String input) {
        return callback.apply(input);
    }
}
