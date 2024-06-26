package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Square;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;

public class PlayManager {

    // 플레이 영역
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //테트 관련
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    // 기타
    public static int dropInterval = 60; // 60프레임마다 떨어진다.

    //게임 오버
    boolean gameOver;

    // 효과
    boolean effectCounteron;
    int effetCounter;
    ArrayList<Integer> effetY = new ArrayList<>();

    public PlayManager(){
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x  + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // 시작 테트 설정
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }
    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch(i){
            case 0: mino = new Mino_L1();break;
            case 1: mino = new Mino_L2();break;
            case 2: mino = new Mino_Square();break;
            case 3: mino = new Mino_Bar();break;
            case 4: mino = new Mino_T();break;
            case 5: mino = new Mino_Z1();break;
            case 6: mino = new Mino_Z2();break;
        }
        return mino;
        }
    public void update(){
        // check if the currentMino is active
        if(currentMino.active){
            currentMino.update();
        }else{
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);
            // 게임 오버 체크
            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){
                gameOver = true;
            }
            currentMino.deactivating = false;
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
            checkDel();
        }
    }
    private void checkDel(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while(x<right_x && y<bottom_y){
            for(int i =0;i<staticBlocks.size();i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }
            x += Block.SIZE;
            if(x == right_x){
                if(blockCount == 12){
                    effectCounteron = true;
                    effetY.add(y);
                    for(int i =staticBlocks.size() -1;i> -1;i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }
                    for(int i = 0;i< staticBlocks.size();i++){
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
    }
    public void draw(Graphics2D g2){

        // Draw Play Area Frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
        
        // 다음 테트 프레임
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial",Font.PLAIN,30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+60, y+60);

        // 다음 테트 그리기
    
        nextMino.draw(g2);
        // 현재 테트 그리기
        if(currentMino != null){
            currentMino.draw(g2);
        }
        //바닥 테트 그리기
        for(int i = 0;i <staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }
        // 이펙트 그리기
        if(effectCounteron){
            effetCounter++;
            g2.setColor(Color.red);
            for(int i =0;i<effetY.size();i++){
                g2.fillRect(left_x, effetY.get(i), WIDTH, Block.SIZE);
            }
            if (effetCounter ==10){
                effectCounteron = false;
                effetCounter = 0;
                effetY.clear();
            }
        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(gameOver){
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("Game Over", x, y);
            g2.drawString("'R'etry",x+ 50,y+50);
        }
        if(KeyHandler.pausePressed){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
        if(KeyHandler.retryPressed == true && gameOver == true){
            for(int i = staticBlocks.size() -1 ; i> -1 ; i --){
                staticBlocks.remove(i);
            }
            currentMino = pickMino();
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            KeyHandler.retryPressed = false;
            gameOver = false;
        }

        x = 35;
        y = top_y + 10;
        g2.setColor(Color.blue);
        g2.setFont(new Font("Times new Roman",Font.ITALIC,30));
        g2.drawString("KangJeongTaek", x, y);
    }
}
