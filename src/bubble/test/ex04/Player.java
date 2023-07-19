package bubble.test.ex04;

import lombok.Data;

import javax.swing.*;

@Data
public class Player extends JLabel {
    //위치 상태
    private int x;
    private int y;

    private ImageIcon playerR,playerL;

    //움직임상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

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
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(playerL);
                x = x - 1;
                setLocation(x, y);
                try {
                    Thread.sleep(10);  // 0.01초 단위로
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    public void right() {
        right = true;
        new Thread(() -> {
            while (right){
            setIcon(playerR);
            x = x+1;
            setLocation(x,y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }//버튼을 클릭할때마다 스레드를 생성,종료하는 메모리가 아까우니까
                //버튼을 눌렀다 뗄떼마다 스레드를 종료시킨다.

            }
        }).start(); //쓰레드 생성
    }

    public void up() {
        // 그림변경 시점은 이벤트 루프에 모든 task가 끝나야 repaint한다. 고로 점프하면서 움직일수없다.
        // 왜? 쓰레드가 하나라서 쓰레드가 여러개여야 동시 task 가 가능하다.
    }
}
