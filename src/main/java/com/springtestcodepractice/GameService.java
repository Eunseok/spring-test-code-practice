package com.springtestcodepractice;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String name) {
        Game game = new Game(name);
        return gameRepository.save(game);
    }

    public Game addScore(Long gameId, int score) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));

        game.addScore(score);
        return gameRepository.save(game);
    }

    public Game findGame(Long gameId) {

        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));
    }
}