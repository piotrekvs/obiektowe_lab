package projekt1.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import projekt1.gui.UserInterface;
import projekt1.utils.Config;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class MenuController {
    private final UserInterface userInterface;
    @FXML
    private TextField widthXInput;
    @FXML
    private TextField heightYInput;
    @FXML
    private TextField jungleRatioInput;
    @FXML
    private TextField startNumOfAnimalsInput;
    @FXML
    private TextField startEnergyInput;
    @FXML
    private TextField moveEnergyInput;
    @FXML
    private TextField plantEnergyInput;
    @FXML
    private Label wrongValLabel;
    @FXML
    private CheckBox sim1Magic;
    @FXML
    private CheckBox sim2Magic;

    public MenuController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void handleUseDefaultConfigBtn() {
        System.out.println("Clicked UseDefaultConfigBtn.");
        widthXInput.setText(Integer.toString(userInterface.getDefaultConfig().getWidthX()));
        heightYInput.setText(Integer.toString(userInterface.getDefaultConfig().getHeightY()));
        jungleRatioInput.setText(Integer.toString(userInterface.getDefaultConfig().getJungleRatio()));
        startNumOfAnimalsInput.setText(Integer.toString(userInterface.getDefaultConfig().getStartNumOfAnimals()));
        startEnergyInput.setText(Integer.toString(userInterface.getDefaultConfig().getStartEnergy()));
        moveEnergyInput.setText(Integer.toString(userInterface.getDefaultConfig().getMoveEnergy()));
        plantEnergyInput.setText(Integer.toString(userInterface.getDefaultConfig().getPlantEnergy()));
        sim1Magic.setSelected(userInterface.getDefaultConfig().isSim1Magical());
        sim2Magic.setSelected(userInterface.getDefaultConfig().isSim2Magical());
    }

    public void handleStartSimulationBtn() throws IOException {
        System.out.println("Clicked StartSimulationBtn.");
        try {
            userInterface.getConfig().setConfig(
                    parseInt(widthXInput.getText()),
                    parseInt(heightYInput.getText()),
                    parseInt(jungleRatioInput.getText()),
                    parseInt(startNumOfAnimalsInput.getText()),
                    parseInt(startEnergyInput.getText()),
                    parseInt(moveEnergyInput.getText()),
                    parseInt(plantEnergyInput.getText()),
                    sim1Magic.isSelected(),
                    sim2Magic.isSelected()
            );
            wrongValLabel.setText("");
            System.out.println("StartSimulationBtn: Configuration set.");
        } catch (NumberFormatException e) {
            wrongValLabel.setText("WRONG VALUES!");
            System.out.println("StartSimulationBtn: WRONG VALUES!");
            return;
        }
        userInterface.startSimulation();
    }
}
