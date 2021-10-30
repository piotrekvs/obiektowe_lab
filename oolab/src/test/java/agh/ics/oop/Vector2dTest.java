package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v1opposite = new Vector2d(-1,-2);
    Vector2d v2 = new Vector2d(2, -5);

    @Test
    void simpleToStringAssertion() {
        Assertions.assertEquals("(2, -5)" ,new Vector2d(2, -5).toString());
    }

    @Test
    void simpleEqualsAssertion() {
        //noinspection AssertBetweenInconvertibleTypes
        Assertions.assertNotEquals(3, new Vector2d(1, 1));
        Assertions.assertEquals(new Vector2d(1, 1), new Vector2d(1, 1));
    }

    @Test
    void precedesAssertion() {
        Assertions.assertTrue(v1opposite.precedes(v1));
        Assertions.assertFalse(v2.precedes(v1));
        Assertions.assertTrue(v1.precedes(v1));
        Assertions.assertFalse(v1.precedes(v1opposite));
    }

    @Test
    void followsAssertion() {
        Assertions.assertTrue(v1.follows(v1opposite));
        Assertions.assertFalse(v1.follows(v2));
        Assertions.assertTrue(v1.follows(v1));
        Assertions.assertFalse(v1opposite.follows(v1));
    }

    @Test
    void upperRightAssertion() {
        Assertions.assertEquals(v1, v1.upperRight(v1opposite));
        Assertions.assertNotEquals(v2, v1.upperRight(v1opposite));
    }

    @Test
    void lowerLeftAssertion() {
        // lowerLeft
        Assertions.assertEquals(v1opposite, v1.lowerLeft(v1opposite));
        Assertions.assertNotEquals(v2, v1.lowerLeft(v2));
    }

    @Test
    void addAssertion() {
        Assertions.assertEquals(new Vector2d(0, 0), v1.add(v1opposite));
        Assertions.assertNotEquals(new Vector2d(0, 0), v1.add(v2));
    }

    @Test
    void subtractAssertion() {
        Assertions.assertEquals(new Vector2d(2, 4), v1.subtract(v1opposite));
        Assertions.assertNotEquals(v1, v1.add(v2));
    }

    @Test
    void oppositeAssertion() {
        Assertions.assertEquals(v1opposite, v1.opposite());
        Assertions.assertNotEquals(v2, v1.opposite());
    }
}
