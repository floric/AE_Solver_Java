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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.floric.runningdinner.main.base.ICluster;
import org.floric.runningdinner.main.base.IObserver;
import org.floric.runningdinner.main.core.*;
import org.floric.runningdinner.util.ILogOutput;
import org.floric.runningdinner.util.MeanCluster;
import org.reactfx.util.FxTimer;

import java.awt.geom.Point2D;
import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Closeable, IObserver, ILogOutput {

    private static final int CANVAS_PADDING = 20;
    private static final int CANVAS_POINT_SIZE = 5;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane canvasPane;
    @FXML
    private VBox teamsBox;

    @FXML
    private Button groupTeamsButton;
    @FXML
    private Button addRandomTeamsButton;
    @FXML
    private Button clearTeamsButton;

    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem settingsMenuItem;

    @FXML
    private Label statusLabel;

    @FXML
    private Spinner<Integer> randomSeedSpinner;
    @FXML
    private Spinner<Integer> randomTeamsCountSpinner;

    @FXML
    private Canvas coordinatesCanvas;
    private GraphicsContext gc;

    @FXML
    private TextField seedTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Logger.addObserver(this);

        initSpinner(randomTeamsCountSpinner, Core.TEAMS_MIN, Core.TEAMS_MAX, Core.TEAMS_DEFAULT, 1);
        initSpinner(randomSeedSpinner, Core.SEED_MIN, Core.SEED_MAX, Core.SEED_DEFAULT, 1);

        gc = coordinatesCanvas.getGraphicsContext2D();

        addListeners();

        update();
    }

    @FXML
    protected void groupTeamsClicked(MouseEvent event) {
        Core c = Core.getInstance();

        ArrayList<Team> allTeams = new ArrayList<>();
        c.getTeamsGroups().forEach(teamGroup -> allTeams.addAll(teamGroup.getTeams()));

        ICluster cluster = new MeanCluster();
        cluster.clusterPoints(3, allTeams);

        drawTeamGroups();
    }

    private void drawTeamGroups() {
        List<TeamGroup> clusteredPoints = Core.getInstance().getTeamsGroups();
        Point2D min = Team.getMinLocation(Core.getInstance().getTeams());
        Point2D max = Team.getMaxLocation(Core.getInstance().getTeams());

        gc.clearRect(0, 0, canvasPane.getWidth(), canvasPane.getHeight());

        // draw points for every teamgroup
        for(int i = 0; i < clusteredPoints.size(); i++) {
            TeamGroup tg = clusteredPoints.get(i);
            ArrayList<Team> teams = tg.getTeams();

            for(Team t: teams) {
                float colorAngle = i * 360.0f / clusteredPoints.size();

                Point2D screenCoords = transformCoordinatesToCanvas(min, max, t.getLocation(), new Point2D.Double(canvasPane.getWidth() - 2 * CANVAS_PADDING, canvasPane.getHeight() - 2 * CANVAS_PADDING));

                // draw point
                gc.setFill(Color.hsb(colorAngle, 1.0, 1.0));
                gc.fillOval(screenCoords.getX() + CANVAS_PADDING, screenCoords.getY() + CANVAS_PADDING, CANVAS_POINT_SIZE, CANVAS_POINT_SIZE);
            }
        }
    }

    private Point2D transformCoordinatesToCanvas(Point2D min, Point2D max, Point2D point, Point2D canvasSize) {
        Point2D boundingDistance = new Point2D.Double(max.getX() - min.getX(), max.getY() - min.getY());

        double xRatio = (canvasSize.getX() - min.getX()) / boundingDistance.getX();
        double yRatio = (canvasSize.getY() - min.getY()) / boundingDistance.getY();
        double scaleFactor = xRatio < yRatio ? xRatio : yRatio;

        double x = (point.getX() - min.getX()) * scaleFactor;
        double y = (point.getY() - min.getY()) * scaleFactor;

        return new Point2D.Double(x, y);
    }

    @FXML
    protected void addRandomTeamsClicked(MouseEvent event) {
        Core c = Core.getInstance();
        c.getDataGenerator().changeSeed(randomSeedSpinner.getValue());

        for (int i = 0; i < randomTeamsCountSpinner.getValue(); i++) {
            Team t = c.getDataGenerator().getRandomTeam();
            Core.getInstance().addTeam(t);
        }
    }

    @FXML
    protected void clearTeamsClicked(MouseEvent event) {
        Core.getInstance().reset();
    }

    @FXML
    protected void addTeamSlot(MouseEvent event) {
        Core.getInstance().addTeam(new Team(new Person(), new Person()));
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Team slot added!");
    }

    private void addTeamSlot(Team t) {
        TeamHBox teamBox = new TeamHBox(t);
        teamsBox.getChildren().add(teamBox);

        teamBox.getDeleteButton().setOnMouseClicked(event1 -> {
            teamsBox.getChildren().remove(teamBox);
            Core.getInstance().removeTeam(teamBox.getAssignedTeam());
            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Team slot deleted!");
        });
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
        drawTeamGroups();
    }

    @FXML
    private void exitApplication() {
        Core.getInstance().exit();
    }

    @FXML
    private void aboutApplication() {
        Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Not implemented yet.");
    }

    @FXML
    private void openSettings() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/settings.fxml"));

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Settings");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(canvasPane.getScene().getWindow());
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

    @Override
    public void update() {
        int currentTeamsSize = teamsBox.getChildren().size();
        teamsBox.getChildren().remove(2, currentTeamsSize);

        List<TeamGroup> teamsGroups = Core.getInstance().getTeamsGroups();
        for (TeamGroup tg : teamsGroups) {
            tg.getTeams().forEach(this::addTeamSlot);
        }
    }

    @Override
    public void updateLogOutput(String text, Logger.LOG_VERBOSITY verbosity) {
        switch (verbosity) {
            case MAIN:
                statusLabel.setTextFill(Color.WHITE);

                break;
            case ERROR:
                statusLabel.setTextFill(Color.RED);

                break;
            default:
        }

        statusLabel.setText(text);

        // add timer to reset the text after DISPLAY_DURATION_SEC seconds
        FxTimer.runLater(
                Duration.ofSeconds(Core.DISPLAY_DURATION_SEC),
                () -> statusLabel.setText(""));
    }
}

