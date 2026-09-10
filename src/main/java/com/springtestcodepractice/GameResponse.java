package com.springtestcodepractice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameResponse {
    private Long id;
    private String name;
    private int score;
}
