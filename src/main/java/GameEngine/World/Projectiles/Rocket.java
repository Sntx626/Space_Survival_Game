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

    public Rocket(Frame frame, Entity belongTo) {
        super(frame);
        this.frame = frame;
        this.belongTo = belongTo;
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        this.setX(belongTo.getX());
        this.setY(belongTo.getY());
        double speed = 5;


        Vector rocketCord = belongTo.getViewPortCords(cx, cy, cw, ch, w, h);

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        gc.save();
        //if(one){
            double angle = belongTo.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);
            gc.rotate(angle);
            one = false;
        //}
        gc.translate(rocketCord.getX()*factorX, rocketCord.getY()*factorY);
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(rocketCord.getX(), rocketCord.getY(), 64*factorX,64*factorY);
        gc.restore();
    }
}
