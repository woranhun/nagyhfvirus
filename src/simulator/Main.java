package simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("simulationEditor.fxml"));
        Scene main = new Scene(root);
        primaryStage.setTitle("Simulation Editor");
        primaryStage.setScene(main);
        primaryStage.setMinWidth(800.0);
        primaryStage.setMinHeight(400.0);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
