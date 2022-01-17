package projekt1.simulation.elements;

import projekt1.simulation.map.Vector2d;

public class Grass implements IMapElement {
    private final Vector2d position;
    private int energy;

    public Grass(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition() {
        return null;
    }

    @Override
    public String getImage() {
        return null;    // na pewno?
    }

    public int getEnergy() {
        return energy;
    }
}
