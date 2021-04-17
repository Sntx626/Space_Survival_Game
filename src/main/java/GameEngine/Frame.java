package GameEngine;

import GameEngine.Background.Background;
import GameEngine.UI.UI;
import GameEngine.World.World;
import Viewer.Renderer;
import javafx.scene.input.KeyEvent;

public class Frame {

    Renderer renderer;

    String windowTitle, windowIcon;

    UI ui;                  // Foreground
    World world;            // Middleground
    Background background;  // Background

    public Frame(Renderer renderer) {
        this.renderer = renderer;
    }

    public void init() {

    }

    public void keyIsPressed(KeyEvent key) {

    }

    public void keyIsReleased(KeyEvent key) {

    }

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setWindowIcon(String windowIcon) {
        this.windowIcon = windowIcon;
        this.renderer.updateWindowIcon();
    }

    public String getWindowIcon() {
        return this.windowIcon;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        this.renderer.updateWindowTitle();
    }

    public String getWindowTitle() {
        return this.windowTitle;
    }

}
