package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiffMenu {
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JPanel DiffMenu;
    private JPanel easyp;
    private JPanel normalp;
    private JPanel hardp;
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public DiffMenu() {
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("EASY");
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("NORMAL");
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("HARD");
            }
        });

    }
    public JPanel getDiffMenu() { return DiffMenu; }
    private void startGame(String difficulty) {
        ImageManager.changeBackground(difficulty);
        Game game = new Game(difficulty);
        Main.cardPanel.add(game, "GAME");
        Main.cardLayout.show(Main.cardPanel, "GAME");
        game.requestFocus();
        game.action();
    }
}
