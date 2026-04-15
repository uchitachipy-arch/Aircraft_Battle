package edu.hitsz.application.scoreboard;

/**
 * 成绩实体：难度、玩家名、得分、时间。
 */
public class Score {
    private final String difficulty;
    private final String playerName;
    private final int score;
    private final String time;

    public Score(String difficulty, String playerName, int score, String time) {
        this.difficulty = difficulty;
        this.playerName = playerName;
        this.score = score;
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public String toDataLine() {
        return difficulty + "," + playerName + "," + score + "," + time;
    }

    public static Score fromDataLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            return null;
        }
        try {
            int parsedScore = Integer.parseInt(parts[2].trim());
            return new Score(parts[0].trim(), parts[1].trim(), parsedScore, parts[3].trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
