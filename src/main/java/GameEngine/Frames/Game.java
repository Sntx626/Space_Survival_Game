package GameEngine.Frames;

import GameEngine.Background.Background;
import GameEngine.Frame;
import GameEngine.UI.UI;
import GameEngine.World.Entitys.Astroid;
import GameEngine.World.Entitys.Player;
import GameEngine.World.World;
import Viewer.Renderer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game extends Frame implements Runnable{

    Player p;

    boolean pw = false, ps = false, pa = false, pd = false;

    public Game(Renderer renderer) {
        super(renderer);
        // generate ui, world & background -> pass to frame
        setUi(new UI(this));
        setWorld(new World(this));
        setBackground(new Background(this));



        p = new Player();
        p.setZ_index(1);
        p.setX(0);
        p.setY(0);
        p.setH(40);
        p.setW(30);

        Astroid a = new Astroid();
        a.setZ_index(-1);
        a.setX(-300);
        a.setY(-300);
        a.setH(100);
        a.setW(100);
        this.getWorld().addEntity(a);
        this.getWorld().addEntity(p);
        System.out.println(this.getWorld().getEntities().get(0).getX());
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
                if (pd) {
                   accX += 1;
                }
                if (pa) {
                    accX -= 1;
                }
                if (pw) {
                    accY -= 1;
                }
                if (ps) {
                    accY += 1;
                }
                this.p.addForce(accX, accY);


                this.getWorld().run();
                this.getWorld().getCamera().setX(p.getX());
                this.getWorld().getCamera().setY(p.getY());


                Thread.sleep(1);
            }
        }catch (InterruptedException e) {

        }
    }
}
