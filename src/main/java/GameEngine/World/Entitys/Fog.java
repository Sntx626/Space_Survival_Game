package GameEngine.World.Entitys;

import GameEngine.World.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Fog  extends Entity {

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.translate((double)w/2.0, (double)h/2.0);
        Rectangle rect = new Rectangle(0, 0, w, h);
        Circle circ = new Circle(h / 2, h / 2, Math.min(w, h) / 2);

        ArrayList<Double> pathX = new ArrayList<Double>();
        ArrayList<Double> pathY = new ArrayList<Double>();
        int circlePoints = 360;
        pathX.add(0.0);
        pathY.add((double)h/2.0);
        pathX.add(-(double)w/2.0);
        pathY.add((double)h/2.0);
        pathX.add(-(double)w/2.0);
        pathY.add(-(double)h/2.0);
        pathX.add((double)w/2.0);
        pathY.add(-(double)h/2.0);
        pathX.add((double)w/2.0);
        pathY.add((double)h/2.0);
        pathX.add(0.0);
        pathY.add((double)h/2.0);
        for (double i = 0; i <= 360; i += 360/circlePoints) {
            pathX.add(Math.sin(Math.toRadians(i)) * ((double)this.getW()/2.0));
            pathY.add(Math.cos(Math.toRadians(i)) * ((double)this.getH()/2.0));
        }

        double[] modelX = new double[pathX.size()];
        double[] modelY = new double[pathY.size()];
        for (int i = 0; i < pathX.size(); i++) {
            modelX[i] = pathX.get(i);
            modelY[i] = pathY.get(i);
        }


        gc.setFill(Color.BLACK);
        gc.fillPolygon(modelX, modelY, modelX.length);

        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }
}
