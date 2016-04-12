package org.floric.runningdinner.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Logger;

public class Main extends Application {

    final static int STD_TEAM_COUNT = 9;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Application started!");

        Core c = Core.getInstance();
        c.readSafeFile();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/main.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Running Dinner");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        // call exit from core on exit try
        primaryStage.setOnCloseRequest(event -> Core.getInstance().exit());
    }
}
