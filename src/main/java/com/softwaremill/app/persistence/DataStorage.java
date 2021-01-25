package com.softwaremill.app.persistence;

import com.softwaremill.app.model.game.Game;
import com.softwaremill.app.model.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class DataStorage {
    private final Map<UUID, Game> games = new HashMap<>();
    private final Map<UUID, Player> players = new HashMap<>();

    public void addGame(UUID gameId, Game game) {
        games.put(gameId, game);
    }

    public Optional<Game> getGameById(UUID gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

    public void addPlayer(UUID playerId, Player player) {
        players.put(playerId, player);
    }

    public Optional<Player> getPlayerById(UUID playerId) {
        return Optional.ofNullable(players.get(playerId));
    }
}
