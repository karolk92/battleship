package com.softwaremill.app.service;

import com.softwaremill.app.model.board.Board;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.model.board.Ship;
import com.softwaremill.app.model.board.ShipType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoardService {

    public Board initPlayer1Board() {
        Position ship1Position1 = new Position("A1");

        Position ship2Position1 = new Position("D1");
        Position ship2Position2 = new Position("D2");

        Position ship3Position1 = new Position("G1");
        Position ship3Position2 = new Position("G2");
        Position ship3Position3 = new Position("G3");

        Position ship4Position1 = new Position("J1");
        Position ship4Position2 = new Position("J2");
        Position ship4Position3 = new Position("J3");
        Position ship4Position4 = new Position("J4");

        Ship ship1 = ShipFactory.createHorizontalShip(ShipType.ONE_DECKER, ship1Position1);
        Ship ship2 = ShipFactory.createHorizontalShip(ShipType.TWO_DECKER, ship2Position1);
        Ship ship3 = ShipFactory.createHorizontalShip(ShipType.THREE_DECKER, ship3Position1);
        Ship ship4 = ShipFactory.createHorizontalShip(ShipType.FOUR_DECKER, ship4Position1);

        Map<Position, Ship> ships = Map.of(
            ship1Position1, ship1,
            ship2Position1, ship2,
            ship2Position2, ship2,
            ship3Position1, ship3,
            ship3Position2, ship3,
            ship3Position3, ship3,
            ship4Position1, ship4,
            ship4Position2, ship4,
            ship4Position3, ship4,
            ship4Position4, ship4
        );

        return new Board(ships);
    }

    public Board initPlayer2Board() {
        Position ship1Position1 = new Position("J10");

        Position ship2Position1 = new Position("G9");
        Position ship2Position2 = new Position("G10");

        Position ship3Position1 = new Position("D8");
        Position ship3Position2 = new Position("D9");
        Position ship3Position3 = new Position("D10");

        Position ship4Position1 = new Position("A1");
        Position ship4Position2 = new Position("B1");
        Position ship4Position3 = new Position("C1");
        Position ship4Position4 = new Position("D1");

        Ship ship1 = ShipFactory.createHorizontalShip(ShipType.ONE_DECKER, ship1Position1);
        Ship ship2 = ShipFactory.createHorizontalShip(ShipType.TWO_DECKER, ship2Position1);
        Ship ship3 = ShipFactory.createHorizontalShip(ShipType.THREE_DECKER, ship3Position1);
        Ship ship4 = ShipFactory.createVerticalShip(ShipType.FOUR_DECKER, ship4Position1);

        Map<Position, Ship> ships = Map.of(
            ship1Position1, ship1,
            ship2Position1, ship2,
            ship2Position2, ship2,
            ship3Position1, ship3,
            ship3Position2, ship3,
            ship3Position3, ship3,
            ship4Position1, ship4,
            ship4Position2, ship4,
            ship4Position3, ship4,
            ship4Position4, ship4
            );

        return new Board(ships);
    }
}
