package bubble.test.ex03;

import javax.swing.*;

public class Player extends JLabel {
    private int x;
    private int y;

    private ImageIcon playerR,playerL;

    public Player(){
        initObject();
        initSetting();
    }

    private void initObject(){
        playerR = new ImageIcon("image/playerR.png");
        playerL = new ImageIcon("image/playerL.png");
    }

    private void initSetting(){
        x = 55;
        y = 535;

        setIcon(playerR);
        setSize(50, 50);
        setLocation(x,y);
    }

    //이벤트 핸들러
    public void left() {
        setIcon(playerL);
        x = x-10;
        setLocation(x,y);
    }

    public void right() {

        setIcon(playerR);
        x = x+10;
        setLocation(x,y);
    }

    public void up() {
        // 그림변경 시점은 이벤트 루프에 모든 task가 끝나야 repaint한다. 고로 점프하면서 움직일수없다.
        // 왜? 쓰레드가 하나라서 쓰레드가 여러개여야 동시 task 가 가능하다.
    }
}
