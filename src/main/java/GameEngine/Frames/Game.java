package GameEngine.Frames;

import GameEngine.Background.Background;
import GameEngine.Frame;
import GameEngine.UI.Components.TextBox;
import GameEngine.UI.UI;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Astroid;
import GameEngine.World.Entitys.AstroidPiece;
import GameEngine.World.Entitys.Fog;
import GameEngine.World.Entitys.Player;
import GameEngine.World.Projectiles.MainingLaser;
import GameEngine.World.Projectiles.Rocket;
import GameEngine.World.Vector;
import GameEngine.World.World;
import Viewer.Renderer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Array;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;

public class Game extends Frame implements Runnable{

    Player p;
    Fog f;

    boolean pw = false, ps = false, pa = false, pd = false;

    TextBox rocketCountLabel;

    int maxSpawnX = 0, maxSpawnY = 0;
    int maxAstroids = 100;

    double playerSpeed = 5;
    int maxSpeed = 10;

    public Game(Renderer renderer) {
        super(renderer);
        // generate ui, world & background -> pass to frame
        setUi(new UI(this));
        setWorld(new World(this));
        setBackground(new Background("file:rsc/background_data/Background.jpg", renderer.getcanvas()));

        this.getWorld().setCanZoom(true);


        rocketCountLabel = new TextBox("Rockets: 0", 10, 40);
        this.getUi().addComponent(rocketCountLabel);

        p = new Player(this);
        p.setX(500);
        p.setY(0);
        p.setMaxSpeed(maxSpeed);


        f = new Fog(this);
        f.setH(400);
        f.setW(400);



        this.getWorld().addEntity(p);
        this.getWorld().addEntity(f);
        this.maxSpawnX = this.getWorld().getCamera().getMaxZoomX() + 2000;
        this.maxSpawnY = this.getWorld().getCamera().getMaxZoomY() + 2000;

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
            if(this.getWorld().getPlayer().getMomRockets() > 0) {
                System.out.println("Rocked Fired");
                Rocket rocket = new Rocket(this, this.getWorld().getPlayer());
                double playerAngle = this.getWorld().getPlayer().getLastAngle();
                rocket.addForce(Math.sin(Math.toRadians(playerAngle)) * 5, -Math.cos(Math.toRadians(playerAngle)) * 5);
                rocket.setFriction(0);
                rocket.setZ_index(-1);
                this.getWorld().addEntity(rocket);
                this.getWorld().getPlayer().setMomRockets(this.getWorld().getPlayer().getMomRockets()-1);
            }else{
                System.out.println("Not enough Rockets");
            }
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

    public int getCurrentFramerate() {
        return currentFramerate;
    }

    public void setCurrentFramerate(int currentFramerate) {
        this.currentFramerate = currentFramerate;
    }

    int currentFramerate = 0;
    @Override
    public void run() {
        try {
            int framesInTheLastSecond = 0;
            long now;
            long timeOfLastCount = 0L;

            long startTime = Instant.now().toEpochMilli();
            long timesRun = 0;
            long tickDuration = 5;
            long expectedRunDuration;
            long actualRunDuration;

            while (true) {
                if (timesRun <= Long.MAX_VALUE-1000) {
                    timesRun++;
                } else {
                    timesRun = 0;
                    startTime = Instant.now().toEpochMilli();
                }

                double accX = 0,  accY = 0;
                double responsive = 0.01;
                if (pd) {
                   accX += playerSpeed*responsive;
                }
                if (pa) {
                    accX -= playerSpeed*responsive;
                }
                if (pw) {
                    accY -= playerSpeed*responsive;
                }
                if (ps) {
                    accY += playerSpeed*responsive;
                }
                this.p.addForce(accX, accY);

                this.getWorld().run();
                this.getWorld().getCamera().setX(p.getX());
                this.getWorld().getCamera().setY(p.getY());
                f.setX(p.getX());
                f.setY(p.getY());
                ArrayList<Entity> tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();
                for (Entity e1: tempEnt) {
                    if (e1.getClass() == Astroid.class && e1.getHp() <= 0) {
                        e1.setDelete(true);
                        //GenerateAstroidPiece
                        for (int i = 0; i < 5; i++) {
                            double angle = Math.random() * 360;
                            double length = Math.random() * 3;
                            AstroidPiece piece = new AstroidPiece(this, e1, new Vector(Math.sin(Math.toRadians(angle)) * length, Math.cos(Math.toRadians(angle)) * length));
                            this.getWorld().addEntity(piece);
                        }
                    } else if (e1.getClass() == Astroid.class || e1.getClass() == AstroidPiece.class || e1.getClass() == Rocket.class) {

                        Vector toHealthBarTarget = new Vector(this.p.getX()-e1.getX(), this.p.getY()-e1.getY());
                        if (toHealthBarTarget.Length() > this.maxSpawnX/2) {
                            e1.setDelete(true);
                        }
                    }
                }

                tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();
                for (Entity e1: tempEnt) {
                    if (e1.isDelete())
                    {
                        e1.disableHealthBar();
                        this.getWorld().getEntities().remove(e1);
                    }
                }


                int countAstroids = 0;
                for (Entity e1: tempEnt) {
                    if (e1.getClass() == Astroid.class) {
                        countAstroids++;
                    }
                }
                while (countAstroids < maxAstroids) {
                    double minX = this.p.getX() - this.maxSpawnX/2;
                    double minY = this.p.getY() - this.maxSpawnY/2;
                    double maxX = this.p.getX() + this.maxSpawnX/2;
                    double maxY = this.p.getY() + this.maxSpawnY/2;
                    double fogSize = this.f.getW()/4;
                    double x = 0;
                    double y = 0;
                    double r = 0;
                    boolean found = false;
                    while (!found) {
                        x = Math.random() * (maxX - minX) + minX;
                        y = Math.random() * (maxY - minY) + minY;
                        r = (int)(Math.random() * (300 - 100) + 100);
                        Vector toHealthBarTarget = new Vector(this.p.getX() - x, this.p.getY() - y);
                        while (toHealthBarTarget.Length() < fogSize) {
                            x = Math.random() * (maxX - minX) + minX;
                            y = Math.random() * (maxY - minY) + minY;
                            toHealthBarTarget = new Vector(this.p.getX() - x, this.p.getY() - y);
                        }
                        tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();
                        found = true;
                        for (Entity e: tempEnt) {
                            if (e.getClass() == Astroid.class) {
                                Vector toAstroid = new Vector(x - e.getX(), y - e.getY());
                                if (toAstroid.Length() <= e.getH() + r + 20) {
                                    found = false;
                                    break;
                                }
                            }
                        }
                    }
                    Astroid a3 = new Astroid(this);
                    a3.setX(x);
                    a3.setY(y);
                    a3.setH((int)r);
                    a3.setW((int)r);
                    this.getWorld().addEntity(a3);

                    countAstroids++;
                }



                tempEnt = (ArrayList<Entity>)this.getWorld().getEntities().clone();

                for (Entity e1: tempEnt) {
                    if (e1.isCanCollide()){
                        ArrayList<Entity> isColliding = new ArrayList<Entity>();
                        ArrayList<Vector> collisionVector = new ArrayList<Vector>();
                        for (Entity e2 : tempEnt) {
                            // && e2.getClass() == Astroid.class
                            if (e1 != e2 && e2.isCanCollide()) {
                                if (e1.isColliding(e2)) {
                                    if (e1.getClass() == MainingLaser.class && e2 != p) {
                                        isColliding.add(e2);
                                        collisionVector.add(((MainingLaser)e1).getCollidingPoint(e2.getX(), e2.getY(), e2.getW()));
                                    }
                                    e1.onColliding(e2);
                                }
                            }
                        }
                        if (e1.getClass() == MainingLaser.class && isColliding.size() > 0) {
                            Entity closest = isColliding.get(0);
                            double closestDist = Math.sqrt(Math.pow(collisionVector.get(0).getX() - p.getX(), 2) + Math.pow(collisionVector.get(0).getY() - p.getY(), 2));

                            for (int i = 1; i < isColliding.size(); i++) {

                                double dist = Math.sqrt(Math.pow(collisionVector.get(i).getX() - p.getX(), 2) + Math.pow(collisionVector.get(i).getY() - p.getY(), 2));
                                if (closestDist > dist) {
                                    closest = isColliding.get(i);
                                    closestDist = dist;
                                }
                            }
                            MainingLaser laser = (MainingLaser)e1;
                            laser.setRenderLength(closestDist);
                            closest.setHp(closest.getHp() - 1);
                        } else if (e1.getClass() == MainingLaser.class) {
                            MainingLaser laser = (MainingLaser)e1;
                            laser.setRenderLength(laser.getW());
                        }
                    }
                }
                framesInTheLastSecond++;
                now = System.currentTimeMillis();
                if (now - (1000) > timeOfLastCount) {
                    currentFramerate = (int)((framesInTheLastSecond*1000)/(now-timeOfLastCount));
                    framesInTheLastSecond = 0;
                    timeOfLastCount = now;
                }

                rocketCountLabel.setContent("Rocktes: " + (int)this.p.getMomRockets());

                //execDuration = (long)Math.ceil((Instant.now().getNano()-nano)/1000000);

                expectedRunDuration = timesRun*tickDuration;
                actualRunDuration = Instant.now().toEpochMilli()-startTime;
                if (actualRunDuration < expectedRunDuration) {
                    Thread.sleep(expectedRunDuration-actualRunDuration);
                }

            }
        } catch (InterruptedException e) {

        }
    }
}
