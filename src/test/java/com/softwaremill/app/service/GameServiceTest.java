package com.softwaremill.app.service;

import com.softwaremill.app.api.model.*;
import com.softwaremill.app.exception.game.GameNotExistsException;
import com.softwaremill.app.exception.game.InvalidGameIdException;
import com.softwaremill.app.exception.game.NotGameParticipantException;
import com.softwaremill.app.exception.player.InvalidPlayerTokenException;
import com.softwaremill.app.exception.player.NotPlayerTurnException;
import com.softwaremill.app.exception.player.PlayerNotExistsException;
import com.softwaremill.app.exception.shot.PositionMalformedException;
import com.softwaremill.app.model.Invitation;
import com.softwaremill.app.model.Player;
import com.softwaremill.app.model.Shot;
import com.softwaremill.app.model.ShotResult;
import com.softwaremill.app.model.board.ShipType;
import com.softwaremill.app.model.game.Game;
import com.softwaremill.app.model.game.GameDetails;
import com.softwaremill.app.model.game.GameStatus;
import com.softwaremill.app.persistence.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GameServiceTest")
class GameServiceTest {

    private ServerDetailsService serverDetailsService;
    private BoardService mockBoardService;
    private DataStorage dataStorage;
    private PlayerService playerService;
    private GameService gameService;

    private Player player1;
    private Player player2;

    @BeforeEach
    public void beforeEach() {
        serverDetailsService = new ServerDetailsService(8080);
        mockBoardService = new MockBoardService();
        dataStorage = new DataStorage();
        playerService = new PlayerService(dataStorage);
        gameService = new GameService(serverDetailsService, playerService, dataStorage);

        player1 = new Player(UUID.randomUUID(), mockBoardService.initPlayer1Board(), true);
        player2 = new Player(UUID.randomUUID(), mockBoardService.initPlayer2Board(), false);
    }

    @Test
    @DisplayName("Given valid playerId, when creating game, expect invitation url")
    public void validPlayerId() {
        Invitation result = gameService.createGame(player1);

        String[] split = result.getInvitationUrl().split("/");

        assertEquals("game", split[1]);
        assertTrue(split[2].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"));
        assertEquals("join", split[3]);
    }

    @Test
    @DisplayName("Given null playerId, when creating game, expect exception thrown")
    public void nullPlayerId() {
        assertThrows(InvalidPlayerTokenException.class, () -> gameService.createGame(null));
    }

    @Test
    @DisplayName("Given valid gameId and null playerId, when joining game, expect exception thrown")
    public void validGameIdNullPlayerId() {
        UUID gameId = UUID.randomUUID();
        assertThrows(InvalidPlayerTokenException.class, () -> gameService.joinGame(gameId, null));
    }

    @Test
    @DisplayName("Given null gameId and valid playerId, when joining game, expect exception thrown")
    public void nullGameIdValidPlayerId() {
        assertThrows(InvalidGameIdException.class, () -> gameService.joinGame(null, player1));
    }

    @Test
    @DisplayName("Given valid gameId and valid playerId, when joining game, expect valid response")
    public void validGameIdAndValidPlayerId() {
        Game game = createTestGame(player1);

        GameDetails result = gameService.joinGame(game.getId(), player2);

        assertEquals(GameStatus.YOUR_TURN, result.getGameStatus());
        assertEquals(0, result.getPlayerScore());
        assertEquals(0, result.getOpponentScore());
    }

    @Test
    @DisplayName("Given null gameId and valid playerId and valid shotRequest, when shooting, expect exception thrown")
    public void nullGameIdAndValidPlayerIdAndValidShotRequest() {
        assertThrows(InvalidGameIdException.class,
            () -> gameService.shot(null, player1.getId(), new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given valid gameId and null playerId and valid shotRequest, when shooting, expect exception thrown")
    public void validGameIdAndNullPlayerIdAndValidShotRequest() {
        Game game = createTestGame(player1);

        assertThrows(InvalidPlayerTokenException.class,
            () -> gameService.shot(game.getId(), null, new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given not existing game id, when shooting, expect exception thrown")
    public void notExistingGameId() {
        UUID gameId = UUID.randomUUID();

        assertThrows(GameNotExistsException.class,
            () -> gameService.shot(gameId, player1.getId(), new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given not existing player id, when shooting, expect exception thrown")
    public void notExistingPlayerId() {
        UUID notExistingPlayerId = UUID.randomUUID();
        Game game = createTestGame(player1);

        assertThrows(PlayerNotExistsException.class,
            () -> gameService.shot(game.getId(), notExistingPlayerId, new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given not a game participant, when shooting, expect exception thrown")
    public void notGameParticipant() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player2.getId(), player2);

        assertThrows(NotGameParticipantException.class,
            () -> gameService.shot(game.getId(), player2.getId(), new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given valid gameId, valid playerId and wrong position, when shooting, expect exception thrown")
    public void wrongPosition() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player2.getId(), player2);
        game.join(player2);

        assertThrows(PositionMalformedException.class,
            () -> gameService.shot(game.getId(), player2.getId(), new ShotRequest("A1a")));
    }

    @Test
    @DisplayName("Given valid gameId, playerId and hit position, when shooting, expect valid response")
    public void validHitPosition() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player2.getId(), player2);
        game.join(player2);

        ShotResult result = gameService.shot(game.getId(), player2.getId(), new ShotRequest("D1"));

        assertEquals(Shot.HIT, result.getResult());
        assertEquals(ShipType.TWO_DECKER, result.getShipType());
        assertFalse(result.isSunken());
    }

    @Test
    @DisplayName("Given valid gameId, playerId and miss position, when shooting, expect valid response")
    public void validMissPosition() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player2.getId(), player2);
        game.join(player2);

        ShotResult result = gameService.shot(game.getId(), player2.getId(), new ShotRequest("D10"));

        assertEquals(Shot.MISS, result.getResult());
        assertNull(result.getShipType());
    }

    @Test
    @DisplayName("Given valid gameId, playerId and miss position, when shooting, expect exception thrown")
    public void wrongPlayerTurn() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player1.getId(), player1);

        game.join(player2);

        assertThrows(NotPlayerTurnException.class,
            () -> gameService.shot(game.getId(), player1.getId(), new ShotRequest("A1")));
    }

    @Test
    @DisplayName("Given valid gameId, playerId, when getting game status, expect exception thrown")
    public void notParticipantGetsStatus() {
        Game game = createTestGame(player1);
        dataStorage.addPlayer(player2.getId(), player2);

        assertThrows(NotGameParticipantException.class,
            () -> gameService.getGameDetails(game.getId(), player2.getId()));
    }

    @Test
    @DisplayName("Given valid gameId, playerId, when getting game status, expect valid response")
    public void participantGetsStatus() {
        dataStorage.addPlayer(player1.getId(), player1);
        Game game = createTestGame(player1);

        GameDetails result = gameService.getGameDetails(game.getId(), player1.getId());

        assertEquals(GameStatus.AWAITING_PLAYERS, result.getGameStatus());
        assertEquals(0, result.getPlayerScore());
        assertEquals(0, result.getOpponentScore());
    }

    private Game createTestGame(Player player) {
        Game game = new Game(player);
        dataStorage.addGame(game.getId(), game);

        return game;
    }
}