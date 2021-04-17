package GameEngine.UI.Components;

import GameEngine.UI.Component;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Camera;
import GameEngine.World.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class HealthBar extends Component {

    Entity entity;

    public HealthBar(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        Vector v = this.entity.getViewPortCords(camera.getX(), camera.getY(), camera.getW(), camera.getH(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        if (this.entity.getX() > camera.getX()-camera.getW()/2 && this.entity.getX() < camera.getX()+camera.getW()/2 &&
            this.entity.getY() > camera.getY()-camera.getH()/2 && this.entity.getY() < camera.getY()+camera.getH()/2)
        {
            gc.save();
            gc.setLineWidth(10);
            gc.setStroke(Color.web("#1c1c1c"));
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.strokeLine(v.getX()-30, v.getY()-entity.getH()/2-20, v.getX()+30, v.getY()-entity.getH()/2-20);

            gc.restore();
        }
    }
}
