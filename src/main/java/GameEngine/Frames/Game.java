package GameEngine.Frames;

import GameEngine.Background.Background;
import GameEngine.Frame;
import GameEngine.UI.Components.TextBox;
import GameEngine.UI.UI;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Astroid;
import GameEngine.World.Entitys.Fog;
import GameEngine.World.Entitys.Player;
import GameEngine.World.World;
import Viewer.Renderer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game extends Frame implements Runnable{

    Player p;
    Fog f;
    boolean pw = false, ps = false, pa = false, pd = false;

    public Game(Renderer renderer) {
        super(renderer);
        // generate ui, world & background -> pass to frame
        setUi(new UI(this));
        setWorld(new World(this));
        setBackground(new Background("file:rsc/background_data/Background.jpg", renderer.getcanvas()));

        this.getWorld().setCanZoom(true);

        this.getUi().addComponent(new TextBox("Lorem Ipsum", 10, 20));
        this.getUi().addComponent(new TextBox("Dolor Sit Amet", 10, 40));

        p = new Player(this);
        p.setZ_index(-2);
        p.setX(0);
        p.setY(0);
        p.setH(64);
        p.setW(64);
        p.setCanCollide(true);

        f = new Fog(this);
        f.setH(1080);
        f.setW(1080);

        Astroid a = new Astroid(this);
        a.setZ_index(-1);
        a.setX(-300);
        a.setY(-250);
        a.setH(100);
        a.setW(100);
        a.setCanCollide(true);
        this.getWorld().addEntity(a);
        this.getWorld().addEntity(p);
        this.getWorld().addEntity(f);
        new Thread(this).start();
    }

    @Override
    public void keyIsPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.D) {
            pd = true;
        }
        if (key.getCode() == KeyCode.A) {
            pa = true;
        }
        if (key.getCode() == KeyCode.W) {
            pw = true;
        }
        if (key.getCode() == KeyCode.S) {
            ps = true;
        }
    }


    @Override
    public void keyIsReleased(KeyEvent key) {
        if (key.getCode() == KeyCode.D) {
            pd = false;
        }
        if (key.getCode() == KeyCode.A) {
            pa = false;
        }
        if (key.getCode() == KeyCode.W) {
            pw = false;
        }
        if (key.getCode() == KeyCode.S) {
            ps = false;
        }
    }

    @Override
    public void init() {
        this.setWindowTitle("Start Menu");
    }

    @Override
    public void run() {
        try {



            while (true) {
                double accX = 0,  accY = 0;
                double responsive = 0.01;
                if (pd) {
                   accX += 1*responsive;
                }
                if (pa) {
                    accX -= 1*responsive;
                }
                if (pw) {
                    accY -= 1*responsive;
                }
                if (ps) {
                    accY += 1*responsive;
                }
                this.p.addForce(accX, accY);

                this.getWorld().run();
                this.getWorld().getCamera().setX(p.getX());
                this.getWorld().getCamera().setY(p.getY());
                f.setX(p.getX());
                f.setY(p.getY());

                for (Entity e1: this.getWorld().getEntities()) {
                    if (e1.isCanCollide()){
                        for (Entity e2 : this.getWorld().getEntities()) {
                            if (e1 != e2 && e2.isCanCollide()) {
                                if (e1.isColliding(e2)) {
                                    e1.onColliding(e2);
                                }
                            }
                        }
                    }
                }

                Thread.sleep(1);
            }
        } catch (InterruptedException e) {

        }
    }
}
