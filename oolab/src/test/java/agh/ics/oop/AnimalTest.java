package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    void moveAndOrientationAssertion() {
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(MapDirection.EAST ,animal.getOrientation());
        Assertions.assertEquals(new Vector2d(4, 2) ,animal.getPosition());
    }
    @Test
    void boundariesAssertion(){
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(0, 4) ,animal.getPosition());
    }

    @Test
    void parserAndInputAssertion() {
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        String[] inputDirs = {"f", "left", "forward", "x", "l", "f", "f"};
        OptionsParser parser = new OptionsParser();
        MoveDirection[] parsedDirections = parser.parse(inputDirs);
        for (MoveDirection dir : parsedDirections){
            animal.move(dir);
        }
        Assertions.assertEquals(MapDirection.SOUTH ,animal.getOrientation());
        Assertions.assertEquals(new Vector2d(1, 1) ,animal.getPosition());
    }
}
