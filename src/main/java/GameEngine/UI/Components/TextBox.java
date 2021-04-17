package GameEngine.UI.Components;

import GameEngine.UI.Component;
import GameEngine.World.Entitys.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TextBox extends Component {

    String content = "";

    public TextBox(String content, int x, int y) {
        this.content = content;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera){
        gc.setFill(Color.WHITE); gc.fillText(content, this.getX(), this.getY());
    }
}
