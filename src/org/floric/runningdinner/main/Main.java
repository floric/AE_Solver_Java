package org.floric.runningdinner.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.floric.runningdinner.util.DataGenerator;
import org.floric.runningdinner.util.Statistics;

import java.util.ArrayList;

public class Main extends Application {

    final static int STD_TEAM_COUNT = 9;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 500));
        primaryStage.show();


        /*ArrayList<int[]> permutations = Statistics.getCookPermutations(STD_TEAM_COUNT);
        for(int[] p : permutations) {
            for(int i = 0; i < p.length; i++) {
                System.out.print(p[i]+ ",");
            }
            System.out.println("");
        }*/

        DataGenerator testData = new DataGenerator(STD_TEAM_COUNT, 1L);
        Pair<Double, Double>[] coords = testData.getCoords();
        for (int i = 0; i < coords.length; i++) {
            Pair<Double, Double> coord = coords[i];
            System.out.println(coord);
        }



    }


    public static void main(String[] args) {
        launch(args);
    }
}
