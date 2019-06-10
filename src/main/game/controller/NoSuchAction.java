package main.game.controller;

public class NoSuchAction extends RuntimeException {
    public NoSuchAction(String message) {
        super(message);
    }
}
