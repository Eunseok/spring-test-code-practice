package com.springtestcodepractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 게임을_생성한다() throws Exception {
        // Given
        Game game = new Game("newGame");
        given(gameService.createGame("newGame")).willReturn(game);

        GameCreateRequest request = new GameCreateRequest("newGame");

        // When & Then
        mockMvc.perform(post("/games") // "POST /games 로 이 요청을 보내라"
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // request 객체를 JSON 문자열로 변환해서 body에 담음
                .andExpect(status().isOk()) // "응답 상태코드가 200인지 확인해라"
                .andExpect(jsonPath("$.name").value("newGame")); // "응답 JSON에서 name 필드 값이 newGame인지 확인해라"
    }

    @Test
    void 게임을_조회한다() throws Exception {
        // Given
        Game game = new Game("newGame");
        given(gameService.findGame(1L)).willReturn(game);

        // When & Then
        mockMvc.perform(get("/games/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newGame"))
                .andExpect(jsonPath("$.score").value(0));
    }

    @Test
    void 존재하지_않는_게임을_조회하면_404를_반환한다() throws Exception {
        // Given
        given(gameService.findGame(999L))
                .willThrow(new IllegalArgumentException("게임을 찾을 수 없습니다."));

        // When & Then
        mockMvc.perform(get("/games/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}