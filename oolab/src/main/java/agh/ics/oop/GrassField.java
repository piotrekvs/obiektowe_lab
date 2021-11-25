package agh.ics.oop;

import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private static final Random rand = new Random();
    private final int furthestGrassXY;

    public GrassField(int numOfGrassFields) {
        super(new Vector2d(Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 1));
        furthestGrassXY = (int) Math.sqrt(numOfGrassFields * 10);
        placeGrass(numOfGrassFields);
    }

    private void placeGrass(int numOfGrassFields) {
        for (int i = 0; i < numOfGrassFields; ) {
            Vector2d position = new Vector2d(rand.nextInt(furthestGrassXY), rand.nextInt(furthestGrassXY));
            if (!isOccupied(position)) {
                mapElementsHashMap.put(position, new Grass(position));
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
        return super.place(animal);
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
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        eatGrass(newPosition);
        super.positionChanged(oldPosition, newPosition);
    }

    @Override
    public String toString() {
        Vector2d upRight = new Vector2d(0, 0);
        for (Vector2d key : mapElementsHashMap.keySet()) {
            upRight = key.upperRight(upRight);
        }
        upRight = upRight.add(new Vector2d(1, 1));
        return super.mapVisualizer.draw(lowerLeft, upRight);
    }
}
