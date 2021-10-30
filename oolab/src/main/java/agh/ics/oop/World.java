package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        String[] inputDirs = {"f", "l", "b", "f", "r"};
        OptionsParser parser = new OptionsParser();
        MoveDirection[] parsedDirections = parser.parse(inputDirs);
        Animal animal = new Animal();
        for (MoveDirection dir : parsedDirections) {
            animal.move(dir);
        }
        // System.out.println(animal.toString());
    }
}
