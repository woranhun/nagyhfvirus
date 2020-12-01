package UI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import simulator.SimulationStatisticsStore;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Simulation Statistics Kontroller osztaja
 * Feladata, hogy kezelje az ablakkal torteno User interakciokat
 */
public class SimStatisticsController implements Initializable {

    /**
     * A kirajzolando grafikus
     */
    @FXML
    private StackedAreaChart<Number,Number> chart;
    /**
     * Kozos store a simulationPlayerrel
     */
    SimulationStatisticsStore sss;

    /**
     * lakossagi adatokat tarolo XYChart Series
     */
    private final XYChart.Series<Number,Number> population = new XYChart.Series<>();
    /**
     * halalozasi adatokat tarolo XYChart Series
     */
    private final XYChart.Series<Number,Number> deaths = new XYChart.Series<>();
    /**
     * fertozesi adatokat tarolo XYChart Series
     */
    private final XYChart.Series<Number,Number> infections = new XYChart.Series<>();
    /**
     * gyogyulasi adatokat tarolo XYChart Series
     */
    private final XYChart.Series<Number,Number> heals = new XYChart.Series<>();

    /**
     * SimStatisticsController konstrukturja
     * @param st A kapott Stage
     * @param sss A kapott SimulationStatisticsStore
     */
    public SimStatisticsController(Stage st,SimulationStatisticsStore sss){
        this.sss=sss;
        st.setOnCloseRequest(windowEvent -> {
            population.getData().clear();
            deaths.getData().clear();
            infections.getData().clear();
            heals.getData().clear();
            chart.getData().clear();
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

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), actionEvent -> updateChart()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        chart.setTitle("Virus");
        population.setName("Current population");
        deaths.setName("Current deaths");
        infections.setName("Current infections");
        heals.setName("Current heals");
        chart.getYAxis().setAnimated(true);
        chart.getXAxis().setAnimated(true);
        chart.setCreateSymbols(true);
        chart.setAnimated(true);
        chart.getYAxis().setAutoRanging(true);
        chart.getXAxis().setAutoRanging(true);

        chart.getData().add(this.population);
        chart.getData().add(this.infections);
        chart.getData().add(this.heals);
        chart.getData().add(this.deaths);

    }

    /**
     * Frissiti a grafikonon megjeleno adatokat
     */
    public void updateChart(){
        for(Pair<Number,Number> p : sss.getPopulationQueue()){
            this.population.getData().add(new XYChart.Data<>(p.getKey(),p.getValue()));
        }
        for(Pair<Number,Number> p : sss.getDeathsQueue()){
            this.deaths.getData().add(new XYChart.Data<>(p.getKey(),p.getValue()));
        }
        for(Pair<Number,Number> p : sss.getInfectionsQueue()){
            this.infections.getData().add(new XYChart.Data<>(p.getKey(),p.getValue()));
        }
        for(Pair<Number,Number> p : sss.getHealsQueue()){
            this.heals.getData().add(new XYChart.Data<>(p.getKey(),p.getValue()));
        }
        sss.clearPopulationQueue();
        sss.clearDeathsQueue();
        sss.clearInfectionsQueue();
        sss.clearHealsQueue();
    }
}
