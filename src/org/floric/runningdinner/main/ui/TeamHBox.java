package org.floric.runningdinner.main.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;

import java.awt.geom.Point2D;

/**
 * Created by florian on 06.03.2016.
 */
public class TeamHBox extends HBox {

    private Label teamIndexLabel = new Label("0");

    private TextField namesOneTextField = new TextField("Name 1, Forename 1");
    private TextField namesTwoTextField = new TextField("Name 2, Forename 2");
    private TextField addressTextField = new TextField("Street, City");
    private TextField coordinatesTextField = new TextField("0.0, 0.0");
    private Button deleteTeamButton = new Button("Delete team");

    private Team assignedTeam;

    public TeamHBox(Team t) {
        ObservableList<Node> boxChildren = this.getChildren();

        // add elements to box
        boxChildren.add(teamIndexLabel);
        boxChildren.add(new Label("Names"));
        boxChildren.add(namesOneTextField);
        boxChildren.add(new Label("&"));
        boxChildren.add(namesTwoTextField);
        boxChildren.add(new Label("Address"));
        boxChildren.add(addressTextField);
        boxChildren.add(new Label("Coordinates"));
        boxChildren.add(coordinatesTextField);
        boxChildren.add(deleteTeamButton);

        // layout properties
        teamIndexLabel.setMinWidth(20);
        this.setSpacing(10);

        setAlignment(Pos.CENTER_LEFT);

        assignedTeam = t;
        teamIndexLabel.setText(String.valueOf(assignedTeam.getTeamIndex()));
        namesOneTextField.setText(t.getPersonA().toString());
        namesTwoTextField.setText(t.getPersonB().toString());
        addressTextField.setText(t.getAddress());
        coordinatesTextField.setText(t.getLocation().getX() + ", " + t.getLocation().getY());

        addListeners();
    }

    public Button getDeleteButton() {
        return deleteTeamButton;
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    // events for save after editing the text fields
    private void addListeners() {
        // add automatic saving after leaving the input focus

        namesOneTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                Person pA = assignedTeam.getPersonA();
                pA.formatCommaInput(namesOneTextField.getText(), true);
                namesOneTextField.setText(pA.getLastName() + ", " + pA.getFirstName());
            }
        });

        namesTwoTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                Person pB = assignedTeam.getPersonB();
                pB.formatCommaInput(namesTwoTextField.getText(), true);
                namesTwoTextField.setText(pB.getLastName() + ", " + pB.getFirstName());
            }
        });

        addressTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                assignedTeam.setAddress(addressTextField.getText());
            }
        });

        coordinatesTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                // parse input
                String input = coordinatesTextField.getText();
                String[] parts = input.split(",");

                // validate input
                try {
                    assignedTeam.setLocation(new Point2D.Double(Double.valueOf(parts[0]), Double.valueOf(parts[1])));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex2) {
                    assignedTeam.setLocation(new Point2D.Double(0, 0));
                    Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Invalid coordinates format!");
                }

                coordinatesTextField.setText(assignedTeam.getLocation().getX() + ", " + assignedTeam.getLocation().getY());
            }
        });
    }
}
