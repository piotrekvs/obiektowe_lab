package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test
    void parse(){
        OptionsParser optionParser = new OptionsParser();
        String[] inputDirs1 = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        MoveDirection[] outputDirs1 = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
        };
        String[] inputDirs2 = {"f", "backward", "r", "g", "l"};
        Assertions.assertArrayEquals(outputDirs1, optionParser.parse(inputDirs1));
        Assertions.assertDoesNotThrow(() -> new OptionsParser().parse(inputDirs1));
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new OptionsParser().parse(inputDirs2));
    }
}
