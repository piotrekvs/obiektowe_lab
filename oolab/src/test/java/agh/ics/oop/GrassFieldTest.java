package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {
    @Test
    void isOccupiedAssertionTest() {
        IWorldMap map  = new GrassField(5);
        map.place(new Animal(map, new Vector2d(2, 2)));
        map.place(new Animal(map, new Vector2d(2, 3)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(2, 2)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(2, 3)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(3, 2)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0, 0)));
    }

    @Test
    void canMoveToAssertionTest() {
        IWorldMap map  = new GrassField(5);
        map.place(new Animal(map, new Vector2d(2, 2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(3, 3)));
    }

    @Test
    void objectAtAssertionTest() {
        IWorldMap map  = new GrassField(5);
        map.place(new Animal(map, new Vector2d(2, 2)));
        Assertions.assertNotNull(map.objectAt(new Vector2d(2, 2)));
        Assertions.assertEquals(new Vector2d(2, 2), ((Animal) map.objectAt(new Vector2d(2, 2))).getPosition());
        Assertions.assertNull(map.objectAt(new Vector2d(2, 3)));
    }
}
