package net.gat3way.achilles.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final Logger LOG = LogManager.getLogger(App.class);

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        setScene(new Scene(loadFXML("frontend/startup/login"), 640, 480));
        stage.setScene(scene);
        stage.show();
    }

    public static void setScene(Scene scene) {
        App.scene = scene;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        LOG.trace("changed root scene to {}", fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void run(String[] args) {
        launch();
    }

}