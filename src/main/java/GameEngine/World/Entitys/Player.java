package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {

    Frame frame;
    double maxRockets = 0;
    double momRockets = 0;
    double energy = 0;
    double max_energy = 100;
    double lastAngle = 0;

    public Player(Frame frame) {
        super(frame);
        this.frame = frame;
        this.setZ_index(1);
        this.setH(64);
        this.setW(64);
        this.setSprite(new Image("file:rsc/entity_data/player.png"));
        this.setCanCollide(true);
        this.setMaxHp(10);
        this.setHp(10);
        this.enableHealthBar();
        this.setMaxRockets(20);
        this.setMomRockets(this.getMaxRockets());
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMax_energy() {
        return max_energy;
    }

    public void setMax_energy(double max_energy) {
        this.max_energy = max_energy;
    }

    public double getMaxRockets() {
        return maxRockets;
    }

    public void setMaxRockets(double maxRockets) {
        this.maxRockets = maxRockets;
    }

    public double getMomRockets() {
        return momRockets;
    }

    public void setMomRockets(double momRockets) {
        this.momRockets = momRockets;
    }

    public double getLastAngle() {
        return lastAngle;
    }

    public void setLastAngle(double lastAngle) {
        this.lastAngle = lastAngle;
    }

    @Override
    public void onColliding(Entity e) {
        super.onColliding(e);
        if (e.getClass() == AstroidPiece.class) {
            e.setDelete(true);
        }
    }

    @Override
    public void setHp(int hp) {
        super.setHp(hp);
        if (this.getHp() == 0) {
            this.frame.getRenderer().resetGame();
        }
    }


    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        //gc.setFill(Color.web("#4728d1"));
        Vector pos = this.getViewPortCords(cx, cy, cw, ch, w, h);
        Vector size = this.getViewPortSize(cx, cy, cw, ch, w, h);

        double[] playerModelX = {
                -(size.getX() / 2),
                (size.getX() / 2),
                0
        };
        double[] playerModelY = {
                (size.getY() / 2),
                (size.getY() / 2),
                -(size.getY() / 2)
        };

        lastAngle = this.getAngleToMouse(cx, cy, cw, ch, w, h, mx, my);

        gc.translate(pos.getX(), pos.getY());
        gc.rotate(lastAngle);
        //gc.fillPolygon(playerModelX, playerModelY, 3);
        //gc.fillRect(-size.getX()/2, -size.getY()/2, size.getX(), size.getY());
        gc.drawImage(this.getSprite(), -(size.getX()) / 2, -(size.getY()) / 2, size.getX(), size.getY());
        //gc.fillPolygon(playerModelX, playerModelY, 3);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);

    }
}
