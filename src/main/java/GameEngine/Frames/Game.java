package GameEngine.Frames;

import GameEngine.Background.Background;
import GameEngine.Frame;
import GameEngine.UI.Components.TextBox;
import GameEngine.UI.UI;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Astroid;
import GameEngine.World.Entitys.Fog;
import GameEngine.World.Entitys.Player;
import GameEngine.World.Projectiles.MainingLaser;
import GameEngine.World.Projectiles.Rocket;
import GameEngine.World.World;
import Viewer.Renderer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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
        p.setZ_index(1);
        p.setX(0);
        p.setY(0);
        p.setH(64);
        p.setW(64);
        p.setCanCollide(true);
        p.enableHealthBar();

        f = new Fog(this);
        f.setH(3840);
        f.setW(3840);

        Astroid a = new Astroid(this);
        a.setZ_index(-1);
        a.setX(-300);
        a.setY(-250);
        a.setH(100);
        a.setW(100);
        a.setCanCollide(true);
        a.enableHealthBar();

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
        if(key.getCode() == KeyCode.R){
            System.out.println("Rocked Fired");
            Rocket rocket = new Rocket(this, this.getWorld().getPlayer());
            rocket.setZ_index(-1);
            this.getWorld().addEntity(rocket);
        }
    }

    @Override
    public void mouseIsClicked(MouseEvent event) {
        MainingLaser laser = new MainingLaser(this, this.getWorld().getPlayer());
        laser.setZ_index(-1);
        laser.setStopRender(false);
        this.getWorld().addEntity(laser);
    }

    @Override
    public void mouseIsRELEASED(MouseEvent event) {
        ArrayList<Entity> entTemp = (ArrayList<Entity>)this.getWorld().getEntities().clone();
        for (Entity current : entTemp) {
            if(current.getClass().equals(MainingLaser.class)){
                current.setDelete(true);
            }
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


                ArrayList<Entity> tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();
                for (Entity e1: tempEnt) {
                    if (e1.isDelete()) this.getWorld().getEntities().remove(e1);
                }

                tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();

                for (Entity e1: tempEnt) {
                    if (e1.isCanCollide()){
                        for (Entity e2 : tempEnt) {
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
