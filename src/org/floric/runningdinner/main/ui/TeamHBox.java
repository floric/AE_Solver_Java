package org.floric.runningdinner.main.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;

/**
 * Created by florian on 06.03.2016.
 */
public class TeamHBox extends HBox {

    private Label teamIndexLabel = new Label(String.valueOf(Team.getNextIndex()));
    private Label namesLabel = new Label("Names");
    private Label coordsLabel = new Label("Coordinates");
    private Label namesAndLabel = new Label("&");

    private TextField namesOneTextField = new TextField("Name 1, Vorname 1");
    private TextField namesTwoTextField = new TextField("Name 2, Vorname 2");
    private TextField coordinatesTextField = new TextField("0.0, 0.0");
    private Button deleteTeamButton = new Button("Delete team");

    private Team assignedTeam;

    public TeamHBox() {
        ObservableList<Node> boxChildren = this.getChildren();

        // add elements to box
        boxChildren.add(teamIndexLabel);
        boxChildren.add(namesLabel);
        boxChildren.add(namesOneTextField);
        boxChildren.add(namesAndLabel);
        boxChildren.add(namesTwoTextField);
        boxChildren.add(coordsLabel);
        boxChildren.add(coordinatesTextField);
        boxChildren.add(deleteTeamButton);

        // layout properties
        teamIndexLabel.setMinWidth(20);
        this.setSpacing(5);

        namesLabel.setPadding(new Insets(0, 10, 0, 0));
        coordsLabel.setPadding(new Insets(0, 10, 0, 10));
        namesAndLabel.setPadding(new Insets(0, 10, 0, 10));

        setAlignment(Pos.CENTER_LEFT);

        assignedTeam = new Team(new Person(namesOneTextField.getText()), new Person(namesTwoTextField.getText()));
        Core.getInstance().addTeam(assignedTeam);

        addListeners();
    }

    public Button getDeleteButton() {
        return deleteTeamButton;
    }

    public String getPersonOne() {
        return namesOneTextField.getText();
    }

    public void setPersonOne(String name) {
        namesOneTextField.setText(name);
    }

    public String getPersonTwo() {
        return namesTwoTextField.getText();
    }

    public void setPersonTwo(String name) {
        namesTwoTextField.setText(name);
    }

    public String getAddress() {
        return coordinatesTextField.getText();
    }

    public void setCoordinates(String address) {
        coordinatesTextField.setText(address);
    }

    public int getTeamIndex() {
        return Integer.valueOf(teamIndexLabel.getText());
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    // events for save after editing the text fields
    private void addListeners() {
        namesOneTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                Person pA = assignedTeam.getPersonA();
                pA.formatCommaInput(namesOneTextField.getText());
                namesOneTextField.setText(pA.getLastName() + ", " + pA.getFirstName());

                Core.getInstance().writeSafeFile();
            }
        });

        namesTwoTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                Person pB = assignedTeam.getPersonB();
                pB.formatCommaInput(namesTwoTextField.getText());
                namesTwoTextField.setText(pB.getLastName() + ", " + pB.getFirstName());

                Core.getInstance().writeSafeFile();
            }
        });

        coordinatesTextField.focusedProperty().addListener((field, oldValue, newValue) -> {
            if (!newValue) {
                // parse input
                String input = coordinatesTextField.getText();
                String[] parts = input.split(",");

                // validate input
                if (parts.length == 2) {
                    assignedTeam.setLocation(new Point2D(Double.valueOf(parts[0]), Double.valueOf(parts[1])));
                } else {
                    assignedTeam.setLocation(new Point2D(0, 0));
                    Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Invalid coordinates format!");
                }

                coordinatesTextField.setText(assignedTeam.getLocation().getX() + ", " + assignedTeam.getLocation().getY());

                Core.getInstance().writeSafeFile();
            }
        });
    }
}
