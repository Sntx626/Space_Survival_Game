package GameEngine.UI.Components;

import GameEngine.UI.Component;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Camera;
import GameEngine.World.Entitys.Fog;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;

public class HealthBar extends Component {

    Entity entity;
    int healthBarWidth = 60;
    int healthBarHeight = 10;

    public HealthBar(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        Vector v = this.entity.getViewPortCords(camera.getX(), camera.getY(), camera.getW(), camera.getH(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        ArrayList<Entity> entities = (ArrayList<Entity>)this.entity.getFrame().getWorld().getEntities().clone();
        double fogSize = -1.0;
        for (Entity e : entities) {
            if (e.getClass() == Fog.class) {
                fogSize = ((Fog) e).getDiameter();
                break;
            }
        }

        Vector toHealthBarTarget = new Vector(camera.getX()-this.entity.getX(), camera.getY()-this.entity.getY());
        System.out.println("Fog Size: " + fogSize + ", discance: " +  toHealthBarTarget.Length());

        if (toHealthBarTarget.Length() <= fogSize) {
            gc.save();
            gc.setLineWidth(this.healthBarHeight);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setStroke(Color.web("#1c1c1c"));
            gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+this.healthBarWidth/2, v.getY()-entity.getH()/2-20);
            gc.setStroke(Color.web("#26a65b"));
            int healthLeftBar = (int)(this.getHpPercentage() * this.healthBarWidth - this.healthBarWidth/2);
            gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            gc.restore();
        }
    }

    private double getHpPercentage() {
        return (double)this.entity.getHp()/(double)this.entity.getMaxHp();
    }

    public int getHealthBarWidth() {
        return healthBarWidth;
    }

    public void setHealthBarWidth(int healthBarWidth) {
        this.healthBarWidth = healthBarWidth;
    }

    public int getHealthBarHeight() {
        return healthBarHeight;
    }

    public void setHealthBarHeight(int healthBarHeight) {
        this.healthBarHeight = healthBarHeight;
    }
}
