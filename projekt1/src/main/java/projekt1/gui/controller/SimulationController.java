package projekt1.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
    // Charts1:
    private final XYChart.Series<Number, Number> animalsAliveDataSim1 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> plantsAliveDataSim1 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgEnergyLvlDataSim1 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgChildrenNumDataSim1 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgLifespanNumDataSim1 = new XYChart.Series<Number, Number>();
    // Charts2:
    private final XYChart.Series<Number, Number> animalsAliveDataSim2 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> plantsAliveDataSim2 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgEnergyLvlDataSim2 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgChildrenNumDataSim2 = new XYChart.Series<Number, Number>();
    private final XYChart.Series<Number, Number> avgLifespanNumDataSim2 = new XYChart.Series<Number, Number>();


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
        // Chart animalsAliveDataSim1
        animalsAliveDataSim1.setName("Animals");
        plantsAliveDataSim1.setName("Plants");
        LivingAnimAndPlantsChartSim1.getData().add(animalsAliveDataSim1);
        LivingAnimAndPlantsChartSim1.getData().add(plantsAliveDataSim1);
        // Chart avgEnergyLvlDataSim1
        avgEnergyLvlDataSim1.setName("avgEnergy");
        EnergyChartSim1.getData().add(avgEnergyLvlDataSim1);
        // Chart ChildrenChartSim1
        avgChildrenNumDataSim1.setName("avgChildrenNum");
        ChildrenChartSim1.getData().add(avgChildrenNumDataSim1);
        // Chart LifespanChartSim1
        avgLifespanNumDataSim1.setName("avgLifespan");
        lifespanChartSim1.getData().add(avgLifespanNumDataSim1);
        // Charts Sim2
        // Chart animalsAliveDataSim2
        animalsAliveDataSim2.setName("Animals");
        plantsAliveDataSim2.setName("Plants");
        LivingAnimAndPlantsChartSim2.getData().add(animalsAliveDataSim2);
        LivingAnimAndPlantsChartSim2.getData().add(plantsAliveDataSim2);
        // Chart avgEnergyLvlDataSim2
        avgEnergyLvlDataSim2.setName("avgEnergy");
        EnergyChartSim2.getData().add(avgEnergyLvlDataSim2);
        // Chart ChildrenChartSim2
        avgChildrenNumDataSim2.setName("avgChildrenNum");
        ChildrenChartSim2.getData().add(avgChildrenNumDataSim2);
        // Chart LifespanChartSim2
        avgLifespanNumDataSim2.setName("avgLifespan");
        lifespanChartSim2.getData().add(avgLifespanNumDataSim2);
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

    public void drawStatisticsSim1() {
        animalsAliveDataSim1.getData().add(new XYChart.Data<Number, Number>(
                sim1Map.getWorldStatistics().getCurrentDay(), sim1Map.getWorldStatistics().getAliveAnimals()
        ));
        plantsAliveDataSim1.getData().add(new XYChart.Data<Number, Number>(
                sim1Map.getWorldStatistics().getCurrentDay(), sim1Map.getWorldStatistics().getNumOfGrass()
        ));
        avgEnergyLvlDataSim1.getData().add(new XYChart.Data<Number, Number>(
                sim1Map.getWorldStatistics().getCurrentDay(), sim1Map.getWorldStatistics().getAvgEnergyLvl()
        ));
        avgChildrenNumDataSim1.getData().add(new XYChart.Data<Number, Number>(
                sim1Map.getWorldStatistics().getCurrentDay(), sim1Map.getWorldStatistics().getAvgChildrenNum()
        ));
        avgLifespanNumDataSim1.getData().add(new XYChart.Data<Number, Number>(
                sim1Map.getWorldStatistics().getCurrentDay(), sim1Map.getWorldStatistics().getAvgLifespanOfDeadAnimals()
        ));
        magicalEventsSim1.setText(Integer.toString(sim1Map.getWorldStatistics().getMagicalEvents()));
        deadAnimalsSim1.setText(Integer.toString(sim1Map.getWorldStatistics().getDeadAnimals()));
        dominantGenomeSim1.setText(sim1Map.getWorldStatistics().getDominantGenome().toString());
    }

    public void drawStatisticsSim2() {
        animalsAliveDataSim2.getData().add(new XYChart.Data<Number, Number>(
                sim2Map.getWorldStatistics().getCurrentDay(), sim2Map.getWorldStatistics().getAliveAnimals()
        ));
        plantsAliveDataSim2.getData().add(new XYChart.Data<Number, Number>(
                sim2Map.getWorldStatistics().getCurrentDay(), sim2Map.getWorldStatistics().getNumOfGrass()
        ));
        avgEnergyLvlDataSim2.getData().add(new XYChart.Data<Number, Number>(
                sim2Map.getWorldStatistics().getCurrentDay(), sim2Map.getWorldStatistics().getAvgEnergyLvl()
        ));
        avgChildrenNumDataSim2.getData().add(new XYChart.Data<Number, Number>(
                sim2Map.getWorldStatistics().getCurrentDay(), sim2Map.getWorldStatistics().getAvgChildrenNum()
        ));
        avgLifespanNumDataSim2.getData().add(new XYChart.Data<Number, Number>(
                sim2Map.getWorldStatistics().getCurrentDay(), sim2Map.getWorldStatistics().getAvgLifespanOfDeadAnimals()
        ));
        magicalEventsSim2.setText(Integer.toString(sim2Map.getWorldStatistics().getMagicalEvents()));
        deadAnimalsSim2.setText(Integer.toString(sim2Map.getWorldStatistics().getDeadAnimals()));
        dominantGenomeSim2.setText(sim2Map.getWorldStatistics().getDominantGenome().toString());
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
                drawStatisticsSim2();
            });
        } else {
            Platform.runLater(() -> {
                drawStatisticsSim1();
            });
        }
    }
}
