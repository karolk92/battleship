package com.softwaremill.app.model.board;

public class ShipPart {
    private boolean hit;

    public ShipPart() {
        this.hit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public void hit() {
        this.hit = true;
    }

}
