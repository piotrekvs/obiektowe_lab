package projekt1.gui.controller;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import projekt1.simulation.map.WorldMap;

public class StatisticsHelper {
    // Charts1:
    public final XYChart.Series<Number, Number> animalsAliveDataSim1 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> plantsAliveDataSim1 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgEnergyLvlDataSim1 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgChildrenNumDataSim1 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgLifespanNumDataSim1 = new XYChart.Series<Number, Number>();
    // Charts2:
    public final XYChart.Series<Number, Number> animalsAliveDataSim2 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> plantsAliveDataSim2 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgEnergyLvlDataSim2 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgChildrenNumDataSim2 = new XYChart.Series<Number, Number>();
    public final XYChart.Series<Number, Number> avgLifespanNumDataSim2 = new XYChart.Series<Number, Number>();

    public StatisticsHelper() {
        animalsAliveDataSim1.setName("Animals");
        plantsAliveDataSim1.setName("Plants");
        avgEnergyLvlDataSim1.setName("avgEnergy");
        avgChildrenNumDataSim1.setName("avgChildrenNum");
        avgLifespanNumDataSim1.setName("avgLifespan");
        animalsAliveDataSim2.setName("Animals");
        plantsAliveDataSim2.setName("Plants");
        avgEnergyLvlDataSim2.setName("avgEnergy");
        avgChildrenNumDataSim2.setName("avgChildrenNum");
        avgLifespanNumDataSim2.setName("avgLifespan");
    }

    public void drawStatisticsSim1(WorldMap sim1Map, Label deadAnimalsSim1, Label dominantGenomeSim1, Label magicalEventsSim1) {
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

    public void drawStatisticsSim2(WorldMap sim2Map, Label deadAnimalsSim2,Label dominantGenomeSim2, Label magicalEventsSim2) {
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
}
