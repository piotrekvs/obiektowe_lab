package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable {
    private final MoveDirection[] directions;
    private final AbstractWorldMap map;
    protected List<Animal> animals = new ArrayList<>();
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private final int moveDelay = 2000;

    public SimulationEngine(MoveDirection[] directions, AbstractWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        this.map = map;
        this.map.addObserver(this);
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
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        positionChangedNotify(oldPosition, newPosition);
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    protected void positionChangedNotify(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
