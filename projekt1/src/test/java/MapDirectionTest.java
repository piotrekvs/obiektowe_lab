import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projekt1.simulation.map.MapDirection;
import projekt1.simulation.map.Vector2d;

public class MapDirectionTest {
    @Test
    void nextTest() {
        Assertions.assertEquals(MapDirection.E.next(), MapDirection.SE);
        Assertions.assertEquals(MapDirection.N.next().next(), MapDirection.E);
        // reminder test
        Assertions.assertEquals(MapDirection.NW.next(), MapDirection.N);
    }

    @Test
    void previousTest() {
        Assertions.assertEquals(MapDirection.SW.previous(), MapDirection.S);
        Assertions.assertEquals(MapDirection.NW.previous(), MapDirection.W);
        // reminder test
        Assertions.assertEquals(MapDirection.N.previous(), MapDirection.NW);
        Assertions.assertEquals(MapDirection.NE.previous().previous().previous(), MapDirection.W);
    }

    @Test
    void nextByTest() {
        // full cycle
        Assertions.assertEquals(MapDirection.E.nextBy(8), MapDirection.E);
        Assertions.assertEquals(MapDirection.N.nextBy(16), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.nextBy(32), MapDirection.NW);
        // positive, less than cycle
        Assertions.assertEquals(MapDirection.SW.nextBy(3), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.nextBy(2), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.nextBy(7), MapDirection.NE);
        // negative, less than cycle
        Assertions.assertEquals(MapDirection.SW.nextBy(-4), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.nextBy(-3), MapDirection.NW);
        Assertions.assertEquals(MapDirection.N.nextBy(-7), MapDirection.NE);
        // positive, more than cycle
        Assertions.assertEquals(MapDirection.SW.nextBy(3+8), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.nextBy(2+16), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.nextBy(7+24), MapDirection.NE);
        // negative, more than cycle
        Assertions.assertEquals(MapDirection.SW.nextBy(-4-8), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.nextBy(-3-16), MapDirection.NW);
        Assertions.assertEquals(MapDirection.N.nextBy(-7-32), MapDirection.NE);
    }

    @Test
    void previousByTest() {
        // full cycle
        Assertions.assertEquals(MapDirection.E.previousBy(8), MapDirection.E);
        Assertions.assertEquals(MapDirection.N.previousBy(16), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.previousBy(32), MapDirection.NW);
        // positive, less than cycle
        Assertions.assertEquals(MapDirection.SW.previousBy(-3), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.previousBy(-2), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.previousBy(-7), MapDirection.NE);
        // negative, less than cycle
        Assertions.assertEquals(MapDirection.SW.previousBy(4), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.previousBy(3), MapDirection.NW);
        Assertions.assertEquals(MapDirection.N.previousBy(7), MapDirection.NE);
        // positive, more than cycle
        Assertions.assertEquals(MapDirection.SW.previousBy(-(3+8)), MapDirection.N);
        Assertions.assertEquals(MapDirection.NW.previousBy(-(2+16)), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.previousBy(-(7+24)), MapDirection.NE);
        // negative, more than cycle
        Assertions.assertEquals(MapDirection.SW.previousBy(-(-4-8)), MapDirection.NE);
        Assertions.assertEquals(MapDirection.E.previousBy(-(-3-16)), MapDirection.NW);
        Assertions.assertEquals(MapDirection.N.previousBy(-(-7-32)), MapDirection.NE);
    }

    @Test
    void othersTest() {
        Assertions.assertEquals(MapDirection.NE.toUnitVector(), new Vector2d(1, 1));
        Assertions.assertEquals(MapDirection.SW.toUnitVector(), new Vector2d(-1, -1));
        Assertions.assertEquals(MapDirection.SE.toUnitVector(), new Vector2d(1, -1));
        Assertions.assertEquals(MapDirection.NW.toUnitVector(), new Vector2d(-1, 1));
        Assertions.assertEquals(MapDirection.SW.toString(), "Southwest");
        Assertions.assertEquals(MapDirection.NW.toString(), "Northwest");
        Assertions.assertEquals(MapDirection.S.toString(), "South");
    }
}
