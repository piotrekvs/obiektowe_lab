package agh.ics.oop.gui;


import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class GuiElementBox {
    private final VBox vBox;

    public GuiElementBox(IMapElement mapElement, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Label label = new Label(mapElement.toString().equals("*") ? "Grass" : "Animal");
        this.vBox = new VBox();
        this.vBox.getChildren().addAll(imageView, label);
        this.vBox.setAlignment(Pos.CENTER);
    }

    public VBox getBox() {
        return vBox;
    }
}
