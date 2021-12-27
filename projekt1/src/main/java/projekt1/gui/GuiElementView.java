package projekt1.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementView {
    private final Image animal100Img;
    private final Image animal75Img;
    private final Image animal50Img;
    private final Image animal25Img;
    private final Image grass100Img;
    private final int netSize;

    public GuiElementView(int netSize) throws FileNotFoundException {
        animal100Img = new Image(new FileInputStream("src/main/resources/assets/monkey100.png"));
        animal75Img = new Image(new FileInputStream("src/main/resources/assets/monkey75.png"));
        animal50Img = new Image(new FileInputStream("src/main/resources/assets/monkey50.png"));
        animal25Img = new Image(new FileInputStream("src/main/resources/assets/monkey25.png"));
        grass100Img = new Image(new FileInputStream("src/main/resources/assets/red-cherry.png"));
        this.netSize = netSize;
    }

    public ImageView getAnimalImg(int energyPercent) {
        ImageView imageView;
        if (energyPercent > 75) imageView = new ImageView(animal100Img);
        else if (energyPercent > 50) imageView = new ImageView(animal75Img);
        else if (energyPercent > 25) imageView = new ImageView(animal50Img);
        else imageView = new ImageView(animal25Img);
        imageView.setFitWidth(netSize);
        imageView.setFitHeight(netSize);
        return imageView;
    }

    public ImageView getGrassImg() {
        ImageView imageView = new ImageView(grass100Img);
        imageView.setFitWidth(netSize);
        imageView.setFitHeight(netSize);
        return imageView;
    }

}
