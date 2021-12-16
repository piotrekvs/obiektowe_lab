package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap implements IPositionChangeObserver {
    private static final Random rand = new Random();
    private final MapBoundary mapBoundary;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private final int furthestGrassXY;

    public GrassField(int numOfGrassFields) {
        super(new Vector2d(Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 1),
                new Vector2d(Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1));
        furthestGrassXY = (int) Math.sqrt(numOfGrassFields * 10);
        this.mapBoundary = new MapBoundary(this);
        placeGrass(numOfGrassFields);
    }

    private void placeGrass(int numOfGrassFields) {
        for (int i = 0; i < numOfGrassFields; ) {
            Vector2d position = new Vector2d(rand.nextInt(furthestGrassXY), rand.nextInt(furthestGrassXY));
            if (!isOccupied(position)) {
                mapElementsHashMap.put(position, new Grass(position));
                mapBoundary.addElement(position);
                i++;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) &&
                (!isOccupied(position) || objectAt(position) instanceof Grass);
    }

    @Override
    public boolean place(Animal animal) {
        eatGrass(animal.getPosition());
        super.place(animal);
        mapBoundary.addElement(animal.getPosition());
        return true;
    }

    public void eatGrass(Vector2d position) {
        if (!(objectAt(position) instanceof Grass)) {
            return;
        }
        mapElementsHashMap.remove(position);
        Vector2d newPosition;
        do {
            newPosition = new Vector2d(rand.nextInt(furthestGrassXY), rand.nextInt(furthestGrassXY));
        } while (isOccupied(newPosition));
        mapElementsHashMap.put(newPosition, new Grass(newPosition));
        positionChangedNotify(position, newPosition);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        eatGrass(newPosition);
        super.positionChanged(oldPosition, newPosition);
        positionChangedNotify(oldPosition, newPosition);
    }

    public void setNewBoundaries() {
        upperRightDraw = mapBoundary.getUpperRight();
        lowerLeftDraw = mapBoundary.getLowerLeft();
    }
}
