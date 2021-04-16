package App;

import Viewer.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Renderer renderer = new Renderer(stage);
        renderer.run();
    }

    public static void main(String[] args) {
        launch();
    }

}