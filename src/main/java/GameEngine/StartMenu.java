package GameEngine;

import Viewer.Renderer;

public class StartMenu extends Frame{
    public StartMenu(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void init() {
        this.setWindowTitle("Start Menu");
    }
}
