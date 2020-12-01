package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulator.SimulationTemplate;
import simulatorComponents.dotTypes;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * Simulation Editor ablak kontroller osztaja.
 * Feladata, hogy kezelje az ablakkal torteno User interakciokat
 */
public class SimEditorController implements Initializable {
    /**
     * Stage eltarolasa.
     * Konstruktorban kapjuk az ablak letrehozasa soran.
     * Ez alapjan pozicionaljuk a tobbi ablakot.
     */
    private final Stage stage;
    /**
     * Potty alapertelmezett sugara.
     */
    private final double radius = 5.0;
    /**
     * Canvas, a Dot-ok jelennek meg.
     */
    @FXML
    private Canvas img;
    /**
     * Slider, az atfertozes eselyenek beallitasara.
     */
    @FXML
    private Slider infSlider;
    /**
     * TextField, itt jelezzuk vissza az Usernek a beallitott infection erteket.
     */
    @FXML
    private TextField infField;
    /**
     * Slider, a halalozasi esely beallitasara.
     */
    @FXML
    private Slider mortSlider;
    /**
     * TextField, itt jelezzuk vissza az Usernek a beallitott halalozas erteket.
     */
    @FXML
    private TextField mortField;
    /**
     * Slider, a gyugyulasi esely beallitasara.
     */
    @FXML
    private Slider healSlider;
    /**
     * TextField, itt jelezzuk vissza az Usernek a beallitott gyogyulas erteket.
     */
    @FXML
    private TextField healField;
    /**
     * Slider, a Dot sebessegenek beallitasara.
     */
    @FXML
    private Slider speedSlider;
    /**
     * TextField, itt jelezzuk vissza az Usernek a beallitott sebesseg erteket.
     */
    @FXML
    private TextField speedField;
    /**
     * Pane, ebben talalhato a canvas, amire rajzolunk.
     */
    @FXML
    private Pane pane;
    /**
     * TextField, a User itt adja meg a lerakni kivant Dotok, szamat.
     * Csak Integer lehet, kulonben hibauzenet keletkezik
     */
    @FXML
    private TextField manyDotsField;
    /**
     * ComboBox, a User itt valasztja ki a Dot tipusat
     */
    @FXML
    private ComboBox<dotTypes> manyDotsComboBox;
    /**
     * SimulationTemplate-et tarolunk, az Editor ezt modositja.
     * Ez tarolja a szimulaciohoz elinditasahoz szukseges adatokat.
     */
    private SimulationTemplate simulationTemplate;
    /**
     * Gomb altal kivalasztott Dot tipusanak taroloja.
     */
    private simulatorComponents.dotTypes selectedType = simulatorComponents.dotTypes.None;

    /**
     * A kontroller konstruktora.
     * Letrehozza a kontrollert, beallitja a stage-et es a simulationTemplate-et
     *
     * @param st Stage, amit a Main-ben hozunk letre.
     */
    public SimEditorController(Stage st) {
        stage = st;
        simulationTemplate = new SimulationTemplate();
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

        ArrayList<dotTypes> vals = new ArrayList<>();
        for (dotTypes dt : dotTypes.values()) {
            if (dt != dotTypes.None) vals.add(dt);
        }
        manyDotsComboBox.getItems().setAll(vals);
        manyDotsComboBox.setValue(dotTypes.Neutral);
    }

    /**
     * Feladata az ablak ujrarajzolasa.
     */
    private void redraw() {
        simulationTemplate.refresh(img);
    }

    /**
     * Tobb potty veletlenszeru elhelyezeset kezelo gomb megnyomasa eseten hivodik.
     * Feladata a TextField Integerre alakitasa, ha ez nem megoldhato hibauzenetet kuld.
     * Ha a kapott szam Integerre alakithato, a megadott tipus alapjan meghivja n db alkalommal a Dot letrehozo fuggvenyt.
     */

    @FXML
    private void addManyDotsPressed() {
        double radius = 5;
        if (!manyDotsField.getText().isEmpty()) {
            try {
                int n = Integer.parseInt(manyDotsField.getText());
                Random random = new Random();
                for (int i = 0; i < n; ++i) {
                    simulationTemplate.createDot(
                            manyDotsComboBox.getValue(),
                            random.nextInt((int) Math.floor(img.getWidth())),
                            random.nextInt((int) Math.floor(img.getHeight())),
                            radius);
                }
                simulationTemplate.refresh(img);
            } catch (NumberFormatException e) {
                System.out.println("ComboBox input is not an integer!");
            }
        }

    }

    /**
     * Fertozesi esely beallito csuszka valtozasakor hivodik.
     * Feladata, hogy ezt az erteket tovabbitsa a simulationTemplate-nek, es kiirja ezt a megfelelo TextFieldbe.
     */

    @FXML
    private void infSliderChanged() {
        double val = infSlider.getValue();
        simulationTemplate.setInfection(val);
        infField.setText(String.valueOf(val));
    }

    /**
     * Halalozasi esely beallito csuszka valtozasakor hivodik.
     * Feladata, hogy ezt az erteket tovabbitsa a simulationTemplate-nek, es kiirja ezt a megfelelo TextFieldbe.
     */

    @FXML
    private void mortalitySliderChanged() {
        double val = mortSlider.getValue();
        simulationTemplate.setMortality(val);
        mortField.setText(String.valueOf(val));
    }

