package com.softwaremill.app.model.board;

public enum ShipType {
    FOUR_DECKER(4), THREE_DECKER(3), TWO_DECKER(2), ONE_DECKER(1);

    ShipType(int length) {
        this.length = length;
    }

    private final int length;

    public int getLength() {
        return length;
    }
}
