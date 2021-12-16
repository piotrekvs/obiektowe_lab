package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {
    @Test
    void runAssertionTest() {
        String[] inputDirs = {"f", "b", "r", "l"};
        MoveDirection[] directions = new OptionsParser().parse(inputDirs);
        AbstractWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(map, positions);
        engine.run();
        Assertions.assertEquals(MapDirection.EAST,
                ((Animal) map.objectAt(new Vector2d(2,3))).getOrientation());
        Assertions.assertEquals(MapDirection.WEST,
                ((Animal) map.objectAt(new Vector2d(3,3))).getOrientation());
    }
}
