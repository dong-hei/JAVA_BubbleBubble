package bubble.test.ex17;

import lombok.Data;

import javax.swing.*;

@Data
public class Bubble extends JLabel implements MoveAble {
    //의존성 컴포지션
    private BubbleFrame mContext;
    private Player player;
    private BackgroundBubbleService backgroundBubbleService;
    private Enermy enermy;
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

    public Bubble(BubbleFrame mContext){
        this.mContext = mContext;
        this.player = mContext.getPlayer();
        this.enermy = mContext.getEnermy();
       initObject();
       initSetting();
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

    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x,y);
            if (backgroundBubbleService.lWall()) {
                left = false;
                break;
            }

            //40과 60의 절대값
            if((Math.abs(x - enermy.getX()) > 40 && Math.abs(x - enermy.getX()) < 60)
            && (Math.abs(y - enermy.getY()) > 0 && Math.abs(y - enermy.getY()) < 50)){
                if(enermy.getState() == 0) {
                    attack();
                    break;
                }
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
                right = false;
                break;
            } 
            //야군과 적군의 거리가 10의 차이
            if((Math.abs(x - enermy.getX()) < 10)
                    && (Math.abs(y - enermy.getY()) > 0 && Math.abs(y - enermy.getY()) < 50)){
                if(enermy.getState() == 0) {
                    attack();
                    break;
                }
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
                up = false;
                break;
            }
            try {
                if (state == 0) { //기본 물방울
                Thread.sleep(1);
            }   else { // 적을 가둔 물방울
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(state == 0)
        clearBubble(); //천장에 버블이 도착하고 다시 3초후에 메모리에서 소멸
    }

    public void attack(){
        state = 1;
        enermy.setState(1);
        setIcon(bubbled);
        mContext.remove(enermy); //메모리에서 사라지게 한다. (가비지 컬렉션 -> 즉시 발동하지 않음.)
        mContext.repaint(); // 화면 갱신
    }

    private void clearBubble(){
        try {
            Thread.sleep(3000);
            setIcon(bomb);
            Thread.sleep(500);
            mContext.remove(this); // 버블프레임의 버블이 메모리에서 소멸
            mContext.repaint(); //버블프레임 전체를 다시 그린다. 즉 메모리에서 없는건 그리지않는다.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    } //메소드라는것은 행위이기 때문에 이름지을때 동사를 앞에 넣어라

    public void clearBubbled(){
        new Thread(() ->{
            System.out.println("Clear Bubbled");
            try {
                up = false;
                setIcon(bomb);
                Thread.sleep(1000);
                mContext.remove(this);
                mContext.repaint();
            } catch (Exception e) {
            }
        }).start();
    }
}
