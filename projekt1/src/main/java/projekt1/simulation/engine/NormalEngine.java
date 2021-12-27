package projekt1.simulation.engine;

import projekt1.simulation.elements.Animal;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.IEraEndedObserver;

import java.util.*;

public class NormalEngine implements IEngine {
    private final ArrayList<IEraEndedObserver> observers = new ArrayList<>();
    private final Random random = new Random();
    private final WorldMap map;
    private final int moveDelay = 500;
    private final int startNumOfAnimals;
    private final int startEnergy;
    private final int moveEnergy;
    private final int plantEnergy;

    public NormalEngine(WorldMap map, int startNumOfAnimals,
                        int startEnergy, int moveEnergy, int plantEnergy) {
        this.map = map;
        this.startNumOfAnimals = startNumOfAnimals;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
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
        while(true) {
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap<Vector2d, ArrayList<Animal>> animalHashMap = map.getAnimalHashMap();
            deleteDeadAnimals(animalHashMap);

        }
    }

    private void deleteDeadAnimals(HashMap<Vector2d, ArrayList<Animal>> animalHashMap) {
        Iterator<Vector2d> it = animalHashMap.keySet().iterator();
        while (it.hasNext()) {
            Vector2d key = it.next();
            ArrayList<Animal> animals = animalHashMap.get(key);
            while (!animals.isEmpty() && animals.get(0).getEnergy() <= 0) {
                animals.remove(0);
            }
            if (animals.isEmpty()) it.remove();
        }
    }

    private void makeMoves(HashMap<Vector2d, ArrayList<Animal>> animalHashMap) {
        Vector2d[] keys = (Vector2d[]) animalHashMap.keySet().toArray();
        for (Vector2d key: keys) {
            ArrayList<Animal> animals = animalHashMap.get(key);
            Iterator<Animal> it = animals.iterator();
            while (it.hasNext()) {
                Animal animal = it.next();
                if (animal.move()) {
                    it.remove();
                    map.placeAnimal(animal);
                }
            }
        }
    }

    public void addObserver(IEraEndedObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IEraEndedObserver observer) {
        observers.remove(observer);
    }

    protected void positionChangedNotify() {
        for (IEraEndedObserver observer : observers) {
            observer.eraEndedSim1();
        }
    }
}
