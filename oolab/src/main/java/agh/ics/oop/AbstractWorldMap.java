package agh.ics.oop;

import java.util.HashMap;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected Vector2d lowerLeftDraw = new Vector2d(0, 0);
    protected Vector2d upperRightDraw = new Vector2d(0, 0);
    protected HashMap<Vector2d, IMapElement> mapElementsHashMap = new HashMap<>();

    public AbstractWorldMap(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            mapElementsHashMap.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Cannot place animal at position: " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        return mapElementsHashMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement mapElement = objectAt(oldPosition);
        mapElementsHashMap.remove(oldPosition);
        mapElementsHashMap.put(newPosition, mapElement);
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(lowerLeftDraw, upperRightDraw);
    }
}