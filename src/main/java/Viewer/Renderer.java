package Viewer;

import GameEngine.Frame;
import GameEngine.Frames.Game;
import GameEngine.Frames.StartMenu;
import GameEngine.UI.Component;
import GameEngine.World.Entity;
import GameEngine.World.Entitys.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Renderer{

    Stage stage;
    Canvas canvas;


    Frame frame;

    int mouseX = 0, mouseY = 0;

    boolean continueRendering = true;

    int WIDTH = 16*80, HEIGHT = 9*80;

    int currentFramerate = 0;

    public Renderer(Stage stage) {
        this.stage = stage;

        //this.changeFrame(new StartMenu(this));
        this.changeFrame(new Game(this));

        //ADD CANVAS TO PANE
        var root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        this.canvas = canvas;
        root.getChildren().add(this.canvas);

        var scene = new Scene(root,WIDTH, HEIGHT);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            this.frame.keyIsPressed(key);
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            this.frame.keyIsReleased(key);
        });
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            this.mouseX = (int)mouseEvent.getX();
            this.mouseY = (int)mouseEvent.getY();
        });
        scene.setOnScroll(ScrollEvent -> {
            System.out.println(ScrollEvent.getDeltaY());
            frame.zoom(ScrollEvent.getDeltaY());
        });
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            WIDTH = newVal.intValue();
            canvas.setWidth(WIDTH);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            HEIGHT = newVal.intValue();
            canvas.setHeight(HEIGHT);
        });
        stage.minHeightProperty().bind(stage.widthProperty().multiply(9.0/16.0));
        stage.maxHeightProperty().bind(stage.widthProperty().multiply(9.0/16.0));

        this.stage.setScene(scene);

        run();

        this.stage.show();

    }

    public Renderer changeFrame(Frame frame) {
        this.frame = frame;
        this.frame.init();
        return this;
    }

    public void updateWindowTitle() {
        this.stage.setTitle(this.frame.getWindowTitle());
    }

    public void updateWindowIcon() {
        this.stage.getIcons().add(new Image("file:" + this.frame.getWindowTitle()));
    }

    public void run() {
        int framesInTheLastSecond = 0;
        long now;
        long timeOfLastCount = 0L;
        final long startNanoTime = System.nanoTime();
        new AnimationTimer(){
            int framesInTheLastSecond = 0;
            long now;
            long timeOfLastCount = 0L;
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                // background

                canvas.getGraphicsContext2D().setFill(Color.WHITE);
                canvas.getGraphicsContext2D().fillRect(0, 0, WIDTH, HEIGHT);

                // middleground

                Camera c = frame.getWorld().getCamera();

                ArrayList<Entity> entities = (ArrayList<Entity>)frame.getWorld().getEntities().clone();
                Collections.sort(entities);

                for (Entity e : entities) {
                    if (Math.abs(c.getX() - e.getX()) <= (c.getW() / 2) + e.getW() && Math.abs(c.getY() - e.getY()) <= e.getH() + (c.getH() / 2)) {
                        e.render(canvas.getGraphicsContext2D(), (int) c.getX(), (int) c.getY(), c.getW(), c.getH(), WIDTH, HEIGHT-40, mouseX, mouseY);
                    }
                }

                // foreground

                for (Component component: frame.getUi().getComponents()) {
                    // render component
                    component.render(canvas.getGraphicsContext2D(), (int) c.getX(), (int) c.getY(), c.getW(), c.getH(), WIDTH, HEIGHT);
                }

                framesInTheLastSecond++;
                now = System.currentTimeMillis();
                if (now - (1000) > timeOfLastCount) {
                    currentFramerate = (int)((framesInTheLastSecond*1000)/(now-timeOfLastCount));
                    framesInTheLastSecond = 0;
                    timeOfLastCount = now;
                }
                stage.setTitle(String.format("%s | FPS: %s", frame.getWindowTitle(), currentFramerate));
            }
        }.start();
    }
}
