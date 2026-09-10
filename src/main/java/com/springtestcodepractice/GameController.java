package com.springtestcodepractice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@RequestBody GameCreateRequest request) {
        Game game = gameService.createGame(request.getName());
        return ResponseEntity.ok(new GameResponse(game.getId(), game.getName(), game.getScore()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long id) {
        Game game = gameService.findGame(id);
        return ResponseEntity.ok(new GameResponse(game.getId(), game.getName(), game.getScore()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
}