package edu.hitsz.application.scoreboard;

import java.util.List;

public interface ScoreBoardDao {
    void add(Score score);

    List<Score> getByDifficulty(String difficulty);

    void recordAndPrint(String difficulty, int score);
}
