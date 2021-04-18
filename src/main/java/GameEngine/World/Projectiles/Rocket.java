package GameEngine.World.Projectiles;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.AstroidPiece;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rocket extends Entity {

    Frame frame;
    Entity belongTo;
    int damage = 100;

    public Rocket(Frame frame, Entity belongTo) {
        super(frame);
        this.frame = frame;
        this.belongTo = belongTo;
        this.setX(belongTo.getX());
        this.setY(belongTo.getY());
        this.setH(32);
        this.setW(32);
        this.setMaxSpeed(200);
        this.setCanCollide(true);
        this.setHasPushback(false);
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {

        Vector pos = this.getViewPortCords(cx, cy, cw, ch, w, h);
        Vector size = this.getViewPortSize(cx, cy, cw, ch, w, h);

        gc.save();
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(pos.getX() - size.getX()/2 ,pos.getY() - size.getY()/2, size.getX(), size.getY());
        gc.restore();
    }

    @Override
    public void onColliding(Entity e) {
        if(!(e.equals(this.belongTo)) && !(e.getClass().equals(AstroidPiece.class))){
            if(e.getClass().equals(Rocket.class)){
                if(!(this.belongTo.equals(((Rocket)e).belongTo))){
                    e.setHp(e.getHp() - damage);
                    this.setDelete(true);
                }
            }else {
                e.setHp(e.getHp() - damage);
                this.setDelete(true);
            }
        }
    }
}
