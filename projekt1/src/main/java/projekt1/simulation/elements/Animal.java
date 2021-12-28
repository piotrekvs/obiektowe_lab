package projekt1.simulation.elements;

import projekt1.Main;
import projekt1.simulation.map.MapDirection;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;

import java.util.Arrays;
import java.util.Random;

public class Animal implements IMapElement {
    private final WorldMap map;
    private final Random rand;
    private MapDirection orientation;
    private Vector2d position;
    private final int startEnergy;
    private int energy;
    private int lifespan = 0;
    private int children = 0;
    private final Genome genome;

    public Animal(WorldMap map, Vector2d position, int startEnergy, Random rand) {
        this.position = position;
        this.startEnergy = startEnergy;
        this.energy = startEnergy;
        this.orientation = MapDirection.N.getRandomDirection();
        this.map = map;
        this.rand = rand;
        this.genome = new Genome(rand);
    }

    public Animal(WorldMap map, Vector2d position, int energy, int startEnergy, Random rand, Genome genome)  {
        this.position = position;
        this.startEnergy = startEnergy;
        this.energy = energy;
        this.orientation = MapDirection.N.getRandomDirection();
        this.map = map;
        this.rand = rand;
        this.genome = genome;
    }

    public boolean move(int moveCost) {
        addEnergy(-moveCost);
        int nextOrientation = genome.getGenes()[rand.nextInt(genome.getLen())];
        if (nextOrientation % 4 == 0) {
            Vector2d newPosition = position.add(orientation.nextBy(nextOrientation).toUnitVector());
            newPosition = map.canMoveTo(position, newPosition);
            if (newPosition.equals(position)) {
                return false;
            } else {
                position = newPosition;
                return true;
            }
        } else {
            orientation = orientation.nextBy(nextOrientation);
            return false;
        }
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getImage() {
        return null;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int x) {
        energy += x;
    }

    public int getEnergyPercent() {
        return (energy * 100) / startEnergy;
    }

    public int[] getGenes() {
        return genome.getGenes();
    }

    public int getLifespan() {
        return lifespan;
    }

    public void nextDay() {
        lifespan++;
    }

    public void newChild() {
        children++;
    }

    public int getChildren() {
        return children;
    }

    public Genome getGenome() {
        return genome;
    }
}
