package GameEngine.World;

import GameEngine.Frame;
import GameEngine.UI.Components.HealthBar;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Entity implements Comparable<Entity> {

    Frame frame;

    Image sprite;

    boolean isImmune = false;
    boolean hasPushback = true;
    double friction = 0.007;
    boolean delete = false;
    boolean stopRender = true;
    HITBOX hitbox = HITBOX.circle;
    ArrayList<Vector> collisions = new ArrayList<Vector>();
    boolean canCollide = false;
    int z_index = 0;
    int w, h;
    double accX = 0, accY = 0, velX = 0, velY = 0;
    int maxSpeed = 1;
    int hp = 0;
    int maxHp = -1; // hp > 0 -> is alive; hp == 0 -> dead; hp == -1 -> No hp/invulnerable
    HealthBar healthBar;
    private double x, y, rotation;

    public Entity(Frame frame) {
        this.frame = frame;
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setImmune(boolean immune) {
        isImmune = immune;
    }

    public boolean isHasPushback() {
        return hasPushback;
    }

    public void setHasPushback(boolean hasPushback) {
        this.hasPushback = hasPushback;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isStopRender() {
        return stopRender;
    }

    public void setStopRender(boolean stopRender) {
        this.stopRender = stopRender;
    }

    public Frame getFrame() {
        return frame;
    }

    public HITBOX getHitbox() {
        return hitbox;
    }

    public void setHitbox(HITBOX hitbox) {
        this.hitbox = hitbox;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public ArrayList<Vector> getCollisions() {
        return collisions;
    }

    public void setCollisions(ArrayList<Vector> collisions) {
        this.collisions = collisions;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void damage(int amount) {
        System.out.println("Got Damage");
        if (!this.isImmune) {
            //System.out.println("Hit Damage");
            this.setHp(this.getHp() - amount);
        }
    }

    public void enableHealthBar() {
        if (healthBar == null) {
            healthBar = new HealthBar(this);
            this.frame.getUi().addComponent(healthBar);
        } else {
            //System.out.println("Health Bar already enabled!");
        }
    }

    public void disableHealthBar() {
        if (healthBar != null) {
            this.frame.getUi().removeComponent(healthBar);
            healthBar = null;
        } else {
            //System.out.println("Health Bar already disabled!");
        }
    }

    public void addForce(double fX, double fY) {
        this.accY += fY;
        this.accX += fX;
    }

    public void move() {
        double mag = Math.sqrt(this.velX * this.velX + this.velY * this.velY);
        double fricX, fricY;
        if (mag != 0 && mag > 0.01 && mag < -0.01) {
            fricX = (this.velX * -1) / mag;
            fricY = (this.velY * -1) / mag;
        } else {
            fricX = this.velX * -1;
            fricY = this.velY * -1;
        }
        fricX *= friction;
        fricY *= friction;
        addForce(fricX, fricY);

        this.velX += this.accX;
        this.velY += this.accY;

        for (Vector v : this.getCollisions()) {
            double length = v.Length();
            if (length > -0.0001 && length < 0.0001) {
                length = 0.1;
            }
            this.velX *= (Math.abs((v.getX() / length) + (1 + Math.random() / 10)) * -1);
            this.velY *= (Math.abs((v.getY() / length) + (1 + Math.random() / 10)) * -1);
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
        if (isHasPushback() && e.isHasPushback()) {
            double momX = this.getX() - e.getX();
            double momY = this.getY() - e.getY();
            this.getCollisions().add(new Vector(momX, momY));
            this.addForce(Math.random() * 2 - 1, Math.random() * 2 - 1);
            this.damage(1);
        }
    }

    public boolean isColliding(Entity e) {
        if (this.hitbox == HITBOX.circle && e.hitbox == HITBOX.circle) {
            double dx = this.x - e.getX();
            double dy = this.y - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            return (distance < this.getW() / 2 + e.getW() / 2);
        } else if (this.hitbox == HITBOX.rect && e.hitbox == HITBOX.rect) {
            double e1X = this.x - this.getW() / 2;
            double e1Y = this.y - this.getH() / 2;
            double e2X = e.getX() - e.getW() / 2;
            double e2Y = e.getY() - e.getH() / 2;
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
            if (distanceX > (Rect.getW() / 2 + Circle.getW() / 2)) {
                return false;
            }
            if (distanceY > (Rect.getH() / 2 + Circle.getH() / 2)) {
                return false;
            }
            if (distanceX <= (Rect.getW() / 2)) {
                return true;
            }
            if (distanceY <= (Rect.getW() / 2)) {
                return true;
            }
            double cornerDistanceSq = Math.pow(distanceX - Rect.getW() / 2, 2) +
                    Math.pow(distanceY - Rect.getH() / 2, 2);
            return (cornerDistanceSq <= (Math.pow(Circle.getH(), 2)));

        }
    }

    public double getAngleToMouse(int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        int tempX = (int) ((cw / 2) + (this.getX() - cx));
        int tempY = (int) ((ch / 2) + (this.getY() - cy));

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        tempX = (int) ((double) tempX * factorX);
        tempY = (int) ((double) tempY * factorY);

        double vectorX = tempX - mx, vectorY = tempY - my;
        double vectorLength = Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));
        double normVectorX = vectorX / vectorLength, normalVectorY = vectorY / vectorLength;
        double angle = Math.toDegrees(Math.asin(normVectorX / 1));
        if (normalVectorY < 0) {
            angle += 180;
        } else {
            angle *= -1;
        }
        return angle;
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

    public Vector getViewPortCords(int cx, int cy, int cw, int ch, int w, int h) {
        int tempX = (int) ((cw / 2) + (this.getX() - cx));
        int tempY = (int) ((ch / 2) + (this.getY() - cy));

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        tempX = (int) ((double) tempX * factorX);
        tempY = (int) ((double) tempY * factorY);
        return new Vector(tempX, tempY);
    }

    public Vector getViewPortCords(double cx, double cy, double cw, double ch, double w, double h) {
        int tempX = (int) ((cw / 2) + (this.getX() - cx));
        int tempY = (int) ((ch / 2) + (this.getY() - cy));

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        tempX = (int) ((double) tempX * factorX);
        tempY = (int) ((double) tempY * factorY);
        return new Vector(tempX, tempY);
    }

    public Vector getViewPortSize(int cx, int cy, int cw, int ch, int w, int h) {

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);
        return new Vector(tempW, tempH);
    }

    public Vector getViewPortSize(double cx, double cy, double cw, double ch, double w, double h) {

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);
        return new Vector(tempW, tempH);
    }

    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {

    }

    public int getZ_index() {
        return z_index;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(this.getZ_index(), o.getZ_index());
    }

    public enum HITBOX {
        rect,
        circle
    }
}
