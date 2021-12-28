package projekt1.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import projekt1.gui.GuiElementView;
import projekt1.gui.SimulationInterface;
import projekt1.simulation.elements.Animal;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.IEraEndedObserver;
import projekt1.utils.IStatisticsUpdateObserver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

public class SimulationController implements IEraEndedObserver, IStatisticsUpdateObserver {
    private final SimulationInterface simulationInterface;
    private final int NET_SIZE = 20;
    private final GuiElementView guiElementBox = new GuiElementView(NET_SIZE);
    private final WorldMap sim1Map;
    private final WorldMap sim2Map;
    private int netArrayLength;
    private StatisticsHelper stats = new StatisticsHelper();
    private boolean isAnimalSim1Selected = false;
    private Animal selectedAnimalSim1;
    private boolean isAnimalSim2Selected = false;
    private Animal selectedAnimalSim2;

    @FXML private GridPane sim1GridPane;
    @FXML private GridPane sim2GridPane;
    @FXML private LineChart<Number, Number> LivingAnimAndPlantsChartSim1;
    @FXML private LineChart<Number, Number> EnergyChartSim1;
    @FXML private LineChart<Number, Number> ChildrenChartSim1;
    @FXML private LineChart<Number, Number> lifespanChartSim1;
    @FXML private Label deadAnimalsSim1;
    @FXML private Label dominantGenomeSim1;
    @FXML private Label magicalEventsSim1;
    @FXML private LineChart<Number, Number> LivingAnimAndPlantsChartSim2;
    @FXML private LineChart<Number, Number> EnergyChartSim2;
    @FXML private LineChart<Number, Number> ChildrenChartSim2;
    @FXML private LineChart<Number, Number> lifespanChartSim2;
    @FXML private Label deadAnimalsSim2;
    @FXML private Label dominantGenomeSim2;
    @FXML private Label magicalEventsSim2;

    @FXML private Label AnimalGenomeSim1;
    @FXML private Label AnimalGenomeSim2;

    public SimulationController(SimulationInterface simulationInterface, WorldMap mapNotWrapped, WorldMap mapWrapped)
            throws FileNotFoundException {
        this.simulationInterface = simulationInterface;
        this.sim1Map = mapNotWrapped;
        this.sim2Map = mapWrapped;
    }

    public void draw() {
        drawNet(sim1GridPane, sim1Map);
        drawNet(sim2GridPane, sim2Map);
        // Charts Sim1
        LivingAnimAndPlantsChartSim1.getData().add(stats.animalsAliveDataSim1);
        LivingAnimAndPlantsChartSim1.getData().add(stats.plantsAliveDataSim1);
        EnergyChartSim1.getData().add(stats.avgEnergyLvlDataSim1);
        ChildrenChartSim1.getData().add(stats.avgChildrenNumDataSim1);
        lifespanChartSim1.getData().add(stats.avgLifespanNumDataSim1);
        // Charts Sim2
        LivingAnimAndPlantsChartSim2.getData().add(stats.animalsAliveDataSim2);
        LivingAnimAndPlantsChartSim2.getData().add(stats.plantsAliveDataSim2);
        EnergyChartSim2.getData().add(stats.avgEnergyLvlDataSim2);
        ChildrenChartSim2.getData().add(stats.avgChildrenNumDataSim2);
        lifespanChartSim2.getData().add(stats.avgLifespanNumDataSim2);
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
            ImageView iv = guiElementBox.getAnimalImg(energyPercent);
            iv.setOnMouseClicked(e -> {
                if (map.isWrapped()) {
                    isAnimalSim2Selected = true;
                    onSelectedAnimalSim2(animals.get(animals.size() - 1));
                } else {
                    isAnimalSim1Selected = true;
                    onSelectedAnimalSim1(animals.get(animals.size() - 1));
                }
            });
            gridPane.add(iv, position.getX() + 1,
                    map.getUpperRight().getY() + 1 - position.getY(), 1, 1);
        });
    }

//    private void drawSelectedByGenome(GridPane gridPane, WorldMap map) {
//        if (gridPane.getChildren().size() > netArrayLength) {
//            gridPane.getChildren().remove(netArrayLength, gridPane.getChildren().size());
//        }
//
//        Set<Vector2d> grassKeySet = map.getGrassHashMap().keySet();
//        Set<Vector2d> animalKeySet = map.getAnimalHashMap().keySet();
//
//        animalKeySet.forEach(position -> {
//            ArrayList<Animal> animals = map.getAnimalHashMap().get(position);
//            int energyPercent = animals.get(animals.size() - 1).getEnergyPercent();
//            ImageView iv = guiElementBox.getAnimalImg(energyPercent);
//
//            gridPane.add(iv, position.getX() + 1,
//                    map.getUpperRight().getY() + 1 - position.getY(), 1, 1);
//        });
//    }

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
        for (int x = 1; x <= map.getUpperRight().getX() + 1; x++) {
            for (int y = map.getUpperRight().getY(), j = 1; y >= 0; y--, j++) {
                Rectangle rect = new Rectangle(0, 0, NET_SIZE, NET_SIZE);
                rect.setStroke(Color.BLACK);
                if (map.isJungle(new Vector2d(x - 1, y))) {
                    rect.setFill(Color.web("#66ff99"));
                } else {
                    rect.setFill(Color.web("#ccff99"));
                }
                gridPane.add(rect, x, j, 1, 1);

            }
        }
        netArrayLength = gridPane.getChildren().size();
    }

    public void startSim1Btn() {
        if (simulationInterface.sim1Engine.isWorking()) {
            simulationInterface.sim1Engine.setWorking(false);
        } else {
            simulationInterface.startEngine1();
        }
    }

    public void startSim2Btn() {
        if (simulationInterface.sim2Engine.isWorking()) {
            simulationInterface.sim2Engine.setWorking(false);
        } else {
            simulationInterface.startEngine2();
        }
    }

    private void onSelectedAnimalSim1(Animal animal) {
        selectedAnimalSim1 = animal;
        isAnimalSim1Selected = true;
        AnimalGenomeSim1.setText(selectedAnimalSim1.getGenome().toString());
    }

    private void onSelectedAnimalSim2(Animal animal) {
        selectedAnimalSim2 = animal;
        isAnimalSim2Selected = true;
        AnimalGenomeSim2.setText(selectedAnimalSim2.getGenome().toString());
    }

    public void unselectAnimalSim1Btn() {
        isAnimalSim1Selected = false;
        AnimalGenomeSim1.setText("");
    }

    public void unselectAnimalSim2Btn() {
        isAnimalSim2Selected = false;
        AnimalGenomeSim2.setText("");
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

    @Override
    public void statisticsUpdated(boolean isWrapped) {
        if (isWrapped) {
            Platform.runLater(() -> {
                stats.drawStatisticsSim2(sim2Map, deadAnimalsSim2, dominantGenomeSim2, magicalEventsSim2);
            });
        } else {
            Platform.runLater(() -> {
                stats.drawStatisticsSim1(sim1Map, deadAnimalsSim1, dominantGenomeSim1, magicalEventsSim1);
            });
        }
    }
}
