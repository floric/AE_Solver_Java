package org.floric.runningdinner.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.floric.runningdinner.main.core.Logger;

public class Main extends Application {

    final static int STD_TEAM_COUNT = 9;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Logger.Log(Logger.LOG_VERBOSITY.IMPORTANT, "Application started!");

        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        primaryStage.setTitle("Running Dinner");
        primaryStage.setScene(new Scene(root, 640, 500));
        primaryStage.show();

        /*ArrayList<int[]> permutations = Statistics.getCookPermutations(STD_TEAM_COUNT);
        for(int[] p : permutations) {
            for(int i = 0; i < p.length; i++) {
                System.out.print(p[i]+ ",");
            }
            System.out.println("");
        }*/

    }
}
