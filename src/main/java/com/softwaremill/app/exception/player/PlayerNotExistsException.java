package com.softwaremill.app.exception.player;

public class PlayerNotExistsException extends RuntimeException {
    public PlayerNotExistsException(String message) {
        super(message);
    }
}
