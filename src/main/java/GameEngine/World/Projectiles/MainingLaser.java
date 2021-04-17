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
        int tempx, tempy;
        //if(belongTo.)
        gc.save();
        gc.setStroke(Color.RED);
        gc.setLineWidth(20);
        //gc.strokeLine(cx, cy, );
        gc.restore();
    }
}