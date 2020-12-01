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
 * Simulation Editor ablak kontroller osztája.
 * Feladata, hogy kezelje az ablakkal történő User interakciókat
 */
public class SimEditorController implements Initializable {
    /**
     * Stage eltárolása.
     * Konstruktorban kapjuk az ablak létrehozása során.
     * Ez alapján pozícionáljuk a többi ablakot.
     */
    private final Stage stage;
    /**
     * Canvas, a Dot-ok jelennek meg.
     */
    @FXML
    private Canvas img;
    /**
     * Slider, az átfertőzés esélyének beállítására.
     */
    @FXML
    private Slider infSlider;
    /**
     * TextField, itt jelezzük vissza az Usernek a beállított infection értékét.
     */
    @FXML
    private TextField infField;
    /**
     * Slider, a halálozási esély beállítására.
     */
    @FXML
    private Slider mortSlider;
    /**
     * TextField, itt jelezzük vissza az Usernek a beállított halálozás értékét.
     */
    @FXML
    private TextField mortField;
    /**
     * Slider, a gyúgyulási esély beállítására.
     */
    @FXML
    private Slider healSlider;
    /**
     * TextField, itt jelezzük vissza az Usernek a beállított gyógyulás értékét.
     */
    @FXML
    private TextField healField;
    /**
     * Slider, a Dot sebességénék beállítására.
     */
    @FXML
    private Slider speedSlider;
    /**
     * TextField, itt jelezzük vissza az Usernek a beállított sebesség értékét.
     */
    @FXML
    private TextField speedField;
    /**
     * Pane, ebben található a canvas, amire rajzolunk.
     */
    @FXML
    private Pane pane;
    /**
     * TextField, a User itt adja meg a lerakni kívánt Dotok, számát.
     * Csak Integer lehet, különben hibaüzenet keletkezik
     */
    @FXML
    private TextField manyDotsField;
    /**
     * ComboBox, a User itt választja ki a Dot típusát
     */
    @FXML
    private ComboBox<dotTypes> manyDotsComboBox;
    /**
     * SimulationTemplate-et tárolunk, az Editor ezt módosítja.
     * Ez tárolja a szimulációhoz elindításához szükséges adatokat.
     */
    private SimulationTemplate simulationTemplate;
    /**
     * Gomb által kiválasztott Dot típusának tárolója.
     */
    private simulatorComponents.dotTypes selectedType = simulatorComponents.dotTypes.None;

    /**
     * Pötty alapértelmezett sugara.
     */
    private final double radius = 5.0;

