package org.floric.runningdinner.main.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.floric.runningdinner.main.base.ICluster;
import org.floric.runningdinner.main.base.IPersistent;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Team;
import org.floric.runningdinner.main.core.TeamGroup;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.util.DataWriterReader;
import org.floric.runningdinner.util.MeanCluster;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Closeable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane canvasPane;
    @FXML
    private VBox teamsBox;

    @FXML
    private Button applyTeamsButton;
    @FXML
    private Button generateCoordinatesButton;

    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem settingsMenuItem;


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
        c.Reset();
        c.getDataGenerator().changeSeed(randomSeedSpinner.getValue());
        c.getDataGenerator().setTeamCount(teamCountSpinner.getValue());

        ICluster cluster = new MeanCluster();
        cluster.clusterPoints(3, c.getDataGenerator().getRandomTeams());
        List<TeamGroup> clusteredPoints = c.getTeamsGroups();

        // draw points
        for(int i = 0; i < clusteredPoints.size(); i++) {
            TeamGroup tg = clusteredPoints.get(i);
            ArrayList<Team> teams = tg.getTeams();
            System.out.println("Class " + i + " has " + teams.size() + " teams!");
            for(Team t: teams) {
                gc.fillOval(t.getLocation().getX()+100, t.getLocation().getY()+100, 4, 4);
            }
        }
    }

    @FXML
    protected void addTeamSlot(MouseEvent event) {
        TeamHBox teamBox = new TeamHBox();
        teamsBox.getChildren().add(teamsBox.getChildren().size() - 1, teamBox);

        teamBox.getDeleteButton().setOnMouseClicked(event1 -> {
            teamsBox.getChildren().remove(teamBox);
            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Team slot deleted!");
        });

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Team slot added!");
    }

    @FXML
    protected void importFile() {

    }

    @FXML
    protected void exportFile(MouseEvent event) {
        DataWriterReader rw = new DataWriterReader();

        String exportPath = "";

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Teams exported to: " + exportPath);

        teamsBox.getChildren().stream().filter(node -> node instanceof IPersistent).forEach(node1 -> {
            IPersistent teamBox = (IPersistent) node1;
            rw.addObject(teamBox);
        });

        try {
            rw.writeFile(Core.getInstance().getSafePath());
        } catch (IOException e) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Filewrite was not successfull!");
        }
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
        //initSpinner(teamCountSpinner, Core.TEAMS_MIN, Core.TEAMS_MAX, Core.TEAMS_DEFAULT, 3);
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

    @FXML
    private void exitApplication() {
        Core.getInstance().exit();
    }

    @FXML
    private void aboutApplication() {

    }

    @FXML
    private void openSettings() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/settings.fxml"));

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Settings");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner((Stage) canvasPane.getScene().getWindow());
            dialogStage.setScene(new Scene(root));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        Platform.exit();
    }
}

