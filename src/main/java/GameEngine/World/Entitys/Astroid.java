package GameEngine.World.Entitys;

import GameEngine.World.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Astroid extends Entity {
    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.setFill(Color.web("#B2535C"));
        int tempX = (int)((cw / 2) + (this.getX()-cx));
        int tempY = (int)((ch / 2) + (this.getY()-cy));

        double factorY = (double)h / (double)ch;
        double factorX = (double)w / (double)cw;

        tempX = (int)((double)tempX * factorX);
        tempY = (int)((double)tempY * factorY);

        int tempW = (int)((double) this.getW() * factorX);
        int tempH = (int)((double) this.getH() * factorY);


        gc.translate(tempX, tempY);
        gc.fillOval(-tempW/2, -tempH/2, tempW, tempH);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }
}
