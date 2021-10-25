package agh.ics.oop;

import java.util.Objects;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;

    public Animal() {
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "orientation=" + orientation +
                ", position=" + position.toString() +
                '}';
    }

    public void move(MoveDirection direction) {
        if (direction == null) {
            return;
        }
        Vector2d downLeft = new Vector2d(0, 0);
        Vector2d upRight = new Vector2d(4, 4);
        Vector2d newPosition;
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                newPosition = position.add(Objects.requireNonNull(orientation.toUnitVector()));
                if (newPosition != null && newPosition.precedes(upRight) && newPosition.follows(downLeft)) {
                    position = newPosition;
                }
                break;
            case BACKWARD:
                orientation = Objects.requireNonNull(orientation.next()).next();
                newPosition = position.add(Objects.requireNonNull(
                        Objects.requireNonNull(orientation).toUnitVector()));
                if (newPosition != null && newPosition.precedes(upRight) && newPosition.follows(downLeft)) {
                    position = newPosition;
                }
                break;
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }
}
