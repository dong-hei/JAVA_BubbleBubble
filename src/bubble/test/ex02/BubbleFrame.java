package bubble.test.ex02;
import javax.swing.*;

//윈도우 창이 된다 (JFrame 상속으로 인해서)
//윈도우 창은 내부에서 패널을 하나 가지고있다.
public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame(){
        initObject();
        initSetting();
        setVisible(true);
    }
    //닫기로 종료시켜도 계속 실행되고있다 = while문이 내장되어있다.
    private void initObject(){
        backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
        setContentPane(backgroundMap); //굳이 새계층으로 만들필요가없어서 이렇게 함
//        backgroundMap.setSize(1000,600); 사이즈조정 가능
//        backgroundMap.setLocation(311, 322); 위치조정 가능
//        add(backgroundMap); //JFrame에 JLabel이 그려진다.

        player = new Player();
        add(player);
//        덧붙이기때문에 add
    }

    private void initSetting(){
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃 (자유롭게 그림그리기)
        setLocationRelativeTo(null); //JFrame을 가운데로 실행하게끔
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기버튼으로 창을끌때 JVM도 같이 종료
    }

    public static void main(String[] args) {

        new BubbleFrame();
    }
}
