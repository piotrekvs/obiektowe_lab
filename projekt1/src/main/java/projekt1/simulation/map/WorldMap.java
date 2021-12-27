package projekt1.simulation.map;

import projekt1.simulation.elements.Animal;
import projekt1.simulation.elements.AnimalEnergyComperator;
import projekt1.simulation.elements.Grass;
import projekt1.simulation.elements.IMapElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldMap {
    private static final Random rand = new Random();
    private final boolean isWrapped;
    protected final int placingAttempts;
    protected final Vector2d lowerLeft = new Vector2d(0, 0);
    protected final Vector2d upperRight;
    protected final Vector2d jungleLowerLeft;
    protected final Vector2d jungleUpperRight;
    protected HashMap<Vector2d, IMapElement> grassHashMap = new HashMap<>();
    protected HashMap<Vector2d, ArrayList<Animal>> animalHashMap = new HashMap<>();

    public WorldMap(Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight, boolean isWrapped) {
        this.upperRight = upperRight;
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
        this.placingAttempts = upperRight.getX() + upperRight.getY();
        this.isWrapped = isWrapped;
    }

    public boolean placeGrassInJungle(int energy) {
        Vector2d newPosition;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return false;
            newPosition = new Vector2d(
                    jungleLowerLeft.getX() + rand.nextInt(jungleUpperRight.getX() - jungleLowerLeft.getX() + 1),
                    jungleLowerLeft.getY() + rand.nextInt(jungleUpperRight.getY() - jungleLowerLeft.getY() + 1)
            );
            counter++;
        } while (isOccupied(newPosition));
        System.out.println(newPosition);
        grassHashMap.put(newPosition, new Grass(newPosition, energy));
        return true;
    }

    public boolean placeGrassNotInJungle(int energy) {
        Vector2d newPosition;
        boolean isJungle;
        int counter = 0;
        do {
            if (counter >= placingAttempts) return false;
            newPosition = new Vector2d(rand.nextInt(upperRight.getX() + 1), rand.nextInt(upperRight.getY() + 1));
            if (isJungle(newPosition)) {
                isJungle = true;
            } else {
                counter++;
                isJungle = false;
            }
        } while (isOccupied(newPosition) || isJungle);
        grassHashMap.put(newPosition, new Grass(newPosition, energy));
        return true;
    }

    public void removeGrass(Vector2d position) {
        grassHashMap.remove(position);
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

    public void removeAnimal(Vector2d position, Animal animal) {
        if (animalHashMap.containsKey(position)) {
            ArrayList<Animal> array = animalHashMap.get(position);
            array.remove(animal);
            if (array.size() == 0) {
                animalHashMap.remove(position);
            }
        }
    }

    public Vector2d moveTo(Vector2d position) {
        Vector2d newPosition;
        if (isWrapped) {
            if (position.precedesIn1d(lowerLeft)) {
                return new Vector2d(
                        (upperRight.getX() + 1 + position.getX()) % (upperRight.getX() + 1),
                        (upperRight.getY() + 1 + position.getY()) % (upperRight.getY() + 1)
                );
            } else {
                return new Vector2d(
                        lowerLeft.getX() + (position.getX() % (upperRight.getX() + 1)),
                        lowerLeft.getY() + (position.getY() % (upperRight.getY() + 1))
                );
            }
        } else {
            if (position.precedesIn1d(lowerLeft)) return position.upperRight(lowerLeft);
            if (position.followsIn1d(upperRight)) return position.lowerLeft(upperRight);
        }
        return position;
    }

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

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public Vector2d getJungleLowerLeft() {
        return jungleLowerLeft;
    }

    public Vector2d getJungleUpperRight() {
        return jungleUpperRight;
    }

    public HashMap<Vector2d, IMapElement> getGrassHashMap() {
        return grassHashMap;
    }

    public HashMap<Vector2d, ArrayList<Animal>> getAnimalHashMap() {
        return animalHashMap;
    }
}
