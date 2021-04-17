package GameEngine.UI;

import GameEngine.Frame;

import java.util.ArrayList;

public class UI {

    Frame frame;

    ArrayList<Component> components = new ArrayList<>();

    public UI(Frame frame) {
        this.frame = frame;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public ArrayList<Component> addComponent() {
        return components;
    }

    public ArrayList<Component> removeComponent() {
        return components;
    }

}
