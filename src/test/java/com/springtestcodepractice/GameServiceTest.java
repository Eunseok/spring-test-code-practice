package com.springtestcodepractice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // "이 테스트 클래스에서 Mockito 기능(@Mock, @InjectMocks)을 쓸 수 있게 해줘"
class GameServiceTest {

    @Mock // GameRepository의 가짜 구현체를 만들어라
    private GameRepository gameRepository;

    @InjectMocks // GameService를 생성하면서 @Mock으로 만든 가짜 객체들을 자동으로 주입해라
    private GameService gameService;

    // 리턴값 검증
    @Test
    void 게임을_생성한다() {
        // Given
        String name = "newGame";
        given(gameRepository.save(any(Game.class)))
                .willAnswer(invocation -> invocation.getArgument(0));  // 넘어온 인자를 그대로 리턴

        // When
        Game result = gameService.createGame(name);

        // Then
        assertEquals(name, result.getName());
    }

    @Test
    void 점수를_추가한다() {
        // Given
        Game game = new Game("newGame");
        given(gameRepository.findById(1L)).willReturn(Optional.of(game)); // "gameRepository.findById(1L)이 호출되면 실제로 DB를 뒤지지 말고 그냥 Optional.of(game)을 리턴해라"
        given(gameRepository.save(any(Game.class))).willReturn(game); // "save()가 어떤 Game 객체로 호출되든 상관없이 무조건 game을 리턴해라"

        // When
        Game result = gameService.addScore(1L, 100);

        // Then
        assertEquals(100, result.getScore());
    }

    @Test
    void 존재하지_않는_게임에_점수를_추가하면_예외가_발생한다() {
        // given
        given(gameRepository.findById(999L)).willReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> gameService.addScore(999L, 100));
    }

    // 호출 여부 검증
    @Test
    void 점수를_추가하면_save가_호출된다() {
        // Given
        Game game = new Game("newGame");
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));
        given(gameRepository.save(any(Game.class))).willReturn(game);

        // When
        gameService.addScore(1L, 100);

        // Then
        verify(gameRepository).save(game); // "gameRepository.save(game)이 실제로 호출되었는가?"
    }
}