package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeMap;

public class MapBoundary implements IPositionChangeObserver {
    private final AbstractWorldMap map;
    private final TreeMap<Vector2d, IMapElement> orderedElementsX =
            new TreeMap<>(new CompareX());
    private final TreeMap<Vector2d, IMapElement> orderedElementsY =
            new TreeMap<>(new CompareY());

    public MapBoundary(AbstractWorldMap map) {
        this.map = map;
    }

    public void addElement(IMapElement mapElement) {
        orderedElementsX.put(mapElement.getPosition(), mapElement);
        orderedElementsY.put(mapElement.getPosition(), mapElement);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement mapElement = map.objectAt(oldPosition);
        orderedElementsX.remove(oldPosition);
        orderedElementsY.remove(oldPosition);
        orderedElementsX.put(newPosition, mapElement);
        orderedElementsY.put(newPosition, mapElement);
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(orderedElementsX.firstKey().x, orderedElementsY.firstKey().y);
    }

    public Vector2d getUpperRight() {
        return new Vector2d(orderedElementsX.lastKey().x, orderedElementsY.lastKey().y);
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
