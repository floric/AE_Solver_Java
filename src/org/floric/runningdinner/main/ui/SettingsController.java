package org.floric.runningdinner.main.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.floric.runningdinner.main.base.IObserver;
import org.floric.runningdinner.main.core.Core;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/** Settings Controller
 *
 * Created by florian on 17.03.2016.
 */
public class SettingsController implements Initializable, IObserver {

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
        Core.getInstance().addObserver(this);
        update();
    }

    @FXML
    private void closeDialog(MouseEvent ev) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void changeSettingsPath() {
        DirectoryChooser chooser = new DirectoryChooser();
        //chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Running Dinner Safefile", "*." + Core.getInstance().SAFE_EXTENSION));
        chooser.setInitialDirectory(new File(Core.getInstance().getSafeDir()));

        File file = chooser.showDialog(pane.getScene().getWindow());

        if (file != null) {
            Core.getInstance().setSafeDir(file.getAbsolutePath());
        }

    }


    @Override
    public void update() {
        pathLabel.setText(Core.getInstance().getSafePath());
    }
}