    /**
     * A kontroller konstruktora.
     * Létrehozza a kontrollert, beállítja a stage-et és a simulationTemplate-et
     *
     * @param st Stage, amit a Main-ben hozunk létre.
     */
    public SimEditorController(Stage st) {
        stage = st;
        simulationTemplate = new SimulationTemplate();
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
     * Feladata az ablak újrarajzolása.
     */
    //ToDO tesztelni
    private void redraw() {
        //simulationTemplate.deleteDotsFromOutOfWindow(img);
        simulationTemplate.refresh(img);
    }

    /**
     * Több pötty véletlenszerű elhelyezését kezelő gomb megnyomása esetén hívódik.
     * Feladata a TextField Integerré alakítása, ha ez nem megoldható hibaüzenetet küld.
     * Ha a kapott szám Integerré alakítható, a megadott típus alapján meghívja n db alkalommal a Dot létrehozó függvényt.
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
     * Fertőzési esély beállító csúszka változásakor hívódik.
     * Feladata, hogy ezt az értéket továbbítsa a simulationTemplate-nek, és kiírja ezt a megfelelő TextFieldbe.
     */

    @FXML
    private void infSliderChanged() {
        double val = infSlider.getValue();
        simulationTemplate.setInfection(val);
        infField.setText(String.valueOf(val));
    }

    /**
     * Halálozási esély beállító csúszka változásakor hívódik.
     * Feladata, hogy ezt az értéket továbbítsa a simulationTemplate-nek, és kiírja ezt a megfelelő TextFieldbe.
     */

    @FXML
    private void mortalitySliderChanged() {
        double val = mortSlider.getValue();
        simulationTemplate.setMortality(val);
        mortField.setText(String.valueOf(val));
    }

    /**
     * Gyógyulási esély beállító csúszka változásakor hívódik.
     * Feladata, hogy ezt az értéket továbbítsa a simulationTemplate-nek, és kiírja ezt a megfelelő TextFieldbe.
     */
    @FXML
    private void healSliderChanged() {
        double val = healSlider.getValue();
        simulationTemplate.setHealChance(val);
        healField.setText(String.valueOf(val));
    }

    //ToDo specifikációtol való eltérés
    //ToDo make val=0 ?

    /**
     * Sebesség beállító csúszka változásakor hívódik.
     * Feladata, hogy ezt az értéket továbbítsa a simulationTemplate-nek, és kiírja ezt a megfelelő TextFieldbe.
     */
    @FXML
    private void speedSliderChanged() {
        double val = Math.pow(2, Math.floor(speedSlider.getValue()));
        simulationTemplate.setSpeed(val);
        speedField.setText(String.valueOf(val));
        //System.out.println(Math.pow(2, Math.floor(speedSlider.getValue())));
    }


    //ToDo tesztelni

    /**
     * Canvas letörlését végzi.
     * Új SimulationTemplate-et hoz létre.
     */
    @FXML
    private void clearCanvas() {
        simulationTemplate = new SimulationTemplate();
        healSliderChanged();
        infSliderChanged();
        mortalitySliderChanged();
        speedSliderChanged();
//        simulationTemplate.setHealChance(healSlider.getValue());
//        simulationTemplate.setInfection(infSlider.getValue());
//        simulationTemplate.setMortality(mortSlider.getValue());
//        simulationTemplate.setSpeed(speedSlider.getValue());
        simulationTemplate.refresh(img);
    }

    /**
     * Egér kattintáskor meghívja a Dot létehoz függvényt, és frissíti a Canvast.
     *
     * @param event A kapott MouseEvent
     */
    @FXML
    private void createDotOnMousePosition(MouseEvent event) {
        simulationTemplate.createDot(selectedType, event.getX(), event.getY(), radius);
        simulationTemplate.refresh(img);
    }

    /**
     * Beállítja az egér által lehelyezendő Dot típusát halottra.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToDead() {
        selectedType = dotTypes.Dead;
    }

    /**
     * Beállítja az egér által lehelyezendő Dot típusát fertőzőre.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToInfectious() {
        selectedType = dotTypes.Infectious;
    }

    /**
     * Beállítja az egér által lehelyezendő Dot típusát egészségesre.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToHealthy() {
        selectedType = dotTypes.Healthy;
    }

    /**
     * Beállítja az egér által lehelyezendő Dot típusát közömbösre.
     */

    @FXML
    private void setTypeOfDotOnMousePositionToNeutral() {
        selectedType = dotTypes.Neutral;
    }

    /**
     * Elmenti a simulationTemplate-et szerializálás segítségével fájlba.
     * Értesíti a User-t, ha sikeres.
     * Értesíti a felhasználót, ha a fájl null vagy nem létezik.
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
     * Meghívja a simulationTemplate-et deszerializáló függvényt.
     * SimulationTemplate-et beállítja a kapott értékre.
     * A csúszkákat és a hozzájuk tartozó TextField-et beállítja a megfelelő értékre.
     * Ha null a kapott template a függvény visszatér. A hibajelzés már az openSimulationTemplate()-ben megtörtént.
     */


    @FXML
    private void openSerializedSimulationTemplate() {
        SimulationTemplate tmp = openSimulationTemplate();
        if (tmp != null){
            simulationTemplate=tmp;
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
     * Deszerializálja a megadott simulationTemplate-et.
     *
     * @return SimulationTemplate visszadja egy deszerializált Template-et, vagy nullt-t ha nem sikerült a folyamat.
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
     * Létrehozza és elindítja a SimulationPlayer ablakot.
     *
     * @throws IOException Kivételt dob, ha az fxml nem létezik.
     */

    @FXML
    private void startSimulationPlayer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationPlayer.fxml"));
        Stage window = new Stage();
        try {
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
        window.setX(stage.getX() + 20);
        window.setY(stage.getY() + 20);
        window.show();

    }

    /**
     * Elindítja a SimulationPlayer-t közvetlenül fájlból.
     *
     * @throws IOException Kivételt dob, ha az fxml nem létezik.
     */

    @FXML
    private void startSimulationPlayerFromFile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationPlayer.fxml"));
        Stage window = new Stage();
        SimulationTemplate simulationTemplateCopy = openSimulationTemplate();
        loader.setControllerFactory(c -> new SimulationPlayerController(window, simulationTemplateCopy));

        Parent main = loader.load();        Scene mainScene = new Scene(main);
        window.setTitle("Simulation Player");
        window.setScene(mainScene);
        window.setMinWidth(430.0);
        window.setMinHeight(104.0);
        window.setX(stage.getX() + 20);
        window.setY(stage.getY() + 20);
        window.show();
    }
}
