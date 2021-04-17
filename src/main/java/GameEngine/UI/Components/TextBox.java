package GameEngine.UI.Components;

import GameEngine.UI.Component;
import javafx.scene.canvas.GraphicsContext;

public class TextBox extends Component {

    String content = "";

    public TextBox(String content, int x, int y) {
        this.content = content;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.fillText(content, this.getX(), this.getY());
    }
}
