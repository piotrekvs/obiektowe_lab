package projekt1.simulation.elements;

import projekt1.simulation.map.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    String getImage();  // dobrze by to było przenieść do GUI
}
