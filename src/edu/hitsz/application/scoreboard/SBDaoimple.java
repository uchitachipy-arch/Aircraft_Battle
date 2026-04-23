package edu.hitsz.application.scoreboard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 使用单个文件记录所有难度下的成绩数据。
 */
public class SBDaoimple implements ScoreBoardDao {

    private static final Path DATA_FILE = Paths.get("scoreboard", "scores.txt");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void add(Score score) {
        try {
            ensureDataFile();
            String line = score.toDataLine() + System.lineSeparator();
            Files.write(DATA_FILE, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("保存成绩失败", e);
        }
    }

    public void delete(Score score){
        if (score == null) {
            return;
        }
        try {
            ensureDataFile();
            List<String> lines = Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8);
            String lineToRemove = score.toDataLine();
            boolean isRemoved = lines.remove(lineToRemove);
            if (isRemoved) {
                Files.write(DATA_FILE, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("删除成绩失败", e);
        }
    }
    @Override
    public List<Score> getByDifficulty(String difficulty) {
        List<Score> result = new ArrayList<>();
        try {
            ensureDataFile();
            List<String> lines = Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8);
            for (String line : lines) {
                Score score = Score.fromDataLine(line);
                if (score == null) {
                    continue;
                }
                if (score.getDifficulty().equalsIgnoreCase(difficulty)) {
                    result.add(score);
                }
            }
            result.sort(Comparator.comparingInt(Score::getScore)
                    .thenComparing(Score::getTime).reversed());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("读取排行榜失败", e);
        }
    }

    @Override
    public void recordAndPrint(String difficulty, int score ,String player) {
        String currentDifficulty = normalizeDifficulty(difficulty);
        String now = LocalDateTime.now().format(TIME_FORMATTER);
        Score currentScore = new Score(currentDifficulty, player, score, now);
        add(currentScore);

        List<Score> ranking = getByDifficulty(currentDifficulty);
        System.out.println("==============================");
        System.out.println("当前难度: " + currentDifficulty);
        System.out.println("名次\t玩家名\t得分\t时间");
        for (int i = 0; i < ranking.size(); i++) {
            Score item = ranking.get(i);
            System.out.println((i + 1) + "\t" + item.getPlayerName() + "\t" + item.getScore() + "\t" + item.getTime());
        }
        System.out.println("==============================");
    }

    private void ensureDataFile() throws IOException {
        Path parent = DATA_FILE.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(DATA_FILE)) {
            Files.createFile(DATA_FILE);
        }
    }

    private String normalizeDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            return "NORMAL";
        }
        return difficulty.trim().toUpperCase();
    }

}
