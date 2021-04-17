package GameEngine.World.Projectiles;

import GameEngine.Frame;
import GameEngine.World.Entity;
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
        double tempx = cx + (Math.sin(Math.toRadians(belongTo.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my))));
        double tempy = cy + (Math.cos(Math.toRadians(belongTo.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my))));

        gc.save();
        gc.setStroke(Color.RED);
        gc.setLineWidth(20);
        gc.strokeLine(cx, cy, tempx, tempy);
        gc.restore();
    }
}