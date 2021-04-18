package GameEngine.World.Entitys;

import GameEngine.Frame;
import GameEngine.World.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Astroid extends Entity {

    Frame frame;
    int immuneTick = -1;

    public Astroid(Frame frame) {
        super(frame);
        this.setZ_index(-1);
        this.setH((int) (Math.random() * (300 - 32) + 32));
        this.setW(this.getH());
        this.setRotation(Math.random() * 360);
        //System.out.println("Loading Image");
        Image img = this.getFrame().getWorld().getImageCache().getImage(String.format("file:rsc/entity_data/asteroid_%s.png", (int) (Math.random() * 10)));
        this.setSprite(img);

        //System.out.println("Loaded: " + this.getSprite().getUrl());
        this.setCanCollide(true);
        this.setMaxHp(100);
        this.setHp(100);
        this.enableHealthBar();
    }

    public void setImmuneTick(int delay) {
        this.setImmune(true);
        this.immuneTick = delay;
    }

    @Override
    public void move() {
        super.move();
        if (immuneTick > 0) {
            immuneTick--;
        } else if (immuneTick == 0) {
            this.setImmune(false);
            immuneTick--;
        }
    }

    @Override
    public void render(GraphicsContext gc, int cx, int cy, int cw, int ch, int w, int h, int mx, int my) {
        gc.save();
        gc.setFill(Color.web("#B2535C"));
        int tempX = (int) ((cw / 2) + (this.getX() - cx));
        int tempY = (int) ((ch / 2) + (this.getY() - cy));

        double factorY = (double) h / (double) ch;
        double factorX = (double) w / (double) cw;

        tempX = (int) ((double) tempX * factorX);
        tempY = (int) ((double) tempY * factorY);

        double tempW = ((double) this.getW() * factorX);
        double tempH = ((double) this.getH() * factorY);


        gc.translate(tempX, tempY);
        gc.rotate(this.getRotation());
        gc.drawImage(this.getSprite(), -tempW / 2.0, -tempH / 2.0, tempW, tempH);
        //gc.fillOval(-tempW/2.0, -tempH/2.0, tempW, tempH);
        gc.restore();
        //gc.fillRect(tempX - (tempW/2), tempY - (tempH/2), tempW, tempH);
    }
}
