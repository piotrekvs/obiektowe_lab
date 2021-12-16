package agh.ics.oop;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
    private final IWorldMap map;
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    @Override
    public String toString() {
        final String[] names = new String[]{
                "N", "E", "S", "W"
        };
        return names[orientation.ordinal()];
    }

    public void move(MoveDirection direction) {
        MapDirection moveOrientation = orientation;
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                positionChangedNotify(position, position);
                break;
            case LEFT:
                orientation = orientation.previous();
                positionChangedNotify(position, position);
                break;
            case BACKWARD:
                moveOrientation = orientation.next().next();
            case FORWARD:
                Vector2d newPosition = position.add(moveOrientation.toUnitVector());
                Vector2d oldPosition = position;
                if (map.canMoveTo(newPosition)) {
                    position = newPosition;
                    positionChangedNotify(oldPosition, newPosition);
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

    @Override
    public String getImage() {
        switch (orientation) {
            case NORTH:
                return "up";
            case EAST:
                return "right";
            case SOUTH:
                return "down";
            case WEST:
                return "left";
            default:
                throw new IllegalArgumentException("Wrong orientation");
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChangedNotify(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
