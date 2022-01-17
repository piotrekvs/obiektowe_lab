package projekt1.simulation.engine;

import projekt1.simulation.map.WorldMap;

public class NormalEngine extends AbstractEngine {  // czy jest sens trzymania klasy abstrakcyjnej, skoro jeden z potomków nic nie wnosi?
    public NormalEngine(WorldMap map, int startNumOfAnimals, int startEnergy, int moveEnergy, int plantEnergy, boolean isMagical) {
        super(map, startNumOfAnimals, startEnergy, moveEnergy, plantEnergy, isMagical);
    }
}
