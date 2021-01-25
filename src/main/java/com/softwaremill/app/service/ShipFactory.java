package com.softwaremill.app.service;

import com.softwaremill.app.exception.shot.PositionOutOfRangeException;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.model.board.Ship;
import com.softwaremill.app.model.board.ShipPart;
import com.softwaremill.app.model.board.ShipType;

import java.util.HashMap;
import java.util.Map;


public class ShipFactory {

    public static Ship createHorizontalShip(ShipType shipType, Position startingPosition) {
        Map<Position, ShipPart> shipParts = new HashMap<>();
        int shipLength = shipType.getLength();

        validateHorizontalPosition(shipLength, startingPosition.getHorizontalPosition());

        Position currentPosition = startingPosition;
        for(int i = 0; i < shipLength; i++) {
            shipParts.put(currentPosition, new ShipPart());

            if (i < shipLength - 1) {
                currentPosition = currentPosition.incrementHorizontalPosition();
            }
        }

        return new Ship(shipType, shipParts);
    }

    public static Ship createVerticalShip(ShipType shipType, Position startingPosition) {
        Map<Position, ShipPart> shipParts = new HashMap<>();
        int shipLength = shipType.getLength();

        validateVerticalPosition(shipLength, startingPosition.getVerticalPosition());

        Position currentPosition = startingPosition;
        for(int i = 0; i < shipLength; i++) {
            shipParts.put(currentPosition, new ShipPart());

            if (i < shipLength - 1) {
                currentPosition = currentPosition.incrementVerticalPosition();
            }
        }

        return new Ship(shipType, shipParts);
    }

    private static void validateHorizontalPosition(int shipLength, int horizontalPosition) {
        if (horizontalPosition + shipLength < 1 || horizontalPosition + shipLength > 11) {
            throw new PositionOutOfRangeException("Not enough fields to locate horizontal battleship. Position: " + horizontalPosition);
        }
    }

    private static void validateVerticalPosition(int shipLength, char verticalPosition) {
        int position = asIntBoardPosition(verticalPosition);
        if (position + shipLength < 1 || position + shipLength > 11) {
            throw new PositionOutOfRangeException("Not enough fields to locate vertical battleship. Position: " + verticalPosition);
        }
    }

    private static int asIntBoardPosition(char verticalPosition) {
        return verticalPosition - 64;
    }
}
