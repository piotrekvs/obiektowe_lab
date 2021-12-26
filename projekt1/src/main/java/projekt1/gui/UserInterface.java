package projekt1.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projekt1.gui.controller.MenuController;
import projekt1.utils.Config;

import java.io.IOException;

public class UserInterface {
    private final Stage menuStage;
    private final Config defaultConfig;
    private static final Config config = new Config(0, 0, 0,
            0, 0, 0, 0);

    public UserInterface(Stage primaryStage, Config defaultConfig) {
        this.menuStage = primaryStage;
        this.defaultConfig = defaultConfig;
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        loader.setControllerFactory(c -> new MenuController(this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        menuStage.setScene(scene);
        menuStage.show();
    }

    public void startSimulation() {
        System.out.println(config);
    }

    public Config getDefaultConfig() {
        return defaultConfig;
    }

    public Config getConfig() {
        return config;
    }
}