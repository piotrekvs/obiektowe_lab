package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) { //throws Exception {
        // Map init
        String[] inputDirs = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(inputDirs);
        AbstractWorldMap map = new GrassField(5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // Init gui
        primaryStage.setTitle("Animals project");
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        // Place elements on the grid
        Vector2d lowerLeft = map.getLowerLeftDraw();
        Vector2d upperRight = map.getUpperRightDraw();

        gridPane.add(new Label("y/x"), 0,0,1,1);
        for (int x = lowerLeft.getX(), j = 1; x <= upperRight.getX(); x++, j++) {
            Label label = new Label(Integer.toString(x));
            gridPane.add(label, j,0,1,1);
        }
        for (int y = upperRight.getY(), j = 1; y >= lowerLeft.getY(); y--, j++) {
            Label label = new Label(Integer.toString(y));
            gridPane.add(label,0, j,1,1);
        }
        for (int x = lowerLeft.getX(), xj = 1; x <= upperRight.getX(); x++, xj++) {
            for (int y = upperRight.getY(), yj = 1; y >= lowerLeft.getY(); y--, yj++) {
                Vector2d position = new Vector2d(x, y);
                if (map.isOccupied(position)) {
                    IMapElement mapElement = map.objectAt(position);
                    gridPane.add(new GuiElementBox(mapElement).getBox(), xj, yj, 1,1);
                }
            }
        }
        // Show scene
        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
