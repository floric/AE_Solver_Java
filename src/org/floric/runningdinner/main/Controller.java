package org.floric.runningdinner.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // teams

    @FXML
    private Button applyTeamsButton;

    @FXML
    private Spinner<Integer> teamCountSpinner;

    // coordinates

    @FXML
    private Button generateCoordinatesButton;

    @FXML
    private Canvas coordinatesCanvas;

    @FXML
    private TextField seedTextField;

    @FXML
    private TextField factorTextField;

    @FXML
    protected void applyTeamsClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3,99,9,3));
    }
}

