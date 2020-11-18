package simulator;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SimEditorController implements Initializable {
    @FXML private Canvas img;
    @FXML private Slider speedSlider;
    @FXML private Slider infSlider;
    private GraphicsContext gc;
    private Color col=Color.WHITE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = img.getGraphicsContext2D();
        speedSlider.setMin(-8);
        speedSlider.setMax(8);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setValue(0);
        infSlider.setMin(0);
        infSlider.setMax(1);
        infSlider.setShowTickLabels(true);
        infSlider.setShowTickMarks(true);
    }
    @FXML
    private void speedSliderChanged(){
        System.out.println(Math.pow(2,Math.floor(speedSlider.getValue())));
    }
    @FXML
    private void test(Event event){
        System.out.println("test");
    }
    @FXML
    private void clearCanvas(Event event){
        gc.clearRect(0, 0, img.getWidth(), img.getHeight());
    }
    @FXML
    private void drawCanvas(MouseEvent event){
        gc.setFill(col);
        gc.fillOval(event.getX(), event.getY(), 5,5);
    }
    @FXML
    private void setDead(){
        col=Color.BLACK;
    }
    @FXML
    private void setInfecious(){
        col=Color.RED;
    }
    @FXML
    private void setHealty(){
        col=Color.GREEN;
    }
    @FXML
    private void setNeutral(){
        col=Color.GRAY;
    }


}
