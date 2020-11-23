package UI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simulator.SimulatorPlayer;
import simulator.SimulationTemplate;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationPlayerController implements Initializable {
    Stage stage;
    SimulatorPlayer simulatorPlayer;
    SimulationTemplate simulationTemplate;

    @FXML
    private Canvas img;
    @FXML
    private Button playPause;
    @FXML
    private Button step;

    SimulationPlayerController(Stage st, SimulationTemplate sim) {
        stage = st;
        simulationTemplate =sim;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                simulatorPlayer.exit();
                stage.close();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simulatorPlayer=new SimulatorPlayer(simulationTemplate,img);
    }
    @FXML
    public void playAndPausePressed(){
        simulatorPlayer.playAndPause();
    }
    @FXML
    public void stepPressed(){
        simulatorPlayer.forwardOneStep();
    }

}