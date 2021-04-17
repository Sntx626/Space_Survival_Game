package GameEngine.World;

import GameEngine.Frame;
import GameEngine.UI.Components.HealthBar;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Entity implements Comparable<Entity>{

    Frame frame;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    boolean delete = false;

    public boolean isStopRender() {
        return stopRender;
    }

    public void setStopRender(boolean stopRender) {
        this.stopRender = stopRender;
    }

    boolean stopRender = true;




    public Entity(Frame frame) {
        this.frame = frame;
    }

    public enum HITBOX {
        rect,
        circle
    }


    public void setHitbox(HITBOX hitbox) {
        this.hitbox = hitbox;
    }

    HITBOX hitbox = HITBOX.circle;

    private double x, y;

    public ArrayList<Vector> getCollisions() {
        return collisions;
    }

    public void setCollisions(ArrayList<Vector> collisions) {
        this.collisions = collisions;
    }

    ArrayList<Vector> collisions = new ArrayList<Vector>();

    boolean canCollide = false;
    int z_index = 0;
    int w, h;

    double accX = 0, accY  = 0, velX = 0, velY = 0;
    int maxSpeed = 1;

    int hp = 0;
    int maxHp = -1; // hp > 0 -> is alive; hp == 0 -> dead; hp == -1 -> No hp/invulnerable
    HealthBar healthBar;

    public void enableHealthBar() {
        if (healthBar == null) {
            healthBar = new HealthBar(this);
            this.frame.getUi().addComponent(healthBar);
        }
    }

    public void disableHealthBar() {

    }

    public void addForce(double fX, double fY) {
        this.accY += fY;
        this.accX += fX;
    }

    public void move () {
        double mag = Math.sqrt(this.velX * this.velX + this.velY * this.velY);
        double fricX, fricY;
        if (mag != 0 && mag > 0.01 && mag < -0.01) {
            fricX = (this.velX * -1) / mag;
            fricY = (this.velY * -1) / mag;
        }else{
            fricX = this.velX * -1;
            fricY = this.velY * -1;
        }
        fricX *= 0.007;
        fricY *= 0.007;
        addForce(fricX, fricY);

        this.velX += this.accX;
        this.velY += this.accY;

        for (Vector v : this.getCollisions()) {
            this.velX *= (Math.abs(v.getX()/v.Length())*-1);
            this.velY *= (Math.abs(v.getY()/v.Length())*-1);
        }
        this.setCollisions(new ArrayList<Vector>());

        mag = Math.sqrt(this.velX * this.velX + this.velY * this.velY);
        if (mag > maxSpeed) {
            this.velY = (this.velY / mag) * maxSpeed;
            this.velX = (this.velX / mag) * maxSpeed;
        }

        this.accX = 0;
        this.accY = 0;
        this.setX(this.getX() + velX);
        this.setY(this.getY() + velY);
    }

    public boolean isCanCollide() {
        return canCollide;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public void onColliding(Entity e) {

    }


    public boolean isColliding(Entity e) {
        if (this.hitbox == HITBOX.circle && e.hitbox == HITBOX.circle) {
            double dx = this.x - e.getX();
            double dy = this.y - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            return (distance < this.getW()/2 + e.getW()/2);
        } else if (this.hitbox == HITBOX.rect && e.hitbox == HITBOX.rect) {
            double e1X = this.x - this.getW()/2;
            double e1Y = this.y - this.getH()/2;
            double e2X = e.getX() - e.getW()/2;
            double e2Y = e.getY() - e.getH()/2;
            return (e1X < e2X + e.getW() &&
                    e1X + this.getW() > e2X &&
                    e1Y < e2Y + e.getH() &&
                    e1Y + this.getH() > e2Y);
        } else {
            Entity Circle = this;
            Entity Rect = e;
            if (this.hitbox == HITBOX.rect) {
                Rect = this;
                Circle = e;
            }
            double distanceX = Math.abs(Circle.getX() - Rect.getX());
            double distanceY = Math.abs(Circle.getY() - Rect.getY());
            if (distanceX > (Rect.getW()/2 + Circle.getW()/2)) { return false; }
            if (distanceY > (Rect.getH()/2 + Circle.getH()/2)) { return false; }
            if (distanceX <= (Rect.getW()/2)) { return true; }
            if (distanceY <= (Rect.getW()/2)) { return true; }
            double cornerDistanceSq = Math.pow(distanceX - Rect.getW()/2, 2) +
                    Math.pow(distanceY - Rect.getH()/2, 2);
            return (cornerDistanceSq <= (Math.pow(Circle.getH(), 2)));

        }
    }

    /*
     * gc = GraphicsContext
     * cx = camera x
     * cy = camera y
     * cw = camera width
     * ch = camera height
     * w = width
     * h = height
     * mx = movement x
     * my = movement y
     */

    public double getAngleToMouse(int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);

        double vectorX = tempX - mx, vectorY = tempY - my;
        double vectorLength = Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));
        double normVectorX = vectorX/vectorLength, normalVectorY = vectorY/vectorLength;
        double angle = Math.toDegrees(Math.asin(normVectorX/1));
        if (normalVectorY < 0) {
            angle += 180;
        } else {
            angle *= -1;
        }
        return angle;
    }

    public Vector getViewPortCords (int cx, int cy, int cw, int ch, int w, int h) {
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);
        return new Vector(tempX, tempY);
    }
    public Vector getViewPortCords (double cx, double cy, double cw, double ch, double w, double h) {
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);
        return new Vector(tempX, tempY);
    }

    public Vector getViewPortSize (int cx, int cy, int cw, int ch, int w, int h) {

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);
        return new Vector(tempW, tempH);
    }
    public Vector getViewPortSize (double cx, double cy, double cw, double ch, double w, double h) {

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);
        return new Vector(tempW, tempH);
    }

    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my){

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getZ_index() {
        return z_index;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(this.getZ_index(), o.getZ_index());
    }
}
