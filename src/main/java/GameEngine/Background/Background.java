package GameEngine.Background;

import GameEngine.Frame;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

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
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth(), -cy%canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        if(cx%canvas.getWidth() > 0){
            canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() + canvas.getWidth(), -cy%canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            if(cy%canvas.getHeight() > 0){
                canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() + canvas.getWidth(), -cy%canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            } else if(cy%canvas.getHeight() < 0){
                canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() + canvas.getWidth(), -cy%canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            }
        } else if(cx%canvas.getWidth() < 0){
            canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() - canvas.getWidth(), -cy%canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            if(cy%canvas.getHeight() > 0){
                canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() - canvas.getWidth(), -cy%canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            } else if(cy%canvas.getHeight() < 0){
                canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth() - canvas.getWidth(), -cy%canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            }
        }
        if(cy%canvas.getHeight() > 0){
            canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth(), -cy%canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        } else if(cy%canvas.getHeight() < 0){
            canvas.getGraphicsContext2D().drawImage(Background, -cx%canvas.getWidth(), -cy%canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        }
    }

}
