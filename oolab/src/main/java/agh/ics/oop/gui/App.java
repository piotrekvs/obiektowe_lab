package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class App extends Application implements IPositionChangeObserver {
    private Stage primaryStage;
    private GridPane gridPane;
    private AbstractWorldMap map;

    @Override
    public void start(Stage primaryStage) { //throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Animals project");
        this.gridPane = new GridPane();
        this.gridPane.setGridLinesVisible(true);

        // Map init
        this.map = new GrassField(5);
        map.addObserver(this);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine = new SimulationEngine(map, positions);

        // Draw first scene
        VBox fullView = new VBox();
        HBox formView = new HBox();
        Button button = new Button();
        TextField textField = new TextField();
        button.setText("Start");
        button.setOnAction(e -> {
            textField.getText();
            MoveDirection[] directions = new OptionsParser().parse(textField.getText().split(" "));
            engine.setDirections(directions);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
        formView.getChildren().addAll(textField, button);
        fullView.getChildren().addAll(gridPane, formView);
        draw();
        Scene scene = new Scene(fullView, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        // Show scene
    }

    private void draw() {
        // Place elements on the grid
        Vector2d lowerLeft = map.getLowerLeftDraw();
        Vector2d upperRight = map.getUpperRightDraw();
        gridPane.add(new Label("y/x"), 0,0,1,1);
        gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        gridPane.getRowConstraints().add(new RowConstraints(40));
        for (int x = lowerLeft.getX(), j = 1; x <= upperRight.getX(); x++, j++) {
            Label label = new Label(Integer.toString(x));
            gridPane.add(label, j,0,1,1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int y = upperRight.getY(), j = 1; y >= lowerLeft.getY(); y--, j++) {
            Label label = new Label(Integer.toString(y));
            gridPane.add(label,0, j,1,1);
            gridPane.getRowConstraints().add(new RowConstraints(50));
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
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            Node linie = gridPane.getChildren().get(0);
            gridPane.getChildren().clear();
            gridPane.getChildren().add(linie);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            draw();
        });
    }
}
