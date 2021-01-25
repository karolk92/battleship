package com.softwaremill.app.service;

import com.softwaremill.app.api.model.*;
import com.softwaremill.app.exception.game.GameNotExistsException;
import com.softwaremill.app.exception.game.InvalidGameIdException;
import com.softwaremill.app.model.Invitation;
import com.softwaremill.app.model.Player;
import com.softwaremill.app.model.ShotResult;
import com.softwaremill.app.model.board.Position;
import com.softwaremill.app.model.game.Game;
import com.softwaremill.app.model.game.GameDetails;
import com.softwaremill.app.model.game.GameStatus;
import com.softwaremill.app.persistence.DataStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GameService {
    private final ServerDetailsService serverDetails;
    private final PlayerService playerService;
    private final DataStorage dataStorage;

    public Invitation createGame(Player player) {
        playerService.validatePlayer(player);

        Game game = new Game(player);
        dataStorage.addGame(game.getId(), game);

        String invitationUrl = String.format("%s/game/%s/join", serverDetails.getApplicationAddress(), game.getId());

        return new Invitation(invitationUrl);
    }

    public GameDetails joinGame(UUID gameId, Player player) {
        playerService.validatePlayer(player);

        getGame(gameId)
            .join(player);

        return new GameDetails(GameStatus.YOUR_TURN, 0, 0);
    }

    public ShotResult shot(UUID gameId, UUID playerId, ShotRequest request) {
        Game game = getGame(gameId);
        Player player = playerService.getPlayer(playerId);

        return game
            .shot(player, new Position(request.getPosition()));
    }

    public GameDetails getGameDetails(UUID gameId, UUID playerId) {
        Game game = getGame(gameId);
        Player player = playerService.getPlayer(playerId);

        return game.getDetails(player);
    }

    private Game getGame(UUID gameId) {
        if (gameId == null) {
            throw new InvalidGameIdException("Token cannot be null");
        }

        return dataStorage.getGameById(gameId)
            .orElseThrow(() -> new GameNotExistsException(String.format("Game with id: %s does not exist", gameId)));
    }
}
