package agh.ics.oop;

public class World {
    public static void main(String[] args){
        /*
        System.out.println("System wystartował");
        String[] a = {"f", "b", "aaa", "l", "r"};
        if (args.length >= 2){
            Direction[] directions = convertArgs(args);
            run(directions);
        }
        */
        // Testy klasy Vector2d
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        System.out.println();
        // Testy klasy MapDirection
        System.out.println("MapDirection TEST:");
        MapDirection dir1 = MapDirection.NORTH;
        System.out.println(dir1.toString());
        System.out.println(dir1.previous());
        System.out.println(dir1.next().toUnitVector().toString());
        // Koniec testu klasy MapDirection
    }

    public static void run(Direction[] directions){
        System.out.println("START");
        for(Direction dir : directions){
            if (dir != null) {
                switch (dir) {
                    case FORWARD -> System.out.println("Zwierzak idzie do przodu.");
                    case BACKWARD -> System.out.println("Zwierzak idzie do tyłu.");
                    case LEFT -> System.out.println("Zwierzak idzie w lewo.");
                    case RIGHT -> System.out.println("Zwierzak idzie w prawo.");
                }
            }
        }
        System.out.println("STOP");
    }

    public static Direction[] convertArgs(String[] args){
        Direction[] directions = new Direction[args.length];
        for(int i = 0; i < args.length; i++){
            switch (args[i]) {
                case "f" -> directions[i] = Direction.FORWARD;
                case "b" -> directions[i] = Direction.BACKWARD;
                case "l" -> directions[i] = Direction.LEFT;
                case "r" -> directions[i] = Direction.RIGHT;
                default -> directions[i] = null;
            }
        }
        return directions;
    }
}
