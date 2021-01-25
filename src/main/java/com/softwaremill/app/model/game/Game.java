package com.softwaremill.app.model.game;

import com.softwaremill.app.api.model.ShotResponse;
import com.softwaremill.app.exception.game.GameAlreadyFinishedException;
import com.softwaremill.app.exception.game.GameNotStartedException;
import com.softwaremill.app.exception.game.NotGameParticipantException;
import com.softwaremill.app.exception.player.InvalidPlayerException;
import com.softwaremill.app.exception.player.NotPlayerTurnException;
import com.softwaremill.app.model.*;
import com.softwaremill.app.model.board.Board;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.model.board.Ship;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Game {
    private final UUID id;
    private final Score score;
    private final Player player1;
    private Player player2;
    private InternalGameStatus internalGameStatus;

    public Game(Player player1) {
        if (player1 == null) {
            throw new InvalidPlayerException("Player cannot be null");
        }

        this.id = UUID.randomUUID();
        this.score = new Score();
        this.player1 = player1;
        this.internalGameStatus = InternalGameStatus.AWAITING_PLAYERS;
    }

    public void join(Player player) {
        if (player == null) {
            throw new InvalidPlayerException("Player cannot be null");
        }

        if (isStarted()) {
            throw new GameNotStartedException("Cannot join already started game");
        }

        player2 = player;
        internalGameStatus = InternalGameStatus.PLAYER_2_TURN;
    }

    public ShotResult shot(Player player, Position position) {
        validateParticipant(player);

        if (!isStarted()) {
            throw new GameNotStartedException("Game not started. Awaiting opponent.");
        }

        if(isFinished()) {
            throw new GameAlreadyFinishedException("You cannot take a shot. The game is over.");
        }

        validatePlayerTurn(player);

        Player opponent = getOpponent(player);
        Board opponentBoard = opponent.getBoard();
        Ship ship = opponentBoard.getShipAt(position);

        return ship == null ? handleMiss(player) : handleHit(player, position, ship);
    }

    private boolean isStarted() {
        return !this.internalGameStatus.equals(InternalGameStatus.AWAITING_PLAYERS);
    }

    private boolean isFinished() {
        return internalGameStatus.equals(InternalGameStatus.PLAYER_1_WON) ||
            internalGameStatus.equals(InternalGameStatus.PLAYER_2_WON);
    }

    private Player getOpponent(Player player) {
        return player.equals(player1) ? player2 : player1;
    }

    private ShotResult handleMiss(Player player) {
        changeTurn(player);
        return new ShotResult(Shot.MISS);
    }

    private ShotResult handleHit(Player player, Position position, Ship ship) {
        if (ship.isHitAt(position)) {
            return handleMiss(player);
        }

        ship.hitAt(position);
        incrementScore(player);

        if (!hasWinner()) {
            changeTurn(player);
        }

        return new ShotResult(Shot.HIT, ship.getShipType(), ship.isSunken());
    }

    private void incrementScore(Player player) {
        if (player.isGameCreator()) {
            score.incrementPlayer1Score();
            if (score.getPlayer1Score() == 10) {
                internalGameStatus = InternalGameStatus.PLAYER_1_WON;
            }
        } else {
            score.incrementPlayer2Score();
            if (score.getPlayer2Score() == 10) {
                internalGameStatus = InternalGameStatus.PLAYER_2_WON;
            }
        }
    }

    private boolean hasWinner() {
        if (score.getPlayer1Score() == 10) {
            internalGameStatus = InternalGameStatus.PLAYER_1_WON;
            return true;
        }

        if (score.getPlayer2Score() == 10 ) {
            internalGameStatus = InternalGameStatus.PLAYER_2_WON;
            return true;
        }

        return false;
    }

    private void changeTurn(Player player) {
        internalGameStatus = player.isGameCreator() ? InternalGameStatus.PLAYER_2_TURN : InternalGameStatus.PLAYER_1_TURN;
    }

    public GameDetails getDetails(Player player) {
        validateParticipant(player);

        GameStatus status = statusOf(player);
        int score = player.isGameCreator() ? getScore().getPlayer1Score() : getScore().getPlayer2Score();
        int opponentScore = player.isGameCreator() ? getScore().getPlayer2Score() : getScore().getPlayer1Score();

        return new GameDetails(status, score, opponentScore);
    }

    private GameStatus statusOf(Player player) {
        GameStatus result;
        switch (internalGameStatus) {
            case AWAITING_PLAYERS ->
                result = GameStatus.AWAITING_PLAYERS;
            case PLAYER_1_TURN ->
                result = player.isGameCreator() ? GameStatus.YOUR_TURN : GameStatus.WAITING_FOR_OPPONENT_MOVE;
            case PLAYER_2_TURN ->
                result = !player.isGameCreator() ? GameStatus.YOUR_TURN : GameStatus.WAITING_FOR_OPPONENT_MOVE;
            case PLAYER_1_WON ->
                result = player.isGameCreator() ? GameStatus.YOU_WON : GameStatus.YOU_LOST;
            case PLAYER_2_WON ->
                result = !player.isGameCreator() ? GameStatus.YOU_WON : GameStatus.YOU_LOST;
            default ->
                throw new IllegalStateException("Unexpected value: " + internalGameStatus);
        }

        return result;
    }

    private void validateParticipant(Player player) {
        boolean isParticipant = player.equals(player1) || player.equals(player2);
        if (!isParticipant) {
            throw new NotGameParticipantException(String.format("Player with token [%s] is not a game participant", player.getId()));
        }
    }

    private void validatePlayerTurn(Player player) {
        boolean isPlayerTurn = (internalGameStatus.equals(InternalGameStatus.PLAYER_1_TURN) && player.isGameCreator()) ||
            (internalGameStatus.equals(InternalGameStatus.PLAYER_2_TURN) && !player.isGameCreator());

        if (!isPlayerTurn) {
            throw new NotPlayerTurnException("It's not your turn. You must wait for your opponent's move.");
        }
    }
}
