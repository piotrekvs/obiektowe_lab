package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private static final Random rand = new Random();
    private final int furthestGrassXY;
    private final List<Grass> grassFields = new ArrayList<>();

    public GrassField(int numOfGrassFields) {
        super(new Vector2d(Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1));
        furthestGrassXY = (int) Math.sqrt(numOfGrassFields * 10);
        placeGrass(numOfGrassFields);
    }

    private void placeGrass(int numOfGrassFields) {
        for (int i = 0; i < numOfGrassFields; ) {
            Vector2d position = new Vector2d(rand.nextInt(furthestGrassXY), rand.nextInt(furthestGrassXY));
            if (!isOccupied(position)) {
                grassFields.add(new Grass(position));
                i++;
            }
        }
    }

    /*private boolean isGrassAlreadyThere(Vector2d position) {
        return grassAt(position) != null;
    }*/

    private Grass grassAt(Vector2d position) {
        for (Grass grass : grassFields) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) &&
                position.precedes(upperRight) &&
                (super.objectAt(position) == null);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if (object != null) {
            return object;
        }
        object = grassAt(position);
        return object;
    }

    @Override
    public String toString() {
        Vector2d upRight = new Vector2d(0, 0);
        for (Grass grass : grassFields) {
            upRight = grass.getPosition().upperRight(upRight);
        }
        for (Animal animal : animals) {
            upRight = animal.getPosition().upperRight(upRight);
        }
        upRight = upRight.add(new Vector2d(1, 1));
        return super.mapVisualizer.draw(lowerLeft, upRight);
    }
}
