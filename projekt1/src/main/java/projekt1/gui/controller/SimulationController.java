package projekt1.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import projekt1.gui.GuiElementView;
import projekt1.gui.SimulationInterface;
import projekt1.simulation.elements.Animal;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.IEraEndedObserver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

public class SimulationController implements IEraEndedObserver {
    private final SimulationInterface simulationInterface;
    private final int NET_SIZE = 20;
    private final GuiElementView guiElementBox = new GuiElementView(NET_SIZE);
    private final WorldMap sim1Map;
    private final WorldMap sim2Map;
    private int netArrayLength;
    private boolean isNetDrawn = false;

    @FXML
    private GridPane sim1GridPane;

    @FXML
    private GridPane sim2GridPane;

    public SimulationController(SimulationInterface simulationInterface, WorldMap mapNotWrapped, WorldMap mapWrapped)
            throws FileNotFoundException {
        this.simulationInterface = simulationInterface;
        this.sim1Map = mapNotWrapped;
        this.sim2Map = mapWrapped;

    }

    public void draw() {
        drawNet(sim1GridPane, sim1Map);
        drawNet(sim2GridPane, sim2Map);
    }

    private void drawMap(GridPane gridPane, WorldMap map) {
        if (gridPane.getChildren().size() > netArrayLength) {
            gridPane.getChildren().remove(netArrayLength, gridPane.getChildren().size());
        }

        Set<Vector2d> grassKeySet = map.getGrassHashMap().keySet();
        Set<Vector2d> animalKeySet = map.getAnimalHashMap().keySet();

        grassKeySet.forEach(position -> {
            if (!map.isAnimal(position)) {
                gridPane.add(guiElementBox.getGrassImg(), position.getX() + 1,
                        map.getUpperRight().getY() + 1 - position.getY(), 1, 1);
            }
        });

        animalKeySet.forEach(position -> {
            ArrayList<Animal> animals = map.getAnimalHashMap().get(position);
            int energyPercent = animals.get(animals.size() - 1).getEnergyPercent();
            gridPane.add(guiElementBox.getAnimalImg(energyPercent), position.getX() + 1,
                    map.getUpperRight().getY() + 1 - position.getY(), 1, 1);
        });
    }

    private void drawNet(GridPane gridPane, WorldMap map) {
        gridPane.setGridLinesVisible(true);
        gridPane.add(new Label("y/x"), 0, 0, 1, 1);
        gridPane.getColumnConstraints().add(new ColumnConstraints(NET_SIZE));
        gridPane.getRowConstraints().add(new RowConstraints(NET_SIZE));
        for (int x = 1; x <= map.getUpperRight().getX() + 1; x++) {
            Label label = new Label(Integer.toString(x - 1));
            gridPane.add(label, x, 0, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(NET_SIZE));
        }
        for (int y = map.getUpperRight().getY(), j = 1; y >= 0; y--, j++) {
            Label label = new Label(Integer.toString(y));
            gridPane.add(label, 0, j, 1, 1);
            gridPane.getRowConstraints().add(new RowConstraints(NET_SIZE));
        }
        for (int x = 1; x <= map.getUpperRight().getX() + 1; x++ ) {
            for (int y = map.getUpperRight().getY(), j = 1; y >= 0; y--, j++) {
                Rectangle rect = new Rectangle(0,0,NET_SIZE-1,NET_SIZE-1);
                if (map.isJungle(new Vector2d(x - 1, y))) {
                    rect.setFill(Color.web("#66ff99"));
                } else {
                    rect.setFill(Color.web("#ccff99"));
                }
                gridPane.add(rect, x, j, 1, 1);
            }
        }
        netArrayLength = gridPane.getChildren().size();
        isNetDrawn = true;
    }

    public void startSim1Btn() {
        draw();
    }

    @Override
    public void eraEnded(boolean isWrapped) {
        if (isWrapped) {
            Platform.runLater(() -> {
                drawMap(sim2GridPane, sim2Map);
            });
        } else {
            Platform.runLater(() -> {
                drawMap(sim1GridPane, sim1Map);
            });
        }
    }
}
