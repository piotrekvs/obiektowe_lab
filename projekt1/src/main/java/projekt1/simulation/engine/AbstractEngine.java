package projekt1.simulation.engine;

import projekt1.simulation.elements.Animal;
import projekt1.simulation.elements.Genome;
import projekt1.simulation.elements.Grass;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.IEraEndedObserver;
import projekt1.utils.IStatisticsUpdateObserver;

import java.util.*;

public class AbstractEngine implements IEngine, Runnable {
    protected final int statsObserverUpdateDelta = 10;
    protected int lastStatsUpdate = -10;
    protected final ArrayList<IEraEndedObserver> eraEndedObservers = new ArrayList<>();
    protected final ArrayList<IStatisticsUpdateObserver> statisticsUpdatedObservers = new ArrayList<>();
    protected final Random random = new Random();
    protected final WorldMap map;
    protected final int moveDelay = 50;
    protected final int startNumOfAnimals;
    protected final int startEnergy;
    protected final int moveEnergy;
    protected final int plantEnergy;
    protected final boolean isMagical;
    protected boolean isWorking = false;

    public AbstractEngine(WorldMap map, int startNumOfAnimals,
                          int startEnergy, int moveEnergy, int plantEnergy, boolean isMagical) {
        this.map = map;
        this.map.getWorldStatistics().setAliveAnimals(startNumOfAnimals);
        this.startNumOfAnimals = startNumOfAnimals;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.isMagical = isMagical;
        this.initializeSimulation();
    }

    public void initializeSimulation() {
        for (int i = 0; i < startNumOfAnimals; i++) {
            map.placeAnimalRandomly(startEnergy);
        }
        map.placeGrassInJungle(plantEnergy);
        map.placeGrassNotInJungle(plantEnergy);
    }

    @Override
    public void run() {
        while (isWorking) {
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            map.deleteDeadAnimals();
            map.makeMoves(moveEnergy);
            map.eatGrass();
            map.animalsMultiplication(startEnergy);
            // Placing grass on the map
            map.placeGrassInJungle(plantEnergy);
            map.placeGrassNotInJungle(plantEnergy);
            updateStatistics();
            eraEndedNotify();
        }
    }

    public void updateStatistics() {
        // DeadAnimals & AverageLifespan updated in map.deleteDeadAnimals();
        // AnimalChildren updated in map.animalsMultiplication(startEnergy);
        // MagicalEvents updated in subclass MagicalEngine
        // GRASS:
        map.getWorldStatistics().setNumOfGrass(map.getGrassHashMap().size());
        // Init counters:
        HashMap<Genome, Integer> dominantGenomeHashMap = new HashMap<>();
        int numOfAliveAnimals = 0;
        int sumOfEnergy = 0;
        int sumOfChildren = 0;
        for (Vector2d key : map.getAnimalHashMap().keySet()) {
            ArrayList<Animal> animals = map.getAnimalHashMap().get(key);
            for (Animal animal : animals) {
                numOfAliveAnimals++;
                sumOfEnergy += animal.getEnergy();
                sumOfChildren += animal.getChildren();
                int a = 1;
                if (dominantGenomeHashMap.containsKey(animal.getGenome())) {
                    a += dominantGenomeHashMap.get(animal.getGenome());
                }
                dominantGenomeHashMap.put(animal.getGenome(), a);
                animal.nextDay();
            }
        }
        map.getWorldStatistics().setAliveAnimals(numOfAliveAnimals);
        map.getWorldStatistics().setAvgEnergyLvl(sumOfEnergy / numOfAliveAnimals);
        map.getWorldStatistics().setAvgChildrenNum(sumOfChildren / numOfAliveAnimals);


        // Set Dominant Genome
        Genome dominantGenome = null;
        int max = 0;
        for (Genome genome : dominantGenomeHashMap.keySet()) {
            if (dominantGenomeHashMap.get(genome) > max) {
                dominantGenome = genome;
                max = dominantGenomeHashMap.get(genome);
            }
        }
        if (dominantGenome != null) {
            map.getWorldStatistics().setDominantGenome(dominantGenome);
        } else {
            map.getWorldStatistics().setDominantGenome(new Genome(new int[]{0}, new int[]{0}));
        }

        map.getWorldStatistics().nextDay();
        if (map.getWorldStatistics().getCurrentDay() - lastStatsUpdate > statsObserverUpdateDelta) {
            lastStatsUpdate = map.getWorldStatistics().getCurrentDay();
            StatisticsUpdatedNotify();
        }
    }


    public void addEraEndedObserver(IEraEndedObserver observer) {
        eraEndedObservers.add(observer);
    }

    protected void eraEndedNotify() {
        for (IEraEndedObserver observer : eraEndedObservers) {
            observer.eraEnded(map.isWrapped());
        }
    }

    public void addStatisticsUpdatedObserver(IStatisticsUpdateObserver observer) {
        statisticsUpdatedObservers.add(observer);
    }

    protected void StatisticsUpdatedNotify() {
        for (IStatisticsUpdateObserver observer : statisticsUpdatedObservers) {
            observer.statisticsUpdated(map.isWrapped());
        }
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
