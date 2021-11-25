package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directions;
    private final IWorldMap map;
    protected List<Animal> animals = new ArrayList<>();

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        this.map = map;
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    @Override
    public void run() {
        System.out.println(map);
        int i = 0;
        for (MoveDirection direction : directions) {
            Animal animal = animals.get(i++);
            animal.move(direction);
            if (i >= animals.size()) {
                i = 0;
            }
            System.out.println("idx" + i + "; " + animal.getPosition() + "; " +
                    animal.getOrientation());
            System.out.println(map);
        }
    }
}
