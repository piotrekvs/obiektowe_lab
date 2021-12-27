import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projekt1.simulation.map.Vector2d;
import projekt1.simulation.map.WorldMap;

public class WorldMapTest {

    @Test
    void moveToWrappedTest() {
        Vector2d upperRight = new Vector2d(29, 29);
        Vector2d jungleLowerLeft = new Vector2d(9, 9);
        Vector2d jungleUpperRight = new Vector2d(19, 19);;
        boolean isWrapped = true;
        WorldMap map = new WorldMap(upperRight, jungleLowerLeft, jungleUpperRight, isWrapped);
        // Upper Right
        Assertions.assertEquals(map.moveTo(new Vector2d(30, 30)), new Vector2d(0,0));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, 31)), new Vector2d(10, 1));
        Assertions.assertEquals(map.moveTo(new Vector2d(32, 11)), new Vector2d(2, 11));
        Assertions.assertEquals(map.moveTo(new Vector2d(0, 10)), new Vector2d(0, 10));
        Assertions.assertEquals(map.moveTo(new Vector2d(29, 10)), new Vector2d(29, 10));
        // Lower Left
        Assertions.assertEquals(map.moveTo(new Vector2d(-2, -2)), new Vector2d(28, 28));
        Assertions.assertEquals(map.moveTo(new Vector2d(-5, 10)), new Vector2d(25, 10));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, -2)), new Vector2d(10, 28));
        Assertions.assertEquals(map.moveTo(new Vector2d(0, 20)), new Vector2d(0, 20));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, 0)), new Vector2d(10, 0));
    }

    @Test
    void moveToNotWrappedTest() {
        Vector2d upperRight = new Vector2d(29, 29);
        Vector2d jungleLowerLeft = new Vector2d(9, 9);
        Vector2d jungleUpperRight = new Vector2d(19, 19);;
        boolean isWrapped = false;
        WorldMap map = new WorldMap(upperRight, jungleLowerLeft, jungleUpperRight, isWrapped);
        // Upper Right
        Assertions.assertEquals(map.moveTo(new Vector2d(30, 30)), new Vector2d(29,29));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, 31)), new Vector2d(10, 29));
        Assertions.assertEquals(map.moveTo(new Vector2d(32, 11)), new Vector2d(29, 11));
        Assertions.assertEquals(map.moveTo(new Vector2d(0, 10)), new Vector2d(0, 10));
        Assertions.assertEquals(map.moveTo(new Vector2d(29, 10)), new Vector2d(29, 10));
        // Lower Left
        Assertions.assertEquals(map.moveTo(new Vector2d(-2, -2)), new Vector2d(0, 0));
        Assertions.assertEquals(map.moveTo(new Vector2d(-5, 10)), new Vector2d(0, 10));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, -2)), new Vector2d(10, 0));
        Assertions.assertEquals(map.moveTo(new Vector2d(0, 20)), new Vector2d(0, 20));
        Assertions.assertEquals(map.moveTo(new Vector2d(10, 0)), new Vector2d(10, 0));
    }
}
