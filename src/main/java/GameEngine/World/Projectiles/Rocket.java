package GameEngine.World.Projectiles;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rocket extends Entity {

    Frame frame;
    Entity belongTo;
    boolean one = true;
    double force;

    public Rocket(Frame frame, Entity belongTo) {
        super(frame);
        this.frame = frame;
        this.belongTo = belongTo;
        this.setX(belongTo.getX());
        this.setY(belongTo.getY());
        this.setH(64);
        this.setW(64);
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {

        Vector pos = this.getViewPortCords(cx, cy, cw, ch, w, h);


        gc.save();
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(pos.getX() ,pos.getY(),this.getW(),this.getH());
        gc.restore();
    }
}
