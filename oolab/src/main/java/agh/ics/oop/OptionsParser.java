package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    public MoveDirection[] parse(String[] inputDirs) {
        MoveDirection[] directions = new MoveDirection[inputDirs.length];
        int j = 0;
        for (String inputDir : inputDirs) {
            switch (inputDir) {
                case "f":
                case "forward":
                    directions[j++] = MoveDirection.FORWARD;
                    break;
                case "b":
                case "backward":
                    directions[j++] = MoveDirection.BACKWARD;
                    break;
                case "l":
                case "left":
                    directions[j++] = MoveDirection.LEFT;
                    break;
                case "r":
                case "right":
                    directions[j++] = MoveDirection.RIGHT;
                    break;
                default:
                    break;
            }
        }
        return Arrays.copyOfRange(directions, 0, j);
    }
}
