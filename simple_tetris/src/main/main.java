package main;

import javax.swing.JFrame;

public class main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        

        // 게임 패널을 윈도우에 추가하기.

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
