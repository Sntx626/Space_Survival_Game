package GameEngine.Frames;

import GameEngine.Background.Background;
import GameEngine.Frame;
import GameEngine.UI.UI;
import GameEngine.World.World;
import Viewer.Renderer;

public class StartMenu extends Frame {

    public StartMenu(Renderer renderer) {
        super(renderer);
        // generate ui, world & background -> pass to frame
        setUi(new UI(this));
        setWorld(new World(this));
        setBackground(new Background(this));
    }

    @Override
    public void init() {
        this.setWindowTitle("Start Menu");
    }
}
