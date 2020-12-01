package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class. Ez a program belépési pontja.
 */
public class Main extends Application {
    /**
     * Ez a program belépési pontja.
     *
     * @param args parancssori argumentumok listája. Nem használom.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Az ablakot elindító függvény.
     *
     * @param primaryStage JavaFX rendszertől kapott primary stage.
     * @throws IOException Akkor dobja, ha a simulationEditor.fxml nem található.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/simulationEditor.fxml"));

        loader.setControllerFactory(c -> new SimEditorController(primaryStage));

        Parent main = loader.load();
        Scene mainScene = new Scene(main);
        primaryStage.setTitle("Simulation Editor");
        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(952.0);
        primaryStage.setMinHeight(400.0);
        primaryStage.show();
    }
}
