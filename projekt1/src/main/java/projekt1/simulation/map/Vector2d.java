package projekt1.simulation.map;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public boolean precedes(Vector2d other) {
        return other.x >= x && other.y >= y;
    }

    public boolean follows(Vector2d other) {
        return other.x <= x && other.y <= y;
    }

    public boolean stronglyPrecedesIn1d(Vector2d other) {
        return other.x > x || other.y > y;
    }

    public boolean stronglyFollowsIn1d(Vector2d other) {
        return other.x < x || other.y < y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(other.x, x), Math.max(other.y, y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(other.x, x), Math.min(other.y, y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
