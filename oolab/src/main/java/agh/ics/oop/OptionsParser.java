package agh.ics.oop;

public class OptionsParser {
    public MoveDirection[] parse(String[] inputDirs) {
        MoveDirection[] directions = new MoveDirection[inputDirs.length];

        for (int i = 0; i < inputDirs.length; i++) {
            switch (inputDirs[i]) {
                case "f":
                case "forward":
                    directions[i] = MoveDirection.FORWARD;
                    break;
                case "b":
                case "backward":
                    directions[i] = MoveDirection.BACKWARD;
                    break;
                case "l":
                case "left":
                    directions[i] = MoveDirection.LEFT;
                    break;
                case "r":
                case "right":
                    directions[i] = MoveDirection.RIGHT;
                    break;
                default:
                    break;
            }
        }
        return directions;
    }
}