    /**
     * Gyogyulasi esely beallito csuszka valtozasakor hivodik.
     * Feladata, hogy ezt az erteket tovabbitsa a simulationTemplate-nek, es kiirja ezt a megfelelo TextFieldbe.
     */
    @FXML
    private void healSliderChanged() {
        double val = healSlider.getValue();
        simulationTemplate.setHealChance(val);
        healField.setText(String.valueOf(val));
    }


    /**
     * Sebesseg beallito csuszka valtozasakor hivodik.
     * Feladata, hogy ezt az erteket tovabbitsa a simulationTemplate-nek, es kiirja ezt a megfelelo TextFieldbe.
     */
    @FXML
    private void speedSliderChanged() {
        double val = Math.pow(2, Math.floor(speedSlider.getValue()));
        simulationTemplate.setSpeed(val);
        speedField.setText(String.valueOf(val));
    }


    /**
     * Canvas letorleset vegzi.
     * uj SimulationTemplate-et hoz letre.
     */
    @FXML
    private void clearCanvas() {
        simulationTemplate = new SimulationTemplate();
        healSliderChanged();
        infSliderChanged();
        mortalitySliderChanged();
        speedSliderChanged();
        simulationTemplate.refresh(img);
    }

    /**
     * Eger kattintaskor meghivja a Dot letehoz fuggvenyt, es frissiti a Canvast.
     *
     * @param event A kapott MouseEvent
     */
    @FXML
    private void createDotOnMousePosition(MouseEvent event) {
        simulationTemplate.createDot(selectedType, event.getX(), event.getY(), radius);
        simulationTemplate.refresh(img);
    }

    /**
     * Beallitja az eger altal lehelyezendo Dot tipusat halottra.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToDead() {
        selectedType = dotTypes.Dead;
    }

    /**
     * Beallitja az eger altal lehelyezendo Dot tipusat fertozore.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToInfectious() {
        selectedType = dotTypes.Infectious;
    }

    /**
     * Beallitja az eger altal lehelyezendo Dot tipusat egeszsegesre.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToHealthy() {
        selectedType = dotTypes.Healthy;
    }

    /**
     * Beallitja az eger altal lehelyezendo Dot tipusat kozombosre.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToNeutral() {
        selectedType = dotTypes.Neutral;
    }

    /**
     * Elmenti a simulationTemplate-et szerializalas segitsegevel fajlba.
     * ertesiti a User-t, ha sikeres.
     * ertesiti a felhasznalot, ha a fajl null vagy nem letezik.
     */

    @FXML
    private void serializeSimulationTemplate() {
        try {
            FileChooser fc = new FileChooser();

            File file = fc.showSaveDialog(new Stage());
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(simulationTemplate);
                fos.flush();
                fos.close();
                System.out.println("Simulation is saved to " + file.toString() + " .");
            } else {
                System.out.println("File is null!");
            }
        } catch (IOException e) {
            System.out.println("File not exists!");
        }
    }

    /**
     * Meghivja a simulationTemplate-et deszerializalo fuggvenyt.
     * SimulationTemplate-et beallitja a kapott ertekre.
     * A csuszkakat es a hozzajuk tartozo TextField-et beallitja a megfelelo ertekre.
     * Ha null a kapott template a fuggveny visszater. A hibajelzes mar az openSimulationTemplate()-ben megtortent.
     */


    @FXML
    private void openSerializedSimulationTemplate() {
        SimulationTemplate tmp = openSimulationTemplate();
        if (tmp != null) {
            simulationTemplate = tmp;
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

    /**
     * Deszerializalja a megadott simulationTemplate-et.
     *
     * @return SimulationTemplate visszadja egy deszerializalt Template-et, vagy nullt-t ha nem sikerult a folyamat.
     */

    private SimulationTemplate openSimulationTemplate() {
        SimulationTemplate st = null;
        try {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(new Stage());
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                st = (SimulationTemplate) ois.readObject();
                fis.close();
            }
        } catch (IOException e) {
            System.out.println("File error!");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong File!");
        }
        return st;
    }

    /**
     * Letrehozza es elinditja a SimulationPlayer ablakot.
     *
     * @throws IOException Kivetelt dob, ha az fxml nem letezik.
     */

    @FXML
    private void startSimulationPlayer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationPlayer.fxml"));
        Stage window = new Stage();
        try {
            SimulationTemplate simulationTemplateCopy = new SimulationTemplate((SimulationTemplate) simulationTemplate.clone());
            loader.setControllerFactory(c -> new SimulationPlayerController(window, simulationTemplateCopy));

        } catch (CloneNotSupportedException e) {
            System.out.println("File error!");
            return;
        }
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
     * Elinditja a SimulationPlayer-t kozvetlenul fajlbol.
     *
     * @throws IOException Kivetelt dob, ha az fxml nem letezik.
     */

    @FXML
    private void startSimulationPlayerFromFile() throws IOException {
        SimulationTemplate simulationTemplateCopy = openSimulationTemplate();
        if(simulationTemplateCopy==null){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationPlayer.fxml"));
        Stage window = new Stage();
        loader.setControllerFactory(c -> new SimulationPlayerController(window, simulationTemplateCopy));

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
}
