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
import org.floric.runningdinner.util.GuiUtil;
import org.floric.runningdinner.util.ILogOutput;
import org.floric.runningdinner.util.MeanCluster;
import org.reactfx.util.FxTimer;

import java.awt.geom.Point2D;
import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

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

    @FXML
    private TitledPane teamsPane;
    @FXML
    private TitledPane coordsPane;
    @FXML
    private TitledPane distributionPane;

    private List<Point2D> usedCenters = new LinkedList<>();
    private Map<Team, TeamHBox> knownTeams = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Logger.addObserver(this);
        Core.getInstance().addObserver(this);

        GuiUtil.initSpinner(randomTeamsCountSpinner, Core.TEAMS_MIN, Core.TEAMS_MAX, Core.TEAMS_DEFAULT, 1);
        GuiUtil.initSpinner(randomSeedSpinner, Core.SEED_MIN, Core.SEED_MAX, Core.SEED_DEFAULT, 1);

        gc = coordinatesCanvas.getGraphicsContext2D();

        addListeners();

        update();
    }

    @FXML
    protected void groupTeamsClicked(MouseEvent event) {
        Core c = Core.getInstance();

        ICluster cluster = new MeanCluster();
        int neededClasses = (int) Math.floor((double) c.getTeams().size() / 9);
        cluster.clusterPoints(neededClasses, c.getTeams(), true);
        usedCenters = cluster.getCenters();

        drawTeamGroups();
        Core.getInstance().notifyObservers();
    }

    private void drawTeamGroups() {
        List<Team> usedTeams = Core.getInstance().getTeams();
        if (usedTeams.isEmpty()) {
            return;
        }

        List<TeamGroup> clusteredPoints = Core.getInstance().getTeamGroups();
        Point2D min = Team.getMinLocation(usedTeams);
        Point2D max = Team.getMaxLocation(usedTeams);
        Point2D canvasSize = new Point2D.Double(canvasPane.getWidth() - 2 * CANVAS_PADDING, canvasPane.getHeight() - 2 * CANVAS_PADDING);

        // clear canvas
        gc.clearRect(0, 0, canvasPane.getWidth(), canvasPane.getHeight());

        // draw centers
        for (Point2D pt : usedCenters) {
            Point2D leftBottom = transformCoordinatesToCanvas(min, max, new Point2D.Double(pt.getX() - 1, pt.getY() + 1), canvasSize);
            Point2D leftTop = transformCoordinatesToCanvas(min, max, new Point2D.Double(pt.getX() - 1, pt.getY() - 1), canvasSize);
            Point2D rightBottom = transformCoordinatesToCanvas(min, max, new Point2D.Double(pt.getX() + 1, pt.getY() + 1), canvasSize);
            Point2D rightTop = transformCoordinatesToCanvas(min, max, new Point2D.Double(pt.getX() + 1, pt.getY() - 1), canvasSize);

            gc.strokeLine(leftBottom.getX(), leftBottom.getY(), rightTop.getX(), rightTop.getY());
            gc.strokeLine(leftTop.getX(), leftTop.getY(), rightBottom.getX(), rightBottom.getY());
        }

        // draw points for every teamgroup
        for(int i = 0; i < clusteredPoints.size(); i++) {
            TeamGroup tg = clusteredPoints.get(i);
            Set<Team> teams = tg.getTeams();

            // set color
            float colorAngle = i * 360.0f / clusteredPoints.size();
            tg.setColor(Color.hsb(colorAngle, 1.0, 1.0));

            for(Team t: teams) {
                Point2D screenCoords = transformCoordinatesToCanvas(min, max, t.getLocation(), canvasSize);

                // draw point
                gc.setFill(tg.getColor());
                gc.fillOval(screenCoords.getX(), screenCoords.getY(), CANVAS_POINT_SIZE, CANVAS_POINT_SIZE);
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(t.getTeamIndex()), screenCoords.getX(), screenCoords.getY());
            }
        }
    }

    private Point2D transformCoordinatesToCanvas(Point2D min, Point2D max, Point2D point, Point2D canvasSize) {
        Point2D boundingDistance = new Point2D.Double(max.getX() - min.getX(), max.getY() - min.getY());

        double xRatio = (canvasSize.getX() - min.getX()) / boundingDistance.getX();
        double yRatio = (canvasSize.getY() - min.getY()) / boundingDistance.getY();
        double scaleFactor = xRatio < yRatio ? xRatio : yRatio;

        double x = (point.getX() - min.getX()) * scaleFactor + CANVAS_PADDING;
        double y = (point.getY() - min.getY()) * scaleFactor + CANVAS_PADDING;

        return new Point2D.Double(x, y);
    }

    @FXML
    protected void addRandomTeamsClicked(MouseEvent event) {
        Core c = Core.getInstance();
        c.getDataGenerator().changeSeed(randomSeedSpinner.getValue());

        for (int i = 0; i < randomTeamsCountSpinner.getValue(); i++) {
            Team t = c.getDataGenerator().getRandomTeam();
            c.addTeam(t);
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
        if (coordsPane.isExpanded()) {
            drawTeamGroups();
        }

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

        List<TeamGroup> teamsGroups = Core.getInstance().getTeamGroups();

        for (TeamGroup tg : teamsGroups) {
            for (Team t : tg.getTeams()) {
                teamsBox.getChildren().add(new TeamHBox(t));
            }
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
        FxTimer.runLater(Duration.ofSeconds(Core.DISPLAY_DURATION_SEC), () -> statusLabel.setText(""));
    }
}

