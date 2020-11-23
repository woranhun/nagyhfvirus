package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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


    public static void main(String[] args) {
        launch(args);
    }
}
