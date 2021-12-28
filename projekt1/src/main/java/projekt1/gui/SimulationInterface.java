package projekt1.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projekt1.gui.controller.SimulationController;
import projekt1.simulation.engine.AbstractEngine;
import projekt1.simulation.engine.MagicalEngine;
import projekt1.simulation.engine.NormalEngine;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.Config;

import java.io.IOException;

public class SimulationInterface implements IGui {
    private final Config config;
    private WorldMap sim1Map;
    public AbstractEngine sim1Engine;
    private WorldMap sim2Map;
    public AbstractEngine sim2Engine;

    public SimulationInterface(Config config) {
        this.config = config;
        this.initializeSimulation();
    }

    private void initializeSimulation() {
        // notWrapped
        Vector2d upperRight = new Vector2d(config.getWidthX() - 1, config.getHeightY() - 1);
        int jungleX = (upperRight.getX() / config.getJungleRatio());
        int jungleY = (upperRight.getY() / config.getJungleRatio());
        Vector2d jungleLowerLeft = new Vector2d(
                (upperRight.getX() / 2) - jungleX / 2,
                (upperRight.getY() / 2) - jungleY / 2
        );
        Vector2d jungleUpperRight = new Vector2d(
                jungleLowerLeft.getX() + jungleX,
                jungleLowerLeft.getY() + jungleY
        );
        // Create Simulation 1 (Not Wrapped)
        sim1Map = new WorldMap(upperRight, jungleLowerLeft, jungleUpperRight, false);
        if (config.isSim1Magical()) {
            sim1Engine = new MagicalEngine(sim1Map, config.getStartNumOfAnimals(), config.getStartEnergy(),
                    config.getMoveEnergy(), config.getPlantEnergy(), config.isSim1Magical());
        } else {
            sim1Engine = new NormalEngine(sim1Map, config.getStartNumOfAnimals(), config.getStartEnergy(),
                    config.getMoveEnergy(), config.getPlantEnergy(), config.isSim1Magical());
        }
        // Create Simulation 2 (Wrapped)
        sim2Map = new WorldMap(upperRight, jungleLowerLeft, jungleUpperRight, true);
        if (config.isSim2Magical()) {
            sim2Engine = new MagicalEngine(sim2Map, config.getStartNumOfAnimals(), config.getStartEnergy(),
                    config.getMoveEnergy(), config.getPlantEnergy(), config.isSim2Magical());
        } else {
            sim2Engine = new NormalEngine(sim2Map, config.getStartNumOfAnimals(), config.getStartEnergy(),
                    config.getMoveEnergy(), config.getPlantEnergy(), config.isSim2Magical());
        }
    }

    @Override
    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
        SimulationController simulationController = new SimulationController(this, sim1Map, sim2Map);
        sim1Engine.addEraEndedObserver(simulationController);
        sim2Engine.addEraEndedObserver(simulationController);
        sim1Engine.addStatisticsUpdatedObserver(simulationController);
        sim2Engine.addStatisticsUpdatedObserver(simulationController);
        loader.setController(simulationController);
        Parent root = loader.load();
        simulationController.draw();
        Scene scene = new Scene(root);
        Stage simulationStage = new Stage();
        simulationStage.setOnCloseRequest(c -> {
            sim1Engine.setWorking(false);
            sim2Engine.setWorking(false);
        });
        simulationStage.setScene(scene);
        simulationStage.show();
    }

    public boolean startEngine1() {
        sim1Engine.setWorking(true);
        Thread engineThread1 = new Thread(sim1Engine);
        engineThread1.start();
        return true;
    }

    public boolean startEngine2() {
        sim2Engine.setWorking(true);
        Thread engineThread2 = new Thread(sim2Engine);
        engineThread2.start();
        return true;
    }
}
