package bubble.test.ex05;

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

    //플레이어 속도
    private final int SPEED = 4;
    private final int JUMPSPEED = 2;

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
                x = x - SPEED;
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
            x = x + SPEED;
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

    // left + up, right + up
    public void up() {
        up = true;
        new Thread(() ->{
            for(int i=0; i<130/JUMPSPEED; i++){
                y = y - JUMPSPEED;
                setLocation(x,y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            up = false;
            down();


        }).start();
        // 그림변경 시점은 이벤트 루프에 모든 task가 끝나야 repaint한다. 고로 점프하면서 움직일수없다.
        // 왜? 쓰레드가 하나라서 쓰레드가 여러개여야 동시 task 가 가능하다.
    }

    public void down(){
        down = true;
        new Thread(() ->{
            for(int i=0; i<130/JUMPSPEED; i++){
                y = y + JUMPSPEED;
                setLocation(x,y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
            down = false;
        }).start();

    }
}
