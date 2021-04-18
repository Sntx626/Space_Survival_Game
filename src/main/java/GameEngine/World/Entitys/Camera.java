package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;

public class Camera extends Entity {

    Frame frame;
    int minZoomX = 16 * 40;
    int minZoomY = 9 * 40;
    int maxZoomX = 16 * 80 * 3;
    int maxZoomY = 9 * 80 * 3;

    public Camera(Frame frame) {
        super(frame);
    }

    public int getMinZoomX() {
        return minZoomX;
    }

    public void setMinZoomX(int minZoomX) {
        this.minZoomX = minZoomX;
    }

    public int getMinZoomY() {
        return minZoomY;
    }

    public void setMinZoomY(int minZoomY) {
        this.minZoomY = minZoomY;
    }

    public int getMaxZoomX() {
        return maxZoomX;
    }

    public void setMaxZoomX(int maxZoomX) {
        this.maxZoomX = maxZoomX;
    }

    public int getMaxZoomY() {
        return maxZoomY;
    }

    public void setMaxZoomY(int maxZoomY) {
        this.maxZoomY = maxZoomY;
    }
}
