package com.softwaremill.app.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwaremill.app.model.game.GameDetails;
import com.softwaremill.app.model.game.GameStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameStatusResponse {
    private final GameStatus gameStatus;
    @JsonProperty(value = "yourScore")
    private final int playerScore;
    private final int opponentScore;

    public static GameStatusResponse from(GameDetails details) {
        return new GameStatusResponse(details.getGameStatus(), details.getPlayerScore(), details.getOpponentScore());
    }
}
