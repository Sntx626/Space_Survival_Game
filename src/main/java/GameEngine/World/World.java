package GameEngine.World;

import GameEngine.Frame;
import GameEngine.World.Entitys.Camera;
import GameEngine.World.Entitys.Player;
import Viewer.Renderer;

import java.util.ArrayList;

public class World{

    Frame frame;

    ImageCache imageCache;
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
        this.imageCache = new ImageCache();
    }

    public ImageCache getImageCache() {
        return imageCache;
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public void zoom(double delta) {
        if (canZoom) {

            Math.pow(delta, 2);
            delta /= 1;
            delta = (int)delta;
            camera.setH((int)(camera.getH() + (delta * 9)/10));
            if (camera.getH() > camera.getMaxZoomY()) {
                camera.setH(camera.getMaxZoomY());
            }
            if (camera.getH() < camera.getMinZoomY()) {
                camera.setH(camera.getMinZoomY());
            }
            camera.setW((int)(camera.getW() + (delta * 16)/10));
            if (camera.getW() > camera.getMaxZoomX()) {
                camera.setW(camera.getMaxZoomX());
            }
            if (camera.getW() < camera.getMinZoomX()) {
                camera.setW(camera.getMinZoomX());
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