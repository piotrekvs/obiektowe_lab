package projekt1.simulation.engine;

import projekt1.simulation.elements.Animal;
import projekt1.simulation.elements.Grass;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;
import projekt1.utils.IEraEndedObserver;

import java.util.*;

public class NormalEngine implements IEngine, Runnable {
    private final ArrayList<IEraEndedObserver> observers = new ArrayList<>();
    private final Random random = new Random();
    private final WorldMap map;
    private final int moveDelay = 50;
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
        while (true) {
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap<Vector2d, ArrayList<Animal>> animalHashMap = map.getAnimalHashMap();
            HashMap<Vector2d, Grass> grassHashMap = map.getGrassHashMap();

            deleteDeadAnimals(animalHashMap);
            makeMoves(animalHashMap);
            eatGrass(animalHashMap, grassHashMap);
            animalsMultiplication(animalHashMap);
            // Placing grass on the map
            map.placeGrassInJungle(plantEnergy);
            map.placeGrassNotInJungle(plantEnergy);
            System.out.println("ERA END");
            eraEndedNotify();
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
        Object[] keys = animalHashMap.keySet().toArray();
        for (Object key : keys) {
            ArrayList<Animal> animals = animalHashMap.get(key);
            Iterator<Animal> it = animals.iterator();
            while (it.hasNext()) {
                Animal animal = it.next();
                if (animal.move(moveEnergy)) {
                    it.remove();
                    map.placeAnimal(animal);
                }
            }
            if (animals.isEmpty()) {
                animalHashMap.remove(key);
            }
        }
    }

    private void eatGrass(HashMap<Vector2d, ArrayList<Animal>> animalHashMap,
                          HashMap<Vector2d, Grass> grassHashMap) {
        Iterator<Vector2d> it = grassHashMap.keySet().iterator();
        while (it.hasNext()) {
            Vector2d key = it.next();
            if (map.isAnimal(key)) {
                ArrayList<Animal> animals = animalHashMap.get(key);
                int highestEnergyAnimals = map.countHighestEnergyAnimals(key);
                int length = animals.size();
                int energyAddition = grassHashMap.get(key).getEnergy() / highestEnergyAnimals;
                for (int i = length - 1; i >= length - highestEnergyAnimals; i--) {
                    animals.get(i).addEnergy(energyAddition);
                }
                it.remove();
            }
        }
    }

    private void animalsMultiplication(HashMap<Vector2d, ArrayList<Animal>> animalHashMap) {
        animalHashMap.keySet().forEach(position -> {
            ArrayList<Animal> animals = animalHashMap.get(position);
            int length = animals.size();
            if (length > 1 && animals.get(length - 1).getEnergy() > 4
                    && animals.get(length - 2).getEnergy() > 4) {
                Animal parent1 = animals.get(length - 1);
                Animal parent2 = animals.get(length - 2);
                int[] left;
                int[] right;
                int leftLength = parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                if (random.nextInt(2) == 0) {
                    left = Arrays.copyOfRange(parent1.getGenes(), 0, leftLength);
                    right = Arrays.copyOfRange(parent2.getGenes(), leftLength, parent2.getGenes().length);
                } else {
                    left = Arrays.copyOfRange(parent2.getGenes(),
                            0, parent2.getGenes().length - leftLength);
                    right = Arrays.copyOfRange(parent1.getGenes(),
                            parent2.getGenes().length - leftLength, parent2.getGenes().length);
                }
                if (left.length + right.length != 32) {
                    System.exit(1);
                }
                Animal animal = new Animal(map, position, parent1.getEnergy() / 4 + parent2.getEnergy() / 4,
                        startEnergy, random, left, right);
                map.placeAnimal(animal);
                parent1.addEnergy(-parent1.getEnergy()/4);
                parent2.addEnergy(-parent2.getEnergy()/4);
            }
        });
    }

    public void addObserver(IEraEndedObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IEraEndedObserver observer) {
        observers.remove(observer);
    }

    protected void eraEndedNotify() {
        for (IEraEndedObserver observer : observers) {
            observer.eraEnded(map.isWrapped());
        }
    }
}
