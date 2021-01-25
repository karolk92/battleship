package com.softwaremill.app.service;

import com.softwaremill.app.exception.player.InvalidPlayerTokenException;
import com.softwaremill.app.exception.player.PlayerNotExistsException;
import com.softwaremill.app.model.Player;
import com.softwaremill.app.model.board.Board;
import com.softwaremill.app.persistence.DataStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final DataStorage dataStorage;

    public Player getPlayer(UUID playerId) {
        validatePlayerId(playerId);

        return dataStorage.getPlayerById(playerId)
            .orElseThrow(() -> new PlayerNotExistsException(String.format("Player with id: %s does not exist", playerId)));
    }

    public void validatePlayer(Player player) {
        if (player == null) throw new InvalidPlayerTokenException("Token cannot be null");

        validatePlayerId(player.getId());
    }

    public void validatePlayerId(UUID playerId) {
        if (playerId == null) throw new InvalidPlayerTokenException("Token cannot be null");
    }

    public Player createFirstPlayer(Board board) {
        return createPlayer(board, true);
    }

    public Player createSecondPlayer(Board board) {
        return createPlayer(board, false);
    }

    private Player createPlayer(Board board, boolean isGameCreator) {
        UUID playerId = UUID.randomUUID();
        Player player = new Player(playerId, board, isGameCreator);
        dataStorage.addPlayer(playerId, player);

        return player;
    }
}
