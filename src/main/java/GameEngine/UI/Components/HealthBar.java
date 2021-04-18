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
                fogSize = ((Fog) e).getW()/4;
                break;
            }
        }

        Vector toHealthBarTarget = new Vector(camera.getX()-this.entity.getX(), camera.getY()-this.entity.getY());
        if (toHealthBarTarget.Length() <= fogSize) {
            gc.save();
            gc.setLineWidth(this.healthBarHeight);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setStroke(Color.web("#1c1c1c"));
            gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+this.healthBarWidth/2, v.getY()-entity.getH()/2-20);
            double hpPercentage = this.getHpPercentage();
            if (hpPercentage >= 1) {
                gc.setStroke(Color.web("#2cba00"));
                int healthLeftBar = (int)(hpPercentage * this.healthBarWidth - this.healthBarWidth/2);
                gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            } else if (hpPercentage >= 0.75) {
                gc.setStroke(Color.web("#a3ff00"));
                int healthLeftBar = (int)(hpPercentage * this.healthBarWidth - this.healthBarWidth/2);
                gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            } else if (hpPercentage >= 0.5) {
                gc.setStroke(Color.web("#fff400"));
                int healthLeftBar = (int)(hpPercentage * this.healthBarWidth - this.healthBarWidth/2);
                gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            } else if (hpPercentage >= 0.25) {
                gc.setStroke(Color.web("#ffa700"));
                int healthLeftBar = (int)(hpPercentage * this.healthBarWidth - this.healthBarWidth/2);
                gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            } else if (hpPercentage >= 0) {
                gc.setStroke(Color.web("#ff0000"));
                int healthLeftBar = (int)(hpPercentage * this.healthBarWidth - this.healthBarWidth/2);
                gc.strokeLine(v.getX()-this.healthBarWidth/2, v.getY()-entity.getH()/2-20, v.getX()+healthLeftBar, v.getY()-entity.getH()/2-20);
            }
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
