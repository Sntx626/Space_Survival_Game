package GameEngine.World;

import GameEngine.Frame;
import GameEngine.World.Entitys.Camera;
import GameEngine.World.Entitys.Player;
import Viewer.Renderer;

import java.util.ArrayList;

public class World{

    Frame frame;

    ArrayList<Entity> entities = new ArrayList<Entity>();
    Camera camera;

    public boolean isCanZoom() {
        return canZoom;
    }

    public void setCanZoom(boolean canZoom) {
        this.canZoom = canZoom;
    }

    boolean canZoom = false;

    public World(Frame frame) {
        camera = new Camera(frame);
        camera.setX(0);
        camera.setY(0);
        camera.setW(16*100);
        camera.setH(9*100);
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public void zoom(double delta) {
        if (canZoom) {
            int minX = 16*40;
            int minY = 9*40;
            int maxX = 16*80*3;
            int maxY = 9*80*3;

            Math.pow(delta, 2);
            delta /= 1;
            delta = (int)delta;
            camera.setH((int)(camera.getH() + (delta * 9)/10));
            System.out.println((delta * 16)/10);
            if (camera.getH() > maxY) {
                camera.setH(maxY);
            }
            if (camera.getH() < minY) {
                camera.setH(minY);
            }
            System.out.println((delta * 9)/10);
            camera.setW((int)(camera.getW() + (delta * 16)/10));
            if (camera.getW() > maxX) {
                camera.setW(maxX);
            }
            if (camera.getW() < minX) {
                camera.setW(minX);
            }
        }
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

    public Player getPlayer(){
        Player player = null;
        for (Entity current : getEntities()) {
            if(current.getClass().equals(Player.class)){
                player = (Player) current;
            }
        }
        return player;
    }
}