package bubble.test.ex17;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

//메인스레드는 키보드를 처리하기 바쁨
//백그라운드에서 관찰
public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    //플레이어, 버블
    public BackgroundPlayerService(Player player){
        this.bubbleList = player.getBubbleList();
        this.player = player;
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run() {
        while (true){
            //1. 버블 충돌체크
            for (int i = 0; i < bubbleList.size(); i++) {
                Bubble bubble = bubbleList.get(i);
                if (bubbleList.get(i).getState() == 1) {
                    if((Math.abs(player.getX() - bubble.getX()) < 10
                            && Math.abs(player.getY() - bubble.getY()) < 60)
                            && (Math.abs(player.getY() - bubble.getY()) < 50)){
                        System.out.println("적군 사망");
                        new Thread(() ->{
                            bubble.clearBubbled();
                        }).start();
                        break;
                    }
                }
            }

            //2. 벽 충돌 체크

            //색상 확인
            Color lColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 36));
            Color rColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
            int bottomColor = image.getRGB(player.getX() + 10 , player.getY() + 50 + 5)
                    +  image.getRGB(player.getX() + 50 -10, player.getY() + 50 + 5);

            //바닥 충돌 확인
            if(bottomColor != -2){
                player.setDown(false);
            } else{ // -2일때 실행됨 => 바닥 색이 흰색이라는 것
                if(!player.isUp() && !player.isDown()){
                    player.down();
                }
            }
            //외벽 충돌 확인
            if (lColor.getRed() == 255 && lColor.getGreen() == 0 && lColor.getBlue() == 0) {
                player.setLeftWallCrash(true);
                player.setLeft(false); // left를 false로 한다는것은 더이상 움직이지 않게하겠다.
            } else if(rColor.getRed() == 255 && rColor.getGreen() == 0 && rColor.getBlue() == 0){
                player.setRightWallCrash(true);
                player.setRight(false);
            } else{
                player.setRightWallCrash(false);
                player.setLeftWallCrash(false);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
