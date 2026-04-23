package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.hitsz.application.scoreboard.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ScoretableUI {
    private JPanel ScoretableUI;
    private JButton delete;
    private JButton ret;
    private JTable table1;
    private JLabel title;
    private JPanel topp;
    private JPanel bottonp;
    private JScrollPane tablescrollpanel;
    private DefaultTableModel tableModel;
    private final ScoreBoardDao scoreBoardDao = new SBDaoimple();
    // 缓存当前表格显示的分数列表，用于准确获取要删除的对象
    private List<Score> currentScores;
    private String difficulty; // 保存当前难度


    public ScoretableUI(String DIFFICULTY, int finalScore) {
        // 直接赋值即可，去掉了冗余的局部变量声明
        this.difficulty = DIFFICULTY;


        String[] columnNames = {"名次", "玩家名", "得分", "记录时间"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        title.setText("排行榜 - " + difficulty + " 模式");


        promptForNameAndSave(finalScore);


        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    int result = JOptionPane.showConfirmDialog(ScoretableUI.this.ScoretableUI,
                            "确定要删除选中的得分记录吗？", "提示", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        Score scoreToDelete = currentScores.get(selectedRow);
                        scoreBoardDao.delete(scoreToDelete);
                        loadScoreData();
                    }
                } else {
                    JOptionPane.showMessageDialog(ScoretableUI.this.ScoretableUI, "请先在表格中点击选择要删除的记录！");
                }
            }
        });


        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void promptForNameAndSave(int score) {

        String playerName = JOptionPane.showInputDialog(
                this.ScoretableUI,
                "战斗结束！最终得分：" + score + "\n请输入指挥官名称：",
                "guest"
        );
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "匿名玩家";
        }
        scoreBoardDao.recordAndPrint(this.difficulty, score, playerName);
        loadScoreData();
    }

    private void loadScoreData() {
        tableModel.setRowCount(0);
        currentScores = scoreBoardDao.getByDifficulty(difficulty);
        for (int i = 0; i < currentScores.size(); i++) {
            Score record = currentScores.get(i);
            Object[] rowData = {
                    i + 1,                 // 动态名次
                    record.getPlayerName(),
                    record.getScore(),
                    record.getTime()
            };
            tableModel.addRow(rowData);
        }
    }

    public JPanel getScoretableUI() { return ScoretableUI; }

}