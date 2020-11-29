package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simulator.SimulationTemplate;
import simulator.SimulatorPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationPlayerController implements Initializable {
    Stage stage;
    SimulatorPlayer simulatorPlayer;
    SimulationTemplate simulationTemplate;

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
            simulatorPlayer.exit();
            stage.close();
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simulatorPlayer=new SimulatorPlayer(simulationTemplate,img);
        img.widthProperty().bind(pane.widthProperty());
        img.heightProperty().bind(pane.heightProperty());
        img.widthProperty().addListener(observable -> redraw());
        img.heightProperty().addListener(observable -> redraw());
        img.setOnMouseMoved(mouseEvent -> System.out.println("MouseX:"+ mouseEvent.getX()+" MouseY: "+mouseEvent.getY() + "\n"));
    }
    private void redraw() {
        if(cnt>=2){
            simulatorPlayer.moveDotsFromOutOfWindow(img);
            simulatorPlayer.refresh(img);
        }
        cnt++;
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
