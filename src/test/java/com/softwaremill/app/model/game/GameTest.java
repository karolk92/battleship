package com.softwaremill.app.model.game;

import com.softwaremill.app.api.model.GameStatusResponse;
import com.softwaremill.app.api.model.ShotResponse;
import com.softwaremill.app.exception.game.GameAlreadyFinishedException;
import com.softwaremill.app.exception.game.NotGameParticipantException;
import com.softwaremill.app.exception.player.InvalidPlayerException;
import com.softwaremill.app.exception.player.NotPlayerTurnException;
import com.softwaremill.app.model.Player;
import com.softwaremill.app.model.Shot;
import com.softwaremill.app.model.ShotResult;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.service.BoardService;
import com.softwaremill.app.service.MockBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private Player player1;
    private Player player2;

    private final String [] player1ShipPositions = new String[] {"A1", "D1", "D2", "G1", "G2", "G3", "J1", "J2", "J3", "J4"};
    private final String [] player2ShipPositions = new String[] {"A1", "B1", "C1", "D1", "D8", "D9", "D10", "G9", "G10", "J10"};

    @BeforeEach
    public void beforeEach() {
        BoardService mockBoardService = new MockBoardService();

        player1 = new Player(UUID.randomUUID(), mockBoardService.initPlayer1Board(), true);
        player2 = new Player(UUID.randomUUID(), mockBoardService.initPlayer2Board(), false);
    }

    @Test
    @DisplayName("Given not started game, when valid player passed, expect joining to game")
    public void validPlayerJoinsGame() {
        Game game = new Game(player1);

        game.join(player2);
    }

    @Test
    @DisplayName("Given not started game, when null player passed, expect exception thrown")
    public void nullPlayerJoinsGame() {
        Game game = new Game(player1);

        assertThrows(InvalidPlayerException.class, () -> game.join(null));
    }

    @Test
    @DisplayName("Given not participant, when shots, expect exception thrown")
    public void notParticipantShots() {
        Game game = new Game(player1);

        assertThrows(NotGameParticipantException.class, () -> game.shot(player2, new Position("A1")));
    }

    @Test
    @DisplayName("Given game and player with no turn, when shots, expect exception thrown")
    public void playerWithNoTurnShots() {
        Game game = new Game(player1);
        game.join(player2);

        assertThrows(NotPlayerTurnException.class, () -> game.shot(player1, new Position("A1")));
    }

    @Test
    @DisplayName("Given same position twice, when shots, second shot should be missed")
    public void whenShootingSamePosition() {
        Game game = new Game(player1);
        game.join(player2);

        ShotResult firstShot = game.shot(player2, new Position("A1"));
        game.shot(player1, new Position("D5"));
        ShotResult secondShot = game.shot(player2, new Position("A1"));

        assertEquals(Shot.HIT, firstShot.getResult());
        assertEquals(1, game.getDetails(player2).getPlayerScore());
        assertEquals(0, game.getDetails(player2).getOpponentScore());
        assertEquals(Shot.MISS, secondShot.getResult());
        assertEquals(1, game.getDetails(player2).getPlayerScore());
    }

    @Test
    @DisplayName("Given player that wins, expect correct statuses after game finished")
    public void whenPlayerWon() {
        Game game = new Game(player1);
        game.join(player2);

        gain10ScoreForPlayer2(game, player1, player2);

        GameDetails player1Details = game.getDetails(player1);
        GameDetails player2Details = game.getDetails(player2);

        assertEquals(GameStatus.YOU_WON, player2Details.getGameStatus());
        assertEquals(GameStatus.YOU_LOST, player1Details.getGameStatus());
    }

    @Test
    @DisplayName("Given finished game, when shots, expect exception thrown")
    public void playerShotsWhenGameIsFinished() {
        Game game = new Game(player1);
        game.join(player2);

        gain10ScoreForPlayer2(game, player1, player2);

        assertThrows(GameAlreadyFinishedException.class, () -> game.shot(player2, new Position("A2")));
        assertThrows(GameAlreadyFinishedException.class, () -> game.shot(player1, new Position("A2")));
    }

    private void gain10ScoreForPlayer2(Game game, Player player1, Player player2) {
        for(int i = 0; i < 10; i++) {
            game.shot(player2, new Position(player1ShipPositions[i]));

            if (i == 9) return;

            game.shot(player1, new Position(player2ShipPositions[i]));
        }
    }

}
