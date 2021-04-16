package Viewer;

import GameEngine.Frame;
import GameEngine.Frames.StartMenu;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.time.LocalTime;

public class Renderer implements Runnable{

    Stage stage;

    Frame frame;

    boolean continueRendering = true;

    int currentFramerate = 0;

    public Renderer(Stage stage) {
        this.stage = stage;

        this.changeFrame(new StartMenu(this));

        var scene = new Scene(new FlowPane(),640, 480);
        this.stage.setScene(scene);

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

    @Override
    public void run() {
        int framesInTheLastSecond = 0;
        long now;
        long timeOfLastCount = 0L;
        while (continueRendering) {
            // render background

            // render game

            // render ui

            framesInTheLastSecond++;
            now = System.currentTimeMillis();
            if (now - (1000) > timeOfLastCount) {
                this.currentFramerate = (int)((framesInTheLastSecond*1000)/(now-timeOfLastCount));
                framesInTheLastSecond = 0;
                timeOfLastCount = now;
            }
            this.stage.setTitle(String.format("%s | FPS: %s", this.frame.getWindowTitle(), this.currentFramerate));
        }
    }
}
