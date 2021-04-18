package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Fog  extends Entity {

    Image img = new Image("file:rsc/entity_data/Fog.png");
    Frame frame;

    double diameter;

    public Fog(Frame frame) {
        super(frame);
        this.setW(1200);
        this.setH(1200);
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.translate(w/2, h/2);
        //gc.translate((double)w/2.0, (double)h/2.0);
        double factorX = (double)w / (double)cw / 2;
        double factorY = (double)h / (double)ch / 2;

        ArrayList<Double> pathX = new ArrayList<Double>();
        ArrayList<Double> pathY = new ArrayList<Double>();
        int circlePoints = 100;
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
            pathX.add(Math.sin(Math.toRadians(i)) * ((double)(this.getW()/2.0)*factorX));
            pathY.add(Math.cos(Math.toRadians(i)) * ((double)(this.getH()/2.0)*factorY));
        }

        double[] modelX = new double[pathX.size()];
        double[] modelY = new double[pathY.size()];
        for (int i = 0; i < pathX.size(); i++) {
            modelX[i] = pathX.get(i);
            modelY[i] = pathY.get(i);
        }

        gc.setFill(Color.BLACK);
        gc.fillPolygon(modelX, modelY, modelX.length);
        //System.out.println(getClass().getResource("files:rsc/entity_data/Fog.jpg"));

        diameter = 0.55 * factorX * (double)this.getW();
        gc.scale(factorX * ((double)this.getW() / 1080.0), factorY * ((double)this.getW() / 1080.0));
        gc.drawImage(img, -img.getWidth()/2, -img.getHeight()/2);

        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }

    public double getDiameter() {
        return diameter;
    }
}
