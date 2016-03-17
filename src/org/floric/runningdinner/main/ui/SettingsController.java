package org.floric.runningdinner.main.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.floric.runningdinner.main.core.Core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/** Settings Controller
 *
 * Created by florian on 17.03.2016.
 */
public class SettingsController implements Initializable {

    @FXML
    private BorderPane pane;

    @FXML
    private Button closeDialogButton;
    @FXML
    private Button changeSafePathButton;

    @FXML
    private Label pathLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
    }

    @FXML
    private void closeDialog() {

    }

    @FXML
    private void changeSettingsPath() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Running Dinner Safefile", "*." + Core.getInstance().getSafeExtension())
        );

        File file = chooser.showSaveDialog(pane.getScene().getWindow());

        if (file != null) {
            Core.getInstance().setSafePath(file.toString());
            updateUI();
        }

    }

    private void updateUI() {
        pathLabel.setText(Core.getInstance().getSafePath());
    }
}
