package bubble.test.ex17;

import bubble.test.ex18.BGM;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//윈도우 창이 된다 (JFrame 상속으로 인해서)
//윈도우 창은 내부에서 패널을 하나 가지고있다.
@Getter
@Setter
public class BubbleFrame extends JFrame {

    private BubbleFrame mContext = this;
    private JLabel backgroundMap;
    private Player player;
    private Enermy enermy;

    public BubbleFrame(){
        initObject();
        initSetting();
        initListener();
        setVisible(true);
    }



    //닫기로 종료시켜도 계속 실행되고있다 = while문이 내장되어있다.
    private void initObject(){
        backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
        setContentPane(backgroundMap); //굳이 새계층으로 만들필요가없어서 이렇게 함
//        backgroundMap.setSize(1000,600); 사이즈조정 가능
//        backgroundMap.setLocation(311, 322); 위치조정 가능
//        add(backgroundMap); //JFrame에 JLabel이 그려진다.
        enermy = new Enermy(mContext);
        add(enermy);
        player = new Player(mContext);
        add(player);
//        덧붙이기때문에 add
    }

    private void initSetting(){
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃 (자유롭게 그림그리기)
        setLocationRelativeTo(null); //JFrame을 가운데로 실행하게끔
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기버튼으로 창을끌때 JVM도 같이 종료
    }

    private void initListener(){
        addKeyListener(new KeyAdapter() {
            
//            키보드 클릭 이벤트 핸들러
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT :
                        if (!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT :
                        if(!player.isRight() && !player.isRightWallCrash()){
                            player.right(); // 메소드를 한번만 실행한다
                        }
                        break;
                    case KeyEvent.VK_UP :
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        player.attack();
                        break;
                }
            }
//            키보드 해제 이벤트 핸들러
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;

                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;

                }
            }
        }); // 키보드값
    }

    public static void main(String[] args) {

        new BubbleFrame();
    }
}
