package bubble.test.ex07;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

//메인스레드는 키보드를 처리하기 바쁨
//백그라운드에서 관찰
public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;

    public BackgroundPlayerService(Player player){
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
            //색상 확인
            Color lColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 36));
            Color rColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));

            if (lColor.getRed() == 255 && lColor.getGreen() == 0 && lColor.getBlue() == 0) {
                System.out.println("왼쪽 벽닿");
                player.setLeftWallCrash(true);
                player.setLeft(false); // left를 false로 한다는것은 더이상 움직이지 않게하겠다.
            } else if(rColor.getRed() == 255 && rColor.getGreen() == 0 && rColor.getBlue() == 0){
                System.out.println("오른쪽 벽닿");
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
