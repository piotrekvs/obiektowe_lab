package agh.ics.oop;


public enum MapDirection {
    NORTH, EAST, SOUTH, WEST;

    private final String[] plNames = new String[]{
            "Północ", "Wschód", "Południe", "Zachód"
    };

    private final Vector2d[] vectors = new Vector2d[]{
            new Vector2d(0, 1), new Vector2d(1, 0),
            new Vector2d(0, -1), new Vector2d(-1, 0),
    };

    @Override
    public String toString() {
        return plNames[this.ordinal()];
    }

    public MapDirection next() {
        return MapDirection.values()[(this.ordinal() + 1) % 4];
    }

    public MapDirection previous() {
        return MapDirection.values()[(this.ordinal() + 3) % 4];
    }

    public Vector2d toUnitVector() {
        return vectors[this.ordinal()];
    }
}
