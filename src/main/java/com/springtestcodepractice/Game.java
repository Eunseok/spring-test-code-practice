package com.springtestcodepractice;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int score;

    public Game(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("점수는 음수일 수 없습니다.");
        }
        this.score += score;
    }
}