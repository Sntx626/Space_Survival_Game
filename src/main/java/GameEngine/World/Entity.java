package GameEngine.World;

import javafx.scene.canvas.GraphicsContext;

public class Entity implements Comparable<Entity>{

    public enum HITBOX {
        rect,
        circle
    }

    HITBOX hitbox = HITBOX.circle;

    private double x, y;
    int z_index = 0;
    int w, h;

    double accX = 0, accY  = 0, velX = 0, velY = 0;
    int maxSpeed = 1;

    int hp = -1; // hp > 0 -> is alive; hp == 0 -> dead; hp == -1 -> No hp/invulnerable

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


    public boolean isColliding(Entity e) {
        if (this.hitbox == HITBOX.circle && e.hitbox == HITBOX.circle) {
            double dx = this.x - e.getX();
            double dy = this.y - e.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            return (distance < this.getW() + e.getW());
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

        }

        return false;
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
