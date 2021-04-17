package GameEngine.World;

import javafx.scene.canvas.GraphicsContext;

public class Entity implements Comparable<Entity>{



    private double x, y;
    int z_index = 0;
    int w, h;

    double accX = 0, accY  = 0, velX = 0, velY = 0;
    int maxSpeed = 1;

    public void addForce(double fX, double fY) {
        this.accY += fY;
        this.accX += fX;
    }

    public void move () {
        double mag = Math.sqrt(this.velX * this.velX + this.velY * this.velY);
        double fricX = 0, fricY = 0;
        if (mag != 0 && mag > 0.01 && mag < -0.01) {
            fricX = (this.velX * -1) / mag;
            fricY = (this.velY * -1) / mag;
        }else{
            fricX = this.velX * -1;
            fricY = this.velY * -1;
        }
        fricX *= 0.005;
        fricY *= 0.005;
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


    /*
     * gc = GraphicsContext
     * cx = current x
     * cy = current y
     * cw = current width
     * ch = current height
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
        return (this.getZ_index() < o.getZ_index()) ? -1 : (this.getZ_index() == o.getZ_index()) ? 0 : 1;
    }
}
