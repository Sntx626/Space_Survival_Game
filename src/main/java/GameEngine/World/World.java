package GameEngine.World;

import GameEngine.Frame;
import Viewer.Renderer;

import java.util.ArrayList;

public class World {

    Frame frame;

    ArrayList<Entity> entities = new ArrayList<Entity>();

    public World(Frame frame) {
        this.frame = frame;
    }
}