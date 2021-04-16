package GameEngine;

import GameEngine.Entitys.Entity;
import GameEngine.UI.Component;
import Viewer.Renderer;

import java.util.ArrayList;

public class Frame {

    String windowTitle, windowIcon;
    int width, height;

    ArrayList<Component> foreground = new ArrayList<Component>();
    ArrayList<Entity> middleground = new ArrayList<Entity>();
    String background;

    Renderer renderer;

    public Frame(Renderer renderer) {
        this.renderer = renderer;
    }

    public void init () {

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
