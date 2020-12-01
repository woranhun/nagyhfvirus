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
 * SimulationPlayer ablak kontroller osztaja.
 * Feladata, hogy kezelje az ablakkal torteno User interakciokat
 */
public class SimulationPlayerController implements Initializable {
    /**
     * Stage eltarolasa.
     * Konstruktorban kapjuk az ablak letrehozasa soran.
     * Ez alapjan pozicionaljuk a tobbi ablakot.
     */
    Stage stage;
    /**
     * simulationPlayer-t tarol
     */
    SimulationPlayer simulationPlayer;
    /**
     * Futatando szimulacio adatait tarolja
     */
    SimulationTemplate simulationTemplate;
    /**
     * Statisztika puffer tara
     */
    SimulationStatisticsStore sss;
    /**
     * Canvas amire rajzolunk
     */
    @FXML
    private Canvas img;
    /**
     * Pane, ebben talalhato a canvas, amire rajzolunk.
     */
    @FXML
    private Pane pane;
    /**
     * Hanyszor hivtuk meg a redraw fuggvenyt.
     * reDraw mukodesehez szukseges
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
     * Az ablak inicializaloja.
     * Feladata az ablakon talalhato elemek ertekeinek beallitasa.
     *
     * @param url            JavaFX hasznalja relativ utvonal meghatarozasa a root objectnek
     * @param resourceBundle Azok a forrasok, amik a root object helyenek meghatarozasahoz szuksegesek
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
     * Az ablak ujrarajzolasa
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
     * playAndPause gomb megnyomasanak kezelese
     */
    @FXML
    public void playAndPausePressed() {
        simulationPlayer.changePlayAndPause();
    }

    /**
     * Step gomb megnyomasanak kezelese
     */
    @FXML
    public void stepPressed() {
        simulationPlayer.forwardOneStep();
    }

    /**
     * Statistics gomb megnyomasanak kezelese
     *
     * @throws IOException kivetelt dobhat, de ha az fxml fajl jo helyen van nem fog
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
     * speedUp gomb megnyomasnak kezelese
     */
    @FXML
    public void speedUpPressed() {
        simulationPlayer.speedUp();
    }

    /**
     * speedDown gomb megnyomasnak kezelese
     */
    @FXML
    public void speedDownPressed() {
        simulationPlayer.speedDown();
    }
}
