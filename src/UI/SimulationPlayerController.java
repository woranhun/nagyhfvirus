package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simulator.SimulationStatisticsStore;
import simulator.SimulationTemplate;
import simulator.SimulationPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulationPlayerController implements Initializable {
    Stage stage;
    SimulationPlayer simulationPlayer;
    SimulationTemplate simulationTemplate;
    SimulationStatisticsStore sss;

    @FXML
    private Canvas img;
    @FXML
    private Pane pane;

    @FXML
    private Button playPause;
    @FXML
    private Button step;

    private int cnt=0;

    SimulationPlayerController(Stage st, SimulationTemplate sim) {
        stage = st;
        simulationTemplate =sim;
        stage.setOnCloseRequest(windowEvent -> {
            simulationPlayer.exit();
            stage.close();
            sss.clearAll();
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sss = new SimulationStatisticsStore();
        simulationPlayer =new SimulationPlayer(simulationTemplate,img,sss);
        img.widthProperty().bind(pane.widthProperty());
        img.heightProperty().bind(pane.heightProperty());
        img.widthProperty().addListener(observable -> redraw());
        img.heightProperty().addListener(observable -> redraw());
    }
    private void redraw() {
        if(cnt>=2){
            simulationPlayer.moveDotsFromOutOfWindow(img);
            simulationPlayer.refresh(img);
        }
        cnt++;
    }
    @FXML
    public void playAndPausePressed(){
        simulationPlayer.playAndPause();
    }
    @FXML
    public void stepPressed(){
        simulationPlayer.forwardOneStep();
    }
    @FXML
    public void statisticsPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationStatistics.fxml"));
        Stage window= new Stage();
        loader.setControllerFactory(c -> new SimStatisticsController(window, sss));
        Parent main = loader.load();
        Scene mainScene = new Scene(main);
        window.setTitle("Simulation Player");
        window.setScene(mainScene);
        window.setMinWidth(430.0);
        window.setMinHeight(104.0);
        window.setX(stage.getX()+20);
        window.setY(stage.getY()+20);
        window.show();
    }
    @FXML
    public void speedUpPressed(){
        simulationPlayer.speedUp();
    }
    @FXML
    public void speedDownPressed(){
        simulationPlayer.speedDown();
    }
}
