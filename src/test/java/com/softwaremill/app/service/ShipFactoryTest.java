package com.softwaremill.app.service;

import com.softwaremill.app.exception.shot.PositionOutOfRangeException;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.model.board.Ship;
import com.softwaremill.app.model.board.ShipType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShipFactoryTest {

    @DisplayName("Given valid ship and position, when creating horizontal four-decker, expect valid object created")
    @Test
    public void horizontalFourDeckerShip() {
        Position position = new Position("G7");
        Ship ship = ShipFactory.createHorizontalShip(ShipType.FOUR_DECKER, position);

        assertEquals(ShipType.FOUR_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating horizontal three-decker, expect valid object created")
    @Test
    public void horizontalThreeDeckerShip() {
        Position position = new Position("A7");
        Ship ship = ShipFactory.createHorizontalShip(ShipType.THREE_DECKER, position);

        assertEquals(ShipType.THREE_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating horizontal two-decker, expect valid object created")
    @Test
    public void horizontalTwoDeckerShip() {
        Position position = new Position("C9");
        Ship ship = ShipFactory.createHorizontalShip(ShipType.TWO_DECKER, position);

        assertEquals(ShipType.TWO_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating horizontal one-decker, expect valid object created")
    @Test
    public void horizontalOneDeckerShip() {
        Position position = new Position("F10");
        Ship ship = ShipFactory.createHorizontalShip(ShipType.ONE_DECKER, position);

        assertEquals(ShipType.ONE_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given invalid position for four-decker, when creating horizontal ship, expect exception thrown")
    @Test
    public void horizontalFourDeckerShipWithWrongPosition() {
        Position position = new Position("A9");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createHorizontalShip(ShipType.FOUR_DECKER, position));
    }

    @DisplayName("Given invalid position for three-decker, when creating horizontal ship, expect exception thrown")
    @Test
    public void horizontalThreeDeckerShipWithWrongPosition() {
        Position position = new Position("F9");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createHorizontalShip(ShipType.THREE_DECKER, position));
    }

    @DisplayName("Given invalid position for two-decker, when creating horizontal ship, expect exception thrown")
    @Test
    public void horizontalTwoDeckerShipWithWrongPosition() {
        Position position = new Position("F10");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createHorizontalShip(ShipType.TWO_DECKER, position));
    }

    @DisplayName("Given valid ship and position, when creating vertical four-decker, expect valid object created")
    @Test
    public void verticalFourDeckerShip() {
        Position position = new Position("G10");
        Ship ship = ShipFactory.createVerticalShip(ShipType.FOUR_DECKER, position);

        assertEquals(ShipType.FOUR_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating vertical three-decker, expect valid object created")
    @Test
    public void verticalThreeDeckerShip() {
        Position position = new Position("H5");
        Ship ship = ShipFactory.createVerticalShip(ShipType.THREE_DECKER, position);

        assertEquals(ShipType.THREE_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating vertical two-decker, expect valid object created")
    @Test
    public void verticalTwoDeckerShip() {
        Position position = new Position("I7");
        Ship ship = ShipFactory.createVerticalShip(ShipType.TWO_DECKER, position);

        assertEquals(ShipType.TWO_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given valid ship and position, when creating vertical one-decker, expect valid object created")
    @Test
    public void verticalOneDeckerShip() {
        Position position = new Position("J10");
        Ship ship = ShipFactory.createVerticalShip(ShipType.ONE_DECKER, position);

        assertEquals(ShipType.ONE_DECKER, ship.getShipType());
        assertFalse(ship.isSunken());
    }

    @DisplayName("Given invalid position for four-decker, when creating vertical ship, expect exception thrown")
    @Test
    public void verticalFourDeckerShipWithWrongPosition() {
        Position position = new Position("I8");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createVerticalShip(ShipType.FOUR_DECKER, position));
    }

    @DisplayName("Given invalid position for three-decker, when creating vertical ship, expect exception thrown")
    @Test
    public void verticalThreeDeckerShipWithWrongPosition() {
        Position position = new Position("I8");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createVerticalShip(ShipType.THREE_DECKER, position));
    }

    @DisplayName("Given invalid position for two-decker, when creating vertical ship, expect exception thrown")
    @Test
    public void verticalTwoDeckerShipWithWrongPosition() {
        Position position = new Position("J10");

        assertThrows(PositionOutOfRangeException.class,
            () -> ShipFactory.createVerticalShip(ShipType.TWO_DECKER, position));
    }
}