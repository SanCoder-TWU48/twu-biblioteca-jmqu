package com.twu.biblioteca;//Created by SanCoder on 2/26/16.

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class Action {

    public final static boolean OUTPUT_INPUT = true;
    public final static boolean OUTPUT_ONLY = false;

    private String type;
    private boolean needInput;
    private Supplier<String> runner;
    private String input;
    private HashMap<String, String> context;

    protected Action(String type, boolean needInput, Supplier<String> runner) {
        this.type = type;
        this.needInput = needInput;
        this.runner = runner;
        this.context = new HashMap<>();
    }

    protected Action(String type, boolean needInput, Supplier<String> runner, HashMap<String, String> context) {
        this.type = type;
        this.needInput = needInput;
        this.runner = runner;
        this.context = new HashMap<>(context);
    }

    public boolean isInputAction() {
        return needInput;
    }

    public String run() {
        return runner.get();
    }

    public String getType() {
        return type;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public HashMap<String, String> getAllContext() {
        return context;
    }

    public String getContext(String key) {
        return context.get(key);
    }

    public Action setContext(String key, String value) {
        this.context.put(key, value);
        return this;
    }

    public Action removeContext(String key) {
        this.context.remove(key);
        return this;
    }
}
