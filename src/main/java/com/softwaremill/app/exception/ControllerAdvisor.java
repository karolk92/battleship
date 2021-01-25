package com.softwaremill.app.exception;

import com.softwaremill.app.exception.game.*;
import com.softwaremill.app.exception.player.InvalidPlayerException;
import com.softwaremill.app.exception.player.InvalidPlayerTokenException;
import com.softwaremill.app.exception.player.NotPlayerTurnException;
import com.softwaremill.app.exception.player.PlayerNotExistsException;
import com.softwaremill.app.exception.shot.PositionMalformedException;
import com.softwaremill.app.exception.shot.PositionOutOfRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({
        PositionOutOfRangeException.class,
        PositionMalformedException.class,
        GameAlreadyFinishedException.class,
        GameAlreadyStartedException.class,
        GameNotExistsException.class,
        GameNotStartedException.class,
        NotGameParticipantException.class,
        PlayerNotExistsException.class,
        InvalidGameIdException.class,
        InvalidPlayerException.class
    })
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        InvalidPlayerTokenException.class
    })
    public ResponseEntity<Object> handleUnauthorizedException(RuntimeException ex) {
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
        NotPlayerTurnException.class
    })
    public ResponseEntity<Object> handleForbiddenException(RuntimeException ex) {
        Map<String, Object> body = getBody(ex);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    private Map<String, Object> getBody(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return body;
    }
}
