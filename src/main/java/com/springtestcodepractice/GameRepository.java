package com.springtestcodepractice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByNameContaining(String game);

    List<Game> findByScoreGreaterThan(int num);
}
