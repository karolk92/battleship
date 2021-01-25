package com.softwaremill.app.model.game;

import lombok.Getter;

@Getter
public class Score {
    private int player1Score;
    private int player2Score;

    public void incrementPlayer1Score() {
        player1Score++;
    }

    public void incrementPlayer2Score() {
        player2Score++;
    }
}
