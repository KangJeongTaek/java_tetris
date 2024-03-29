package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener{

    public static boolean upPressed, downPressed, leftPressed, rightPressed;
    
    @Override
    public void keyTyped(KeyEvent e) {

        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code ==KeyEvent.VK_UP){
            upPressed = true;
        }
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
