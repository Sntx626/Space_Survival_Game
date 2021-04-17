package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Vector;
import GameEngine.World.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity {

    Frame frame;


    public Player(Frame frame) {
        super(frame);
        this.frame = frame;
    }

    @Override
    public void onColliding(Entity e) {
        if (e.getClass() == Astroid.class) {
            System.out.println("Ouch Astroid");
            double momX = this.getX()-e.getX();
            double momY = this.getY()-e.getY();
            this.getCollisions().add(new Vector(momX, momY));
        }
    }



    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.setFill(Color.web("#283ED1"));
        Vector pos = this.getViewPortCords(cx, cy, cw, ch, w, h);
        Vector size = this.getViewPortSize(cx, cy, cw, ch, w, h);

        double[] playerModelX =  {
                -(size.getX()/2),
                (size.getX()/2),
                0
        };
        double[] playerModelY =  {
                (size.getY()/2),
                (size.getY()/2),
                -(size.getY()/2)
        };

        double angle = this.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);

        gc.translate(pos.getX(), pos.getY());
        gc.rotate(angle);
        //gc.fillPolygon(playerModelX, playerModelY, 3);
        //gc.fillRect(-size.getX()/2, -size.getY()/2, size.getX(), size.getY());
        gc.fillPolygon(playerModelX, playerModelY, 3);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);

    }
}
