package com.softwaremill.app.api;

import com.softwaremill.app.api.model.*;
import com.softwaremill.app.model.Player;
import com.softwaremill.app.service.BoardService;
import com.softwaremill.app.service.GameService;
import com.softwaremill.app.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {

    private final PlayerService playerService;
    private final BoardService boardService;
    private final GameService gameService;

    @PostMapping
    public GameCreationResponse createGame(HttpServletResponse response) {
        Player player = playerService.createFirstPlayer(boardService.initPlayer1Board());
        GameCreationResponse gameCreationResponse = GameCreationResponse.from(gameService.createGame(player));

        response.setHeader("Set-Auth-Token", player.getId().toString());

        return gameCreationResponse;
    }

    @PostMapping("/{id}/join")
    public GameStatusResponse joinGame(HttpServletResponse response, @PathVariable("id") UUID gameId) {
        Player player = playerService.createSecondPlayer(boardService.initPlayer2Board());
        GameStatusResponse gameJoinResponse = GameStatusResponse.from(gameService.joinGame(gameId, player));

        response.setHeader("Set-Auth-Token", player.getId().toString());

        return gameJoinResponse;
    }

    @PutMapping("/{id}")
    public ShotResponse shot(
        @PathVariable("id") UUID gameId,
        @RequestHeader("Auth-Token") UUID playerId,
        @RequestBody ShotRequest request
    ) {
        playerService.validatePlayerId(playerId);
        return ShotResponse.from(gameService.shot(gameId, playerId, request));
    }

    @GetMapping("/{id}")
    public GameStatusResponse getGameStatus(
        @PathVariable("id") UUID gameId,
        @RequestHeader("Auth-Token") UUID playerId
    ) {
        playerService.validatePlayerId(playerId);
        return GameStatusResponse.from(gameService.getGameDetails(gameId, playerId));
    }
}
