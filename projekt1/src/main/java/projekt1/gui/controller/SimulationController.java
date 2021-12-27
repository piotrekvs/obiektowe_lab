package projekt1.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    private WorldMap sim1Map;
    private int netArrayLength;
    private boolean isNetDrawn = false;

    @FXML
    private GridPane sim1GridPane;

    public SimulationController(SimulationInterface simulationInterface, WorldMap mapNotWrapped) throws FileNotFoundException {
        this.simulationInterface = simulationInterface;
        this.sim1Map = mapNotWrapped;
    }

    public void draw() {
        drawMap(sim1GridPane, sim1Map);
    }

    private void drawMap(GridPane gridPane, WorldMap map) {
        if (!isNetDrawn) {
            drawNet(sim1GridPane, sim1Map);
        }
        if (sim1GridPane.getChildren().size() > netArrayLength) {
            sim1GridPane.getChildren().remove(netArrayLength);
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
        netArrayLength = gridPane.getChildren().size();
        isNetDrawn = true;
    }

    public void startSim1Btn() {
        draw();
    }

    @Override
    public void eraEndedSim1() {
        drawMap(sim1GridPane, sim1Map);
    }

    @Override
    public void eraEndedSim2() {

    }
}
