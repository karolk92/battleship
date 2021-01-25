package com.softwaremill.app.model.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameDetails {
    private final GameStatus gameStatus;
    private final int playerScore;
    private final int opponentScore;
}
