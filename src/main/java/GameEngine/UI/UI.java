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

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
    }

}
