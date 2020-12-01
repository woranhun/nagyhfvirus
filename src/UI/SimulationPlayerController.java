package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simulator.SimulationPlayer;
import simulator.SimulationStatisticsStore;
import simulator.SimulationTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * SimulationPlayer ablak kontroller osztája.
 * Feladata, hogy kezelje az ablakkal történő User interakciókat
 */
public class SimulationPlayerController implements Initializable {
    /**
     * Stage eltárolása.
     * Konstruktorban kapjuk az ablak létrehozása során.
     * Ez alapján pozícionáljuk a többi ablakot.
     */
    Stage stage;
    /**
     * simulationPlayer-t tárol
     */
    SimulationPlayer simulationPlayer;
    /**
     * Futatandó szimuláció adatait tárolja
     */
    SimulationTemplate simulationTemplate;
    /**
     * Statisztika puffer tára
     */
    SimulationStatisticsStore sss;
    /**
     * Canvas amire rajzolunk
     */
    @FXML
    private Canvas img;
    /**
     * Pane, ebben található a canvas, amire rajzolunk.
     */
    @FXML
    private Pane pane;
    /**
     * Hányszor hívtuk meg a redraw függvényt.
     * reDraw működéséhez szükséges
     */
    private int reDrawCallCnt = 0;

    /**
     * SimulationPlayerController konstruktora
     *
     * @param st  A kapott Stage
     * @param sim A kapott SimulationTemplate
     */
    SimulationPlayerController(Stage st, SimulationTemplate sim) {
        stage = st;
        simulationTemplate = sim;
        stage.setOnCloseRequest(windowEvent -> {
            simulationPlayer.exit();
            stage.close();
            sss.clearAll();
        });
    }

    /**
     * Az ablak inicializálója.
     * Feladata az ablakon található elemek értékeinek beállítása.
     *
     * @param url            JavaFX használja relatív útvonal meghatározása a root objectnek
     * @param resourceBundle Azok a források, amik a root object helyének meghatározásához szükségesek
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sss = new SimulationStatisticsStore();
        simulationPlayer = new SimulationPlayer(simulationTemplate, img, sss);
        img.widthProperty().bind(pane.widthProperty());
        img.heightProperty().bind(pane.heightProperty());
        img.widthProperty().addListener(observable -> redraw());
        img.heightProperty().addListener(observable -> redraw());
    }

    /**
     * Az ablak újrarajzolása
     */
    private void redraw() {
        // We need this because of the 2 addListeners
        if (reDrawCallCnt >= 2) {
            simulationPlayer.moveDotsFromOutOfWindow(img);
            simulationPlayer.refresh(img);
        }
        reDrawCallCnt++;
    }

    /**
     * playAndPause gomb megnyomásának kezelése
     */
    @FXML
    public void playAndPausePressed() {
        simulationPlayer.changePlayAndPause();
    }

    /**
     * Step gomb megnyomásának kezelése
     */
    @FXML
    public void stepPressed() {
        simulationPlayer.forwardOneStep();
    }

    /**
     * Statistics gomb megnyomásának kezelése
     *
     * @throws IOException kivételt dobhat, de ha az fxml fájl jó helyen van nem fog
     */
    @FXML
    public void statisticsPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationStatistics.fxml"));
        Stage window = new Stage();
        loader.setControllerFactory(c -> new SimStatisticsController(window, sss));
        Parent main = loader.load();
        Scene mainScene = new Scene(main);
        window.setTitle("Simulation Player");
        window.setScene(mainScene);
        window.setMinWidth(430.0);
        window.setMinHeight(104.0);
        window.setX(stage.getX() + 20);
        window.setY(stage.getY() + 20);
        window.show();
    }

    /**
     * speedUp gomb megnyomásnak kezelése
     */
    @FXML
    public void speedUpPressed() {
        simulationPlayer.speedUp();
    }

    /**
     * speedDown gomb megnyomásnak kezelése
     */
    @FXML
    public void speedDownPressed() {
        simulationPlayer.speedDown();
    }
}
