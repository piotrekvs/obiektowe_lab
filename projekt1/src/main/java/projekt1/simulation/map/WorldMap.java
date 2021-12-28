package projekt1.simulation.map;

import projekt1.simulation.elements.*;

import java.util.*;

public class WorldMap {
    private final WorldStatistics worldStatistics = new WorldStatistics();
    private static final Random rand = new Random();
    private final boolean isWrapped;
    protected final int placingAttempts;
    protected final Vector2d lowerLeft = new Vector2d(0, 0);
    protected final Vector2d upperRight;
    protected final Vector2d jungleLowerLeft;
    protected final Vector2d jungleUpperRight;
    protected HashMap<Vector2d, Grass> grassHashMap = new HashMap<>();
    protected HashMap<Vector2d, ArrayList<Animal>> animalHashMap = new HashMap<>();

    public WorldMap(Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight, boolean isWrapped) {
        this.upperRight = upperRight;
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
        this.placingAttempts = upperRight.getX() + upperRight.getY();
        this.isWrapped = isWrapped;
    }

    public void placeGrassInJungle(int energy) {
        Vector2d newPosition;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return;
            newPosition = new Vector2d(jungleLowerLeft.getX() + rand.nextInt(jungleUpperRight.getX() - jungleLowerLeft.getX() + 1), jungleLowerLeft.getY() + rand.nextInt(jungleUpperRight.getY() - jungleLowerLeft.getY() + 1));
            counter++;
        } while (isOccupied(newPosition));
        grassHashMap.put(newPosition, new Grass(newPosition, energy));
    }

    public void placeGrassNotInJungle(int energy) {
        Vector2d newPosition;
        boolean isJungle;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return;
            newPosition = new Vector2d(rand.nextInt(upperRight.getX() + 1), rand.nextInt(upperRight.getY() + 1));
            if (isJungle(newPosition)) {
                isJungle = true;
            } else {
                counter++;
                isJungle = false;
            }
        } while (isOccupied(newPosition) || isJungle);
        grassHashMap.put(newPosition, new Grass(newPosition, energy));
    }

    public Animal placeAnimalRandomly(int energy) {
        Vector2d position;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return null;
            counter++;
            position = new Vector2d(rand.nextInt(upperRight.getX() + 1), rand.nextInt(upperRight.getY() + 1));
        } while (isOccupied(position));
        return placeAnimal(new Animal(this, position, energy, rand));
    }

    public Animal placeAnimalRandomlyMagicalEvent(int startEnergy, Genome genome) {
        Vector2d position;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return null;
            counter++;
            position = new Vector2d(rand.nextInt(upperRight.getX() + 1), rand.nextInt(upperRight.getY() + 1));
        } while (isOccupied(position));
        return placeAnimal(new Animal(this, position, startEnergy, startEnergy, rand, genome));
    }

    public Animal placeAnimal(Animal animal) {
        ArrayList<Animal> array;
        Vector2d position = animal.getPosition();
        if (animalHashMap.containsKey(position)) {
            array = animalHashMap.get(position);
            array.add(animal);
            array.sort(new AnimalEnergyComperator());
        } else {
            array = new ArrayList<Animal>();
            array.add(animal);
            animalHashMap.put(position, array);
        }
        return animal;
    }

    public int countHighestEnergyAnimals(Vector2d position) {
        ArrayList<Animal> animals = animalHashMap.get(position);
        int length = animals.size();
        int highestEnergy = animals.get(length - 1).getEnergy();
        int counter = 1;
        for (int i = length - 2; i >= 0; i--) {
            if (animals.get(i).getEnergy() == highestEnergy) counter++;
            else return counter;
        }
        return counter;
    }

    public Vector2d canMoveTo(Vector2d position, Vector2d newPosition) {
        if (isWrapped) {
            if (newPosition.stronglyPrecedesIn1d(lowerLeft)) {
                return new Vector2d((upperRight.getX() + 1 + newPosition.getX()) % (upperRight.getX() + 1), (upperRight.getY() + 1 + newPosition.getY()) % (upperRight.getY() + 1));
            } else {
                return new Vector2d(lowerLeft.getX() + (newPosition.getX() % (upperRight.getX() + 1)), lowerLeft.getY() + (newPosition.getY() % (upperRight.getY() + 1)));
            }
        } else {
            if (newPosition.stronglyPrecedesIn1d(lowerLeft)) return position;
            if (newPosition.stronglyFollowsIn1d(upperRight)) return position;
        }
        return newPosition;
    }

//
//
//
//
//
//  DAY CYCLE METHODS:

    public void deleteDeadAnimals() {
        Iterator<Vector2d> it = animalHashMap.keySet().iterator();
        while (it.hasNext()) {
            Vector2d key = it.next();
            ArrayList<Animal> animals = animalHashMap.get(key);
            while (!animals.isEmpty() && animals.get(0).getEnergy() <= 0) {
                worldStatistics.addDeadAnimal(animals.get(0).getLifespan());
                animals.remove(0);
            }
            if (animals.isEmpty()) it.remove();
        }
    }

    public void makeMoves(int moveEnergy) {
        Object[] keys = animalHashMap.keySet().toArray();
        for (Object key : keys) {
            ArrayList<Animal> animals = animalHashMap.get((Vector2d) key);
            Iterator<Animal> it = animals.iterator();
            while (it.hasNext()) {
                Animal animal = it.next();
                if (animal.move(moveEnergy)) {
                    it.remove();
                    placeAnimal(animal);
                }
            }
            if (animals.isEmpty()) {
                animalHashMap.remove(key);
            }
        }
    }

    public void eatGrass() {
        Iterator<Vector2d> it = grassHashMap.keySet().iterator();
        while (it.hasNext()) {
            Vector2d key = it.next();
            if (isAnimal(key)) {
                ArrayList<Animal> animals = animalHashMap.get(key);
                int highestEnergyAnimals = countHighestEnergyAnimals(key);
                int length = animals.size();
                int energyAddition = grassHashMap.get(key).getEnergy() / highestEnergyAnimals;
                for (int i = length - 1; i >= length - highestEnergyAnimals; i--) {
                    animals.get(i).addEnergy(energyAddition);
                }
                it.remove();
            }
        }
    }

    public void animalsMultiplication(int startEnergy) {
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
                if (rand.nextInt(2) == 0) {
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
                Animal animal = new Animal(this, position, parent1.getEnergy() / 4 + parent2.getEnergy() / 4,
                        startEnergy, rand, new Genome(left, right));
                placeAnimal(animal);
                parent1.addEnergy(-parent1.getEnergy()/4);
                parent2.addEnergy(-parent2.getEnergy()/4);
                parent1.newChild();
                parent2.newChild();
            }
        });
    }

//
//
//
//
//
//  GETTERS AND SETTERS

    public boolean isOccupied(Vector2d position) {
        return (isGrass(position) || isAnimal(position));
    }

    public boolean isGrass(Vector2d position) {
        return grassHashMap.containsKey(position);
    }

    public boolean isAnimal(Vector2d position) {
        return animalHashMap.containsKey(position);
    }

    public boolean isJungle(Vector2d position) {
        return position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight);
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public boolean isWrapped() {
        return isWrapped;
    }

    public HashMap<Vector2d, Grass> getGrassHashMap() {
        return grassHashMap;
    }

    public HashMap<Vector2d, ArrayList<Animal>> getAnimalHashMap() {
        return animalHashMap;
    }

    public WorldStatistics getWorldStatistics() {
        return worldStatistics;
    }
}
