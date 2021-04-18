package App;

import Viewer.Renderer;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Renderer renderer = new Renderer(stage);
    }

}