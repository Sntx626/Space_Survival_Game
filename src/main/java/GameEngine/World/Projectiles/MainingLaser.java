package GameEngine.World.Projectiles;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Player;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MainingLaser extends Entity {

    Frame frame;
    Entity belongTo;
    double lastAngle = 0;

    public double getRenderLength() {
        return renderLength;
    }

    public void setRenderLength(double renderLength) {
        this.renderLength = renderLength;
    }

    double renderLength = 0;

    public MainingLaser(Frame frame, Entity belongTo) {
        super(frame);
        this.belongTo = belongTo;
        this.setW(100);
        this.setCanCollide(true);
        this.setHasPushback(false);
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        //System.out.println("Laser Fired");
        this.setX(belongTo.getX());
        this.setY(belongTo.getY());

        Vector playerCord = belongTo.getViewPortCords(cx, cy, cw, ch, w, h);

        lastAngle = belongTo.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);

        //double tempX = Math.sin(Math.toRadians(angle)) * 1000;
        //double tempY = Math.cos(Math.toRadians(angle)) * 1000;

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;


        gc.save();
        gc.setStroke(Color.RED);
        gc.setLineWidth(factorX * 10);
        gc.translate(playerCord.getX(), playerCord.getY());
        gc.rotate(lastAngle);
        gc.strokeLine(0, 0, 0, -renderLength * factorX);
        gc.restore();
    }

    public boolean pointInCircle(double px, double py, double cx, double cy, double r) {
        double distX = px - cx;
        double distY = py - cy;
        double distance = Math.sqrt(distX * distX + distY * distY);

        return distance <= r;
    }

    public boolean pointOnLine(double px, double py, double startx, double starty, double endx, double endy) {
        double dist1 = Math.sqrt(Math.pow(px - startx, 2) + Math.pow(py - starty, 2));
        double dist2 = Math.sqrt(Math.pow(px - endx, 2) + Math.pow(py - endy, 2));

        double linedist = Math.sqrt(Math.pow(startx - endx, 2) + Math.pow(starty - endy, 2));

        double buffer = 0.1;

        return (dist1 + dist2 >= linedist-buffer && dist1 + dist2 <= linedist+buffer);
    }

    public boolean lineToLine(Vector x1, Vector x2, Vector x3, Vector x4) {
        double uA = ((x4.getX()-x3.getX())*(x1.getY()-x3.getY()) - (x4.getY()-x3.getY())*(x1.getX()-x3.getX())) / ((x4.getY()-x3.getY())*(x2.getX()-x1.getX()) - (x4.getX()-x3.getX())*(x2.getY()-x1.getY()));
        double uB = ((x2.getX()-x1.getX())*(x1.getY()-x3.getY()) - (x2.getY()-x1.getY())*(x1.getX()-x3.getX())) / ((x4.getY()-x3.getY())*(x2.getX()-x1.getX()) - (x4.getX()-x3.getX())*(x2.getY()-x1.getY()));
        return (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1);
    }

    public Vector getCollidingPoint(double x, double y, int r) {
        double px = this.belongTo.getX(), py = this.belongTo.getY();
        double lastAngle = 0.0;
        if (this.belongTo.getClass() == Player.class) {
            lastAngle = ((Player)this.belongTo).getLastAngle();
        }

        double endx = px + Math.sin(Math.toRadians(lastAngle)) * this.getW(), endy = py - Math.cos(Math.toRadians(lastAngle)) * this.getW();

        double distX = px - endx;
        double distY = py - endy;
        double len = Math.sqrt(distX * distX + distY * distY);
        double dot = (((x-px)*(endx-px))+((y-py)*(endy-py)))/Math.pow(len, 2);
        double closestX = px + (dot * (endx - px));
        double closestY = py + (dot * (endy - py));
        return new Vector(closestX, closestY);
    }

    @Override
    public boolean isColliding(Entity e) {
        boolean isNotPlayer = e.getClass() != Player.class;

        if (e.getHitbox() == HITBOX.rect) {
            double px = this.belongTo.getX(), py = this.belongTo.getY();
            double endx = px + Math.sin(Math.toRadians(lastAngle)) * this.getW(), endy = py - Math.cos(Math.toRadians(lastAngle)) * this.getW();
            boolean left =   lineToLine(new Vector(px, py), new Vector(endx, endy), new Vector(e.getX() - e.getW()/2, e.getY() - e.getH()/2), new Vector(e.getX() - e.getW()/2, e.getY() + e.getH()/2));
            boolean right =  lineToLine(new Vector(px, py), new Vector(endx, endy), new Vector(e.getX() + e.getW()/2, e.getY() - e.getH()/2), new Vector(e.getX() + e.getW()/2, e.getY() + e.getH()/2));
            boolean top =    lineToLine(new Vector(px, py), new Vector(endx, endy), new Vector(e.getX() - e.getW()/2, e.getY() - e.getH()/2), new Vector(e.getX() + e.getW()/2, e.getY() - e.getH()/2));
            boolean bottom = lineToLine(new Vector(px, py), new Vector(endx, endy), new Vector(e.getX() - e.getW()/2, e.getY() + e.getH()/2), new Vector(e.getX() + e.getW()/2, e.getY() + e.getH()/2));
            return left || right || top || bottom;
        } else {
            double px = this.belongTo.getX(), py = this.belongTo.getY();
            double endx = px + Math.sin(Math.toRadians(lastAngle)) * this.getW(), endy = py - Math.cos(Math.toRadians(lastAngle)) * this.getW();
            boolean inside1 = pointInCircle(px, py, e.getX(), e.getY(), e.getW()/2.0);
            boolean inside2 = pointInCircle(endx, endy, e.getX(), e.getY(), e.getW()/2.0);
            if (inside1 || inside2) return true;

            double distX = px - endx;
            double distY = py - endy;
            double len = Math.sqrt(distX * distX + distY * distY);
            double dot = (((e.getX()-px)*(endx-px))+((e.getY()-py)*(endy-py)))/Math.pow(len, 2);
            double closestX = px + (dot * (endx - px));
            double closestY = py + (dot * (endy - py));

            boolean onSegment = pointOnLine(closestX, closestY, px, py, endx, endy);
            if (!onSegment) return false;
            distX = closestX - e.getX();
            distY = closestY - e.getY();
            double distance = Math.sqrt( (distX*distX) + (distY*distY) );
            return distance <= e.getW()/2.0;
        }
    }
}