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
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);

        double[] playerModelX =  {
                -(tempW/2),
                (tempW/2),
                0
        };
        double[] playerModelY =  {
                (tempH/2),
                (tempH/2),
                -(tempH/2)
        };
        double roatet = 0;

        double angle = this.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);

        gc.translate(tempX, tempY);
        gc.rotate(angle);
        //gc.fillPolygon(playerModelX, playerModelY, 3);
        gc.fillRect(-tempW/2, -tempH/2, tempW, tempH);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);

    }
}
