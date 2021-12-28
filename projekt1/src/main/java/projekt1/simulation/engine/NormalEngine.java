package projekt1.simulation.engine;

import projekt1.simulation.map.WorldMap;

public class NormalEngine extends AbstractEngine {
    public NormalEngine(WorldMap map, int startNumOfAnimals, int startEnergy, int moveEnergy, int plantEnergy, boolean isMagical) {
        super(map, startNumOfAnimals, startEnergy, moveEnergy, plantEnergy, isMagical);
    }
}
