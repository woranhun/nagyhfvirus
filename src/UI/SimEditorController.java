package UI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulator.SimulationTemplate;
import simulatorComponents.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SimEditorController implements Initializable {
    @FXML
    private Canvas img;

    @FXML
    private Slider infSlider;
    @FXML
    private TextField infField;

    @FXML
    private Slider mortSlider;
    @FXML
    private TextField mortField;

    @FXML
    private Slider healSlider;
    @FXML
    private TextField healField;

    @FXML
    private Slider speedSlider;
    @FXML
    private TextField speedField;

    @FXML
    private Pane pane;


    private SimulationTemplate simulationTemplate;

    private final Stage stage;

    private simulatorComponents.dotTypes selectedType = simulatorComponents.dotTypes.None;


    public SimEditorController(Stage st) {
        stage = st;
        simulationTemplate = new SimulationTemplate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infSlider.setMin(0);
        infSlider.setMax(1);
        infSlider.setShowTickLabels(true);
        infSlider.setShowTickMarks(true);
        infField.setEditable(false);

        mortSlider.setMin(0);
        mortSlider.setMax(1);
        mortSlider.setShowTickLabels(true);
        mortSlider.setShowTickMarks(true);
        mortField.setEditable(false);

        healSlider.setMin(0);
        healSlider.setMax(1);
        healSlider.setShowTickLabels(true);
        healSlider.setShowTickMarks(true);
        healField.setEditable(false);

        speedSlider.setMin(-8);
        speedSlider.setMax(8);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setValue(0);
        speedField.setEditable(false);

        img.widthProperty().bind(pane.widthProperty());
        img.heightProperty().bind(pane.heightProperty());
        img.widthProperty().addListener(observable -> redraw());
        img.heightProperty().addListener(observable -> redraw());
    }

    private void redraw() {
        simulationTemplate.deleteDotsFromOutOfWindow(img);
        simulationTemplate.refresh(img);
    }

    @FXML
    private void infSliderChanged() {
        double val = infSlider.getValue();
        simulationTemplate.setInfection(val);
        infField.setText(String.valueOf(val));
    }

    @FXML
    private void mortalitySliderChanged() {
        double val = mortSlider.getValue();
        simulationTemplate.setMortality(val);
        mortField.setText(String.valueOf(val));
    }

    @FXML
    private void healSliderChanged() {
        double val = healSlider.getValue();
        simulationTemplate.setHealChance(val);
        healField.setText(String.valueOf(val));
    }

    @FXML
    private void speedSliderChanged() {
        //ToDo make val=0
        double val = Math.pow(2, Math.floor(speedSlider.getValue()));
        simulationTemplate.setSpeed(val);
        speedField.setText(String.valueOf(val));
        //System.out.println(Math.pow(2, Math.floor(speedSlider.getValue())));
    }

    @FXML
    private void clearCanvas() {
        simulationTemplate = new SimulationTemplate();
        simulationTemplate.refresh(img);
    }

    @FXML
    private void drawCanvas(MouseEvent event) {
        simulationTemplate.createDot(selectedType, event.getX(), event.getY(), 5.0);
        simulationTemplate.refresh(img);
    }

    @FXML
    private void setDead() {
        selectedType = dotTypes.Dead;
    }

    @FXML
    private void setInfecious() {
        selectedType = dotTypes.Infectious;
    }

    @FXML
    private void setHealty() {
        selectedType = dotTypes.Healthy;
    }

    @FXML
    private void setNeutral() {
        selectedType = dotTypes.Neutral;
    }

    @FXML
    private void saveFile() throws IOException {
        FileChooser fc = new FileChooser();

        File file = fc.showSaveDialog(new Stage());
        if (file != null) {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(simulationTemplate);
            fos.flush();
            fos.close();
            System.out.println("Simulation is saved to " + file.toString() + " .");
        }
    }

    @FXML
    private void openFile(Event event) throws IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(new Stage());
        if (file != null) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            simulationTemplate = (SimulationTemplate) ois.readObject();
            fis.close();
        }
        simulationTemplate.refresh(img);

        infSlider.setValue(simulationTemplate.getInfChance());
        infField.setText(String.valueOf(simulationTemplate.getInfChance()));

        mortSlider.setValue(simulationTemplate.getMortChance());
        mortField.setText(String.valueOf(simulationTemplate.getMortChance()));

        healSlider.setValue(simulationTemplate.getHealChance());
        healField.setText(String.valueOf(simulationTemplate.getHealChance()));

        speedSlider.setValue(simulationTemplate.getSpeedOfDot());
        speedField.setText(String.valueOf(simulationTemplate.getSpeedOfDot()));
    }

    @FXML
    private void startSimulationPlayer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationPlayer.fxml"));
        Stage window= new Stage();
        try{
            SimulationTemplate simulationTemplateCopy = new SimulationTemplate((SimulationTemplate) simulationTemplate.clone());
            loader.setControllerFactory(c -> new SimulationPlayerController(window, simulationTemplateCopy));

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }
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


}
