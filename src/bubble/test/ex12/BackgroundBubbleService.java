package bubble.test.ex12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BackgroundBubbleService  {

    private BufferedImage image;
    private Bubble bubble;

    public BackgroundBubbleService(Bubble bubble){
        this.bubble = bubble;
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean lWall(){
        Color lColor = new Color(image.getRGB(bubble.getX() - 10, bubble.getY() + 36));
        if (lColor.getRed() == 255 && lColor.getGreen() == 0 && lColor.getBlue() == 0) {
            return true; // left를 false로 한다는것은 더이상 움직이지 않게하겠다.
        }
        return false;
    }
    public boolean rWall(){
        Color rColor = new Color(image.getRGB(bubble.getX() + 50 + 15, bubble.getY() + 25));
        if(rColor.getRed() == 255 && rColor.getGreen() == 0 && rColor.getBlue() == 0) {
            return true;
        }
        return false;
    }
    public boolean tWall() {
        Color tColor = new Color(image.getRGB(bubble.getX() + 25, bubble.getY() - 10));
        if (tColor.getRed() == 255 && tColor.getGreen() == 0 && tColor.getBlue() == 0) {
            return true;
        }
        return false;
    }
}
