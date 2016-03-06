package org.floric.runningdinner.main.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Created by florian on 06.03.2016.
 */
public class TeamHBox extends HBox {

    private TextField namesOneTextField = new TextField("Name 2");
    private TextField namesTwoTextField = new TextField("Name 1");
    private TextField addressTextField = new TextField("Street, City");
    private Button deleteTeamButton = new Button("Delete team");

    public TeamHBox() {
        this.getChildren().add(new Label("Names"));
        this.getChildren().add(namesOneTextField);
        this.getChildren().add(new Label("&"));
        this.getChildren().add(namesTwoTextField);
        this.getChildren().add(new Label("Address"));
        this.getChildren().add(addressTextField);
        this.getChildren().add(deleteTeamButton);


    }

    public String getAddress() {
        return addressTextField.getText();
    }

    public Button getDeleteButton() {
        return deleteTeamButton;
    }



}
