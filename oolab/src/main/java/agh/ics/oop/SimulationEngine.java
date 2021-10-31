package agh.ics.oop;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directions;
    private final IWorldMap map;
    private final Vector2d[] positions;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        this.map = map;
        this.positions = positions;
        for (Vector2d position : positions) {
            map.place(new Animal(map, position));
        }
    }

    @Override
    public void run() {
        int i = 0;
        for (MoveDirection direction : directions) {
            Animal animal = (Animal) map.objectAt(positions[i]);
            if (animal != null) {
                animal.move(direction);
                positions[i++] = animal.getPosition();
                if (i >= positions.length) {
                    i = 0;
                }
                // System.out.println("idx" + i + "; " + animal.getPosition().toString() + "; " + animal.getOrientation().toString());
            }
            // System.out.println(map.toString());
        }
    }
}
