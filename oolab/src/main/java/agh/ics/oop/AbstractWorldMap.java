package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Vector2d upperRight;
    protected final Vector2d lowerLeft = new Vector2d(0, 0);
    protected Map<Vector2d, IMapElement> mapElementsHashMap= new HashMap<>();
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
            mapElementsHashMap.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        return false;
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
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}