package GameEngine.World.Projectiles;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MainingLaser extends Entity {

    Frame frame;
    Entity belongTo;

    public MainingLaser(Frame frame, Entity belongTo) {
        super(frame);
        this.belongTo = belongTo;

    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        System.out.println("Laser Fired");
        this.setX(belongTo.getX());
        this.setY(belongTo.getY());

        Vector playerCord = belongTo.getViewPortCords(cx, cy, cw, ch, w, h);

        double angle = belongTo.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);

        //double tempX = Math.sin(Math.toRadians(angle)) * 1000;
        //double tempY = Math.cos(Math.toRadians(angle)) * 1000;

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;


        gc.save();
        gc.setStroke(Color.RED);
        gc.setLineWidth(factorX * 10);
        gc.translate(playerCord.getX(), playerCord.getY());
        gc.rotate(angle);
        gc.strokeLine(0, 0, 0, -200 * factorX);
        gc.restore();
    }
}