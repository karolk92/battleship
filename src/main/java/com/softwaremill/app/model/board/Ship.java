package com.softwaremill.app.model.board;

import java.util.Map;

public class Ship {
    private final ShipType shipType;
    private final Map<Position, ShipPart> shipParts;
    private boolean isSunken;

    public Ship(ShipType shipType, Map<Position, ShipPart> shipParts) {
        this.shipType = shipType;
        this.shipParts = shipParts;
        this.isSunken = false;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public boolean isSunken() {
        return this.isSunken;
    }

    public boolean isHitAt(Position position) {
        return shipParts.get(position).isHit();
    }

    public void hitAt(Position position) {
        shipParts.get(position).hit();
        checkIfSunken();
    }

    private void checkIfSunken() {
        this.isSunken =
            shipParts
                .entrySet()
                .stream()
                .allMatch(part -> part.getValue().isHit());
    }
}
