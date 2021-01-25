package com.softwaremill.app.exception.shot;

public class PositionOutOfRangeException extends RuntimeException {
    public PositionOutOfRangeException(String message) {
        super(message);
    }
}
