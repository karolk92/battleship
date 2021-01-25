package com.softwaremill.app.model;

import com.softwaremill.app.model.board.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
public class Player {
    private final UUID id;
    private final Board board;
    private final boolean isGameCreator;

    public Player(UUID token, Board board, boolean isGameCreator) {
        this.id = token;
        this.board = board;
        this.isGameCreator = isGameCreator;
    }
}
