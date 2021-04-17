package GameEngine.World;

import GameEngine.Frame;
import GameEngine.World.Entitys.Camera;
import Viewer.Renderer;

import java.util.ArrayList;

public class World{

    Frame frame;

    ArrayList<Entity> entities = new ArrayList<Entity>();
    Camera camera = new Camera();


    public World(Frame frame) {
        camera.setX(0);
        camera.setY(0);
        camera.setW(600);
        camera.setH(500);
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Camera getCamera() {
        return camera;
    }

    public void run() {
        this.camera.move();
        for (int i = 0; i < this.entities.size(); i++) {
            this.entities.get(i).move();
        }
    }
}