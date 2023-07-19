package bubble.bubble.game.service;

import bubble.test.ex19.Enermy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

//메인스레드는 키보드를 처리하기 바쁨
//백그라운드에서 관찰
public class BackgroundEnemyService implements Runnable {

    private BufferedImage image;
    private Enermy enermy;

    //플레이어, 버블
    public BackgroundEnemyService(Enermy enermy) {
        this.enermy = enermy;
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (enermy.getState() == 0) {
            //2. 벽 충돌 체크

            //색상 확인
            Color lColor = new Color(image.getRGB(enermy.getX() - 10, enermy.getY() + 25));
            Color rColor = new Color(image.getRGB(enermy.getX() + 50 + 15, enermy.getY() + 25));
            int bottomColor = image.getRGB(enermy.getX() + 10, enermy.getY() + 50 + 5)
                    + image.getRGB(enermy.getX() + 50 - 10, enermy.getY() + 50 + 5);

            //바닥 충돌 확인
            if (bottomColor != -2) {
                enermy.setDown(false);
            } else { // -2일때 실행됨 => 바닥 색이 흰색이라는 것
                if (!enermy.isUp() && !enermy.isDown()) {
                    enermy.down();
                }
            }
            //외벽 충돌 확인
            if (lColor.getRed() == 255 && lColor.getGreen() == 0 && lColor.getBlue() == 0) {
                enermy.setLeft(false); // left를 false로 한다는것은 더이상 움직이지 않게하겠다.
                if (!enermy.isRight()) {
                    enermy.right();
                }
            } else if (rColor.getRed() == 255 && rColor.getGreen() == 0 && rColor.getBlue() == 0) {
                enermy.setRight(false);
                if (!enermy.isLeft()) {
                    enermy.left();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
