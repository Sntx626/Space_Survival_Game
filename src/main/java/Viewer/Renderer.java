package Viewer;

import GameEngine.Frame;
import GameEngine.StartMenu;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Renderer {

    Stage stage;

    Frame frame;

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
}
