package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AstroidPiece extends Entity {

    public enum Type {
        Energy,
        Money ,
        Sight,
        Rocket
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    Type type = Type.Energy;

    public AstroidPiece(Frame frame, Entity astroid, Vector initForce) {
        super(frame);
        this.setCanCollide(true);
        this.setMaxSpeed(3);
        this.setX(astroid.getX());
        this.setY(astroid.getY());
        this.setW((int)(Math.random() * (astroid.getW()/3 - 20) + 20));
        if (Math.random() > 0.5) {
            this.setType(Type.Money);
            this.setSprite(frame.getWorld().getImageCache().getImage("file:rsc/entity_data/DebrisScore.png"));
        } else if (Math.random() > 0.2) {
            this.setType(Type.Rocket);
            this.setSprite(frame.getWorld().getImageCache().getImage("file:rsc/entity_data/DebrisRocket.png"));
        } else if (Math.random() > 0.2) {
            this.setType(Type.Sight);
            this.setSprite(frame.getWorld().getImageCache().getImage("file:rsc/entity_data/DebrisSight.png"));
        } else {
            this.setSprite(frame.getWorld().getImageCache().getImage("file:rsc/entity_data/DebrisEnergy.png"));
        }


        this.setZ_index(-1);
        this.setH(this.getW());
        this.addForce(initForce.getX(), initForce.getY());
        this.setHasPushback(false);
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.setFill(Color.web("#eb6a76"));
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);


        gc.translate(tempX, tempY);
        //gc.fillOval(-tempW/2.0, -tempH/2.0, tempW, tempH);
        //gc.translate(tempX, tempY);
        gc.drawImage(this.getSprite(), -tempW, -tempH, tempW*2, tempH*2);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }
}
