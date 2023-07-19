package bubble.bubble.game.component;

import bubble.bubble.game.service.BackgroundEnemyService;
import bubble.bubble.game.BubbleFrame;
import bubble.bubble.game.state.EnermyWay;
import bubble.bubble.game.MoveAble;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;


@Getter
@Setter
public class Enermy extends JLabel implements MoveAble {

    private BubbleFrame mContext;

    //위치 상태
    private int x;
    private int y;

    //적의 방향
    private EnermyWay enermyWay;

    private ImageIcon enermyWayR,enermyWayL;

    //움직임상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private int state; // 0(살아있는 상태),1(적을 가둔 물방울)

    //적의 속도
    private final int SPEED = 3;
    private final int JUMPSPEED = 1;
    

    public Enermy(BubbleFrame mContext){
        this.mContext = mContext;
        initObject();
        initSetting();
        BackgroundEnemyService();
        right();
    }

    private void BackgroundEnemyService() {
        new Thread(new BackgroundEnemyService(this)).start();
    }
    private void initObject(){
        enermyWayR = new ImageIcon("image/enemyR.png");
        enermyWayL = new ImageIcon("image/enemyL.png");
    }

    private void initSetting(){
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

        state = 0;

        enermyWay = EnermyWay.RIGHT;
        setIcon(enermyWayR);
        setSize(50, 50);
        setLocation(x,y);


    }

    //이벤트 핸들러
    public void left() {
        enermyWay = EnermyWay.LEFT;
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(enermyWayL);
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
        enermyWay = EnermyWay.RIGHT;
        right = true;
        new Thread(() -> {
            while (right){
            setIcon(enermyWayR);
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
                    Thread.sleep(5);
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
            while(down){
                y = y + JUMPSPEED;
                setLocation(x,y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
            down = false;
        }).start();

    }
}
