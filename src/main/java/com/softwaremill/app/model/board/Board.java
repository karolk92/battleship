package com.softwaremill.app.model.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Board {
    private final Map<Position, Ship> shipsOccurrences;

    public Ship getShipAt(Position position) {
        return shipsOccurrences.get(position);
    }
}
