package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AstroidPiece extends Entity {


    public AstroidPiece(Frame frame, Entity astroid, Vector initForce) {
        super(frame);
        this.setCanCollide(true);
        this.setMaxSpeed(3);
        this.setX(astroid.getX());
        this.setY(astroid.getY());
        this.setW((int)(Math.random() * (astroid.getW()/2 - 20) + 20));
        this.setH(this.getW());
        this.addForce(initForce.getX(), initForce.getY());
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.setFill(Color.web("#B2535C"));
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);


        gc.translate(tempX, tempY);
        gc.fillOval(-tempW/2.0, -tempH/2.0, tempW, tempH);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }
}
