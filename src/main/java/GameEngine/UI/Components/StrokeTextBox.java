package GameEngine.UI.Components;

import GameEngine.UI.Component;
import GameEngine.World.Entitys.Camera;
import javafx.scene.canvas.GraphicsContext;

public class StrokeTextBox extends Component {

    String content = "";

    public StrokeTextBox(String content, int x, int y) {
        this.content = content;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera, int w, int h){
        gc.strokeText(content, this.getX(), this.getY());
    }
}
