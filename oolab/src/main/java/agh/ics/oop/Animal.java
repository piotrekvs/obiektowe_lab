package agh.ics.oop;


public class Animal {
    private final IWorldMap map;
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    @Override
    public String toString() {
        String[] names = new String[]{  // można by to zapisać jako pole private static final
                "N", "E", "S", "W"
        };
        return names[orientation.ordinal()];
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case BACKWARD:
                orientation = orientation.next().next();    // zwierzę się nie powinno odwracać, tylko przemieścić
            case FORWARD:
                if (map.canMoveTo(position.add(orientation.toUnitVector()))){
                    position = position.add(orientation.toUnitVector());    // dwa razy dodawanie wektora
                }
                break;
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }
}
