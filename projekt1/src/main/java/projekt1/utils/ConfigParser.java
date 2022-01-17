package projekt1.utils;


import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigParser {
    public static Config parse() throws Exception { // czemu throws Exception?
        Path path = Path.of("src/main/java/projekt1/DefaultConfig.json");   // parametry powinny być ustawiane przez GUI, nie z pliku
        String jsonString = Files.readString(path);
        JSONObject jsonObject = new JSONObject(jsonString);

        int widthX = jsonObject.getInt("widthX");
        int heightY = jsonObject.getInt("heightY");
        int jungleRatio = jsonObject.getInt("jungleRatio");
        int startNumOfAnimals = jsonObject.getInt("startNumOfAnimals");
        int startEnergy = jsonObject.getInt("startEnergy");
        int moveEnergy = jsonObject.getInt("moveEnergy");
        int plantEnergy = jsonObject.getInt("plantEnergy");
        boolean isSim1Magical = jsonObject.getBoolean("isSim1Magical");
        boolean isSim2Magical = jsonObject.getBoolean("isSim2Magical");

        return new Config( widthX,  heightY,  jungleRatio, startNumOfAnimals,
                startEnergy, moveEnergy, plantEnergy, isSim1Magical, isSim2Magical);
    }
}
