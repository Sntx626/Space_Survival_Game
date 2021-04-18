package GameEngine.Background;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background {

    String path;
    Canvas canvas;
    Image Background;

    public Background(String path, Canvas canvas) {
        this.path = path;
        this.canvas = canvas;
        Background = new Image(path);
    }

    public void render(double cx, double cy) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth(), -cy % canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        if (cx % canvas.getWidth() > 0) {
            canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() + canvas.getWidth(), -cy % canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            if (cy % canvas.getHeight() > 0) {
                canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() + canvas.getWidth(), -cy % canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            } else if (cy % canvas.getHeight() < 0) {
                canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() + canvas.getWidth(), -cy % canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            }
        } else if (cx % canvas.getWidth() < 0) {
            canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() - canvas.getWidth(), -cy % canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            if (cy % canvas.getHeight() > 0) {
                canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() - canvas.getWidth(), -cy % canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            } else if (cy % canvas.getHeight() < 0) {
                canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth() - canvas.getWidth(), -cy % canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
            }
        }
        if (cy % canvas.getHeight() > 0) {
            canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth(), -cy % canvas.getHeight() + canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        } else if (cy % canvas.getHeight() < 0) {
            canvas.getGraphicsContext2D().drawImage(Background, -cx % canvas.getWidth(), -cy % canvas.getHeight() - canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
        }
    }

}
