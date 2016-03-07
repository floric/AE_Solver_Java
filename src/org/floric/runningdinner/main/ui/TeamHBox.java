package org.floric.runningdinner.main.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.floric.runningdinner.main.base.IPersistent;

import java.io.Serializable;

/**
 * Created by florian on 06.03.2016.
 */
public class TeamHBox extends HBox implements IPersistent {

    private static int countingTeamIndex = 0;

    private Label teamIndexLabel = new Label(String.valueOf(getNextIndex()));
    private Label namesLabel = new Label("Names");
    private Label addressLabel = new Label("Address");
    private Label namesAndLabel = new Label("&");

    private TextField namesOneTextField = new TextField("Name 1");
    private TextField namesTwoTextField = new TextField("Name 2");
    private TextField addressTextField = new TextField("Street, City");
    private Button deleteTeamButton = new Button("Delete team");

    public TeamHBox() {
        ObservableList<Node> boxChildren = this.getChildren();

        // add elements to box
        boxChildren.add(teamIndexLabel);
        boxChildren.add(namesLabel);
        boxChildren.add(namesOneTextField);
        boxChildren.add(namesAndLabel);
        boxChildren.add(namesTwoTextField);
        boxChildren.add(addressLabel);
        boxChildren.add(addressTextField);
        boxChildren.add(deleteTeamButton);

        // layout properties
        teamIndexLabel.setMinWidth(20);

        namesLabel.setPadding(new Insets(0, 10, 0, 0));
        addressLabel.setPadding(new Insets(0, 10, 0, 10));
        namesAndLabel.setPadding(new Insets(0, 10, 0, 10));

        setAlignment(Pos.CENTER_LEFT);


    }

    public Button getDeleteButton() {
        return deleteTeamButton;
    }

    private static int getNextIndex() {
        return countingTeamIndex++;
    }

    public String getPersonOne() {
        return namesOneTextField.getText();
    }

    public String getPersonTwo() {
        return namesTwoTextField.getText();
    }

    public String getAddress() {
        return addressTextField.getText();
    }

    @Override
    public String getType() {
        return "TeamBox";
    }

    @Override
    public void readObject(String data) {

    }

    @Override
    public String writeObject() {
        StringBuilder strBld = new StringBuilder();

        strBld.append(getPersonOne() + "\n");
        strBld.append(getPersonTwo() + "\n");
        strBld.append(getAddress() + "\n\n");

        return  strBld.toString();
    }
}
