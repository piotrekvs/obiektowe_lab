package projekt1.simulation.elements;

import java.util.Comparator;

public class AnimalEnergyComperator implements Comparator<Animal> { // Comparator
    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.getEnergy() - o2.getEnergy();
    }
}
