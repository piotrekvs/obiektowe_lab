package projekt1;

import javafx.application.Application;
import javafx.stage.Stage;
import projekt1.gui.UserInterface;
import projekt1.utils.Config;
import projekt1.utils.ConfigParser;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("App has started");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Config defaultConfig = ConfigParser.parse();
            UserInterface ui = new UserInterface(primaryStage, defaultConfig);
            ui.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
