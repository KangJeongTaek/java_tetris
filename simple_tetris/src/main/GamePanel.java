package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 720;
    public static final int HEIGHT = 480;
    //FPS = 60
    final int FPS = 60;
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new DimensionUIResource(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }
    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        // 게임 루프
        double drwaInterval = 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (lastTime - currentTime) / drwaInterval;
            lastTime = currentTime;
            if (delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){

    }
    private void paintCompenent(Graphics g){
        super.paintComponent(g);
    }

}
