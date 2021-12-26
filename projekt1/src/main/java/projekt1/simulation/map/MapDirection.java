package projekt1.simulation.map;

public enum MapDirection {
    N, NE, E, SE, S, SW, W, NW;

    private final String[] plNames = new String[]{
            "North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest"
    };

    private final Vector2d[] vectors = new Vector2d[]{
            new Vector2d(0, 1), new Vector2d(1, 1),
            new Vector2d(1, 0), new Vector2d(1, -1),
            new Vector2d(0, -1), new Vector2d(-1, -1),
            new Vector2d(-1, 0), new Vector2d(-1, 1)
    };

    @Override
    public String toString() {
        return plNames[this.ordinal()];
    }

    public MapDirection nextBy(int by) {
        return MapDirection.values()[(this.ordinal() + 8 + (by % 8)) % 8];
    }

    public MapDirection previousBy(int by) {
        return MapDirection.values()[(this.ordinal() + 8 + (-by % 8)) % 8];
    }

    public MapDirection next() {
        return nextBy(1);
    }

    public MapDirection previous() {
        return previousBy(1);
    }

    public Vector2d toUnitVector() {
        return vectors[this.ordinal()];
    }
}
