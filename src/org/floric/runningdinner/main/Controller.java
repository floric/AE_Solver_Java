package org.floric.runningdinner.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class Controller {

    // teams

    @FXML
    private Button applyTeamsButton;

    @FXML
    private TextField teamsCountTextField;

    // coordinates

    @FXML
    private Button generateCoordinatesButton;

    @FXML
    private Canvas coordinatesCanvas;

    @FXML
    private TextField seedTextField;

    @FXML
    private TextField factorTextField;


    private void applyTeamCount(ActionEvent e) {
        System.out.println("Change");
    }

}

