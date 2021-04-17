package GameEngine.Background;

import GameEngine.Frame;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.FileInputStream;

public class Background {

    String path;
    Canvas canvas;
    Image Background;

    public Background(String path, Canvas canvas) {
        this.path = path;
        this.canvas = canvas;
        Background = new Image(path);
    }

    public void render(double cx, double cy){
        canvas.getGraphicsContext2D().drawImage(Background, -cx, -cy, canvas.getWidth(), canvas.getHeight());
        if(cx < (canvas.getWidth()/2)){
            canvas.getGraphicsContext2D().drawImage(Background, cx*2, cy*2, canvas.getWidth(), canvas.getHeight());
        }
        if(cy < (canvas.getHeight()/2)){
            canvas.getGraphicsContext2D().drawImage(Background, cx*2, cy*2, canvas.getWidth(), canvas.getHeight());
        }
    }

}
