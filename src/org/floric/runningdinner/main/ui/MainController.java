package org.floric.runningdinner.main.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.floric.runningdinner.main.core.Core;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane canvasPane;

    @FXML
    private Button applyTeamsButton;
    @FXML
    private Button generateCoordinatesButton;

    @FXML
    private Spinner<Integer> teamCountSpinner;
    @FXML
    private Spinner<Integer> randomSeedSpinner;

    @FXML
    private Canvas coordinatesCanvas;
    private GraphicsContext gc;

    @FXML
    private TextField seedTextField;


    @FXML
    protected void applyNewTeamsClicked(MouseEvent event) {
        Core c = Core.getInstance();
        c.getDataGenerator().changeSeed(randomSeedSpinner.getValue());
        c.getDataGenerator().setTeamCount(teamCountSpinner.getValue());
        c.getDataGenerator().recalculateData();
    }

    private void initSpinner(Spinner<Integer> spinner, int min, int max, int def, int step) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, def, step));
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String currentInput = spinner.editorProperty().getValue().getText();
            try {
                int currentValue = Integer.parseInt(currentInput);

                if (currentValue > max) {
                    currentValue = max;
                } else if (currentValue < min) {
                    currentValue = min;
                }

                spinner.editorProperty().getValue().setText(String.valueOf(currentValue - (currentValue % step)));
            } catch (NumberFormatException ex) {
                spinner.editorProperty().getValue().setText(String.valueOf(def));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSpinner(teamCountSpinner, Core.TEAMS_MIN, Core.TEAMS_MAX, Core.TEAMS_DEFAULT, 3);
        initSpinner(randomSeedSpinner, Core.SEED_MIN, Core.SEED_MAX, Core.SEED_DEFAULT, 1);

        gc = coordinatesCanvas.getGraphicsContext2D();

        addListeners();

    }

    private void addListeners() {
        // force resize for child panels
        mainPane.widthProperty().addListener((observable1, oldValue1, newValue1) -> {
            canvasPane.autosize();
        });
        mainPane.heightProperty().addListener((observable1, oldValue1, newValue1) -> {
            canvasPane.autosize();
        });

        // resize and repaint canvas after parent panel size changes
        canvasPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            coordinatesCanvas.setHeight(newValue.doubleValue());
            repaintCanvas();
        });
        canvasPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            coordinatesCanvas.setWidth(newValue.doubleValue());
            repaintCanvas();
        });
    }

    private void repaintCanvas() {

    }
}

