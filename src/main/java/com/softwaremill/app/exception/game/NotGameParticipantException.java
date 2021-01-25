package com.softwaremill.app.exception.game;

public class NotGameParticipantException extends RuntimeException {
    public NotGameParticipantException(String message) {
        super(message);
    }
}
