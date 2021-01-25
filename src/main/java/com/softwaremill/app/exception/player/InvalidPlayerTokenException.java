package com.softwaremill.app.exception.player;

public class InvalidPlayerTokenException extends RuntimeException {
    public InvalidPlayerTokenException(String message) {
        super(message);
    }
}
