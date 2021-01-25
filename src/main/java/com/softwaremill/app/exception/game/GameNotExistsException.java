package com.softwaremill.app.exception.game;

public class GameNotExistsException extends RuntimeException {
    public GameNotExistsException(String message) {
        super(message);
    }
}
