package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        super(new Vector2d(0, 0), new Vector2d(width, height));
        lowerLeftDraw = new Vector2d(0, 0);
        upperRightDraw = new Vector2d(width, height);
    }
}
