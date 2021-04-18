package GameEngine.UI.Components;

import GameEngine.UI.Component;
import GameEngine.World.Entitys.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextBox extends Component {

    String content = "";

    public TextBox(String content, int x, int y) {
        this.content = content;
        this.setX(x);
        this.setY(y);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera, int w, int h) {
        gc.setFill(Color.WHITE);
        gc.save();
        gc.setFont(new Font("Arial", 40));
        gc.fillText(content, this.getX(), this.getY());

        gc.restore();
    }
}
