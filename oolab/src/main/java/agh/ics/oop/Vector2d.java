package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;

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

    public Vector2d upperRight(Vector2d other) {
        Vector2d newVector = new Vector2d(Math.max(other.x, x), Math.max(other.y, y));
        return newVector;
    }

    public Vector2d lowerLeft(Vector2d other) {
        Vector2d newVector = new Vector2d(Math.min(other.x, x), Math.min(other.y, y));
        return newVector;
    }

    public Vector2d add(Vector2d other) {
        Vector2d newVector = new Vector2d(x + other.x, y + other.y);
        return newVector;
    }

    public Vector2d subtract(Vector2d other) {
        Vector2d newVector = new Vector2d(x - other.x, y - other.y);
        return newVector;
    }

    public Vector2d opposite() {
        Vector2d newVector = new Vector2d(-x, -y);
        return newVector;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == x && that.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
