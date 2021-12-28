package projekt1.simulation.engine;

import projekt1.simulation.elements.Animal;
import projekt1.simulation.elements.Genome;
import projekt1.simulation.elements.Grass;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MagicalEngine extends NormalEngine {

    public MagicalEngine(WorldMap map, int startNumOfAnimals, int startEnergy, int moveEnergy, int plantEnergy, boolean isMagical) {
        super(map, startNumOfAnimals, startEnergy, moveEnergy, plantEnergy, isMagical);
    }

    @Override
    public void run() {
        while (isWorking) {
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (map.getWorldStatistics().getAliveAnimals() <= 5 && map.getWorldStatistics().getAliveAnimals() >= 1 &&
                    map.getWorldStatistics().getMagicalEvents() < 3) {
                doMagic();
                map.getWorldStatistics().magicalEvent();
            }
            map.deleteDeadAnimals();
            map.makeMoves(moveEnergy);
            map.eatGrass();
            map.animalsMultiplication(startEnergy);
            // Placing grass on the map
            map.placeGrassInJungle(plantEnergy);
            map.placeGrassNotInJungle(plantEnergy);

            updateStatistics();
            System.out.println("ERA END");
            eraEndedNotify();
        }
    }

    private void doMagic() {
        ArrayList<Genome> newAnimalsGenom = new ArrayList<>();
        map.getAnimalHashMap().keySet().forEach(key -> {
            ArrayList<Animal> animals = map.getAnimalHashMap().get(key);
            for (Animal animal : animals) {
                int[] genes = Arrays.copyOfRange(animal.getGenome().getGenes(), 0, animal.getGenome().getLen());
                Genome genome = new Genome(genes, new int[]{});
                newAnimalsGenom.add(genome);
                System.out.println("Hello: "+Arrays.toString(genes));
            }
        });
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello: "+ newAnimalsGenom.get(i % newAnimalsGenom.size()).toString());

            map.placeAnimalRandomlyMagicalEvent(startEnergy,
                    newAnimalsGenom.get(i % newAnimalsGenom.size()));
        }
    }
}
