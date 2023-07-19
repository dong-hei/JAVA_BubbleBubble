package bubble.test.ex12;

import lombok.Data;

import javax.swing.*;

@Data
public class Bubble extends JLabel implements MoveAble {
    //의존성 컴포지션
    private Player player;
    private BackgroundBubbleService backgroundBubbleService;

    //위치 상태
    private int x;
    private int y;

    //움직임상태
    private boolean left;
    private boolean right;
    private boolean up;

    //적을 맞춘 상태
    private int state; // 0 물방울 상태 1 적을 가둔 물방울
    private ImageIcon bubble;
    private ImageIcon bubbled;
    private ImageIcon bomb; // 물방울이 터진상태

    public Bubble(Player player){
        this.player = player;
       initObject();
       initSetting();
       initThread();
    }

    private void initObject(){
        bubble = new ImageIcon("image/bubble.png");
        bubbled = new ImageIcon("image/bubbled.png");
        bomb = new ImageIcon("image/bomb.png");

        backgroundBubbleService = new BackgroundBubbleService(this);
    }

    private void initSetting(){
        left = false;
        right = false;
        up = false;

        x = player.getX();
        y = player.getY();

        setIcon(bubble);
        setSize(50, 50);

        state = 0;
    }

    private void initThread(){
        // 버블은 스레드가 1개만 필요하다
        new Thread(()->{
            if (player.getPlayerWay() == PlayerWay.LEFT) {
                left();
            }else{
                right();
            }

        }).start();
    }

    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x,y);
            if (backgroundBubbleService.lWall()) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x,y);
            if (backgroundBubbleService.lWall()) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x,y);
            if (backgroundBubbleService.tWall()) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
