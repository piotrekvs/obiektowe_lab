package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final GrassField map;
    private final TreeSet<Vector2d> orderedElementsX = new TreeSet<>(new CompareX());
    private final TreeSet<Vector2d> orderedElementsY = new TreeSet<>(new CompareY());

    public MapBoundary(GrassField map) {
        this.map = map;
        this.map.addObserver(this);
    }

    public void addElement(Vector2d position) {
        orderedElementsX.add(position);
        orderedElementsY.add(position);
        map.setNewBoundaries();
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        orderedElementsX.remove(oldPosition);
        orderedElementsY.remove(oldPosition);
        orderedElementsX.add(newPosition);
        orderedElementsY.add(newPosition);
        map.setNewBoundaries();
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(orderedElementsX.first().x, orderedElementsY.first().y);
    }

    public Vector2d getUpperRight() {
        return new Vector2d(orderedElementsX.last().x, orderedElementsY.last().y);
    }
}

class CompareX implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d o1, Vector2d o2) {
        if (o1.x != o2.x) {
            return o1.x - o2.x;
        } else {
            return o1.y - o2.y;
        }
    }
}

class CompareY implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d o1, Vector2d o2) {
        if (o1.y != o2.y) {
            return o1.y - o2.y;
        } else {
            return o1.x - o2.x;
        }
    }
}
