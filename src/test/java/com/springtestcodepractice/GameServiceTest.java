package com.springtestcodepractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void 게임을_생성한다() {
        // When
        Game game = gameService.createGame("newGame");

        // Then
        assertNotNull(game.getId());
        assertEquals("newGame", game.getName());
        System.out.println("테스트1 실행 후 count() " + gameRepository.count());
    }

    @Test
    void 점수를_추가한다() {
        // Given
        Game game = gameService.createGame("newGame");

        // When
        Game updated = gameService.addScore(game.getId(), 100);

        // Then
        assertEquals(100, updated.getScore());
        System.out.println("테스트1 실행 후 count() " + gameRepository.count());
    }

    @Test
    void 존재하지_않는_게임에_점수를_추가하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> gameService.addScore(999L, 100)
        );
    }

    @Test
    void 전체_게임_개수를_확인한다() {
        // Given
        gameService.createGame("game1");
        gameService.createGame("game2");

        // When
        long count = gameRepository.count();

        // Then
        assertEquals(2, count);   // 다른 테스트가 먼저 실행됐다면 실패할 수도 있음
    }
}