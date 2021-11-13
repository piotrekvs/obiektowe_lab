package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
    protected final Vector2d upperRight;
    protected final Vector2d lowerLeft = new Vector2d(0, 0);
    protected List<Animal> animals = new ArrayList<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
    }



    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            return animals.add(animal);
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}