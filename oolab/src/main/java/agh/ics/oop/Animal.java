package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement{
    private final IWorldMap map;
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        addObserver((IPositionChangeObserver) map);
        this.position = initialPosition;
    }

    @Override
    public String toString() {
        String[] names = new String[]{
                "N", "E", "S", "W"
        };
        return names[orientation.ordinal()];
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case BACKWARD:
                orientation = orientation.next().next();
            case FORWARD:
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if (map.canMoveTo(newPosition)) {
                    positionChanged(position, newPosition);
                    position = newPosition;
                }
                break;
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
