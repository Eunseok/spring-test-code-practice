package com.springtestcodepractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // "자동으로 H2로 바꾸지 마라, application.yml에 설정된 진짜 DB를 써라"
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void 이름에_특정_키워드가_포함된_게임을_조회한다() {
        // Given
        gameRepository.save(new Game("adventure game"));
        gameRepository.save(new Game("puzzle game"));
        gameRepository.save(new Game("racing"));

        // When
        List<Game> result = gameRepository.findByNameContaining("game");

        // Then
        assertEquals(2, result.size());
    }


    // @Transactional 안 붙였는데도 자동 적용됨!

    @Test
    void 테스트1() {
        gameRepository.save(new Game("game1"));
        // 테스트 끝나면 자동 롤백
    }

    @Test
    void 테스트2() {
        assertEquals(0, gameRepository.count());  // 항상 0, 테스트1 영향 없음
    }
}