package projekt1.utils;

public class Config {
    private int widthX;
    private int heightY;
    private int jungleRatio;
    private int startNumOfAnimals;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;

    public Config(int widthX, int heightY, int jungleRatio, int startNumOfAnimals,
                  int startEnergy, int moveEnergy, int plantEnergy) {
        setConfig(widthX, heightY, jungleRatio, startNumOfAnimals, startEnergy, moveEnergy, plantEnergy);
    }

    public void setConfig(int widthX, int heightY, int jungleRatio, int startNumOfAnimals,
                          int startEnergy, int moveEnergy, int plantEnergy) {
        this.widthX = widthX;
        this.heightY = heightY;
        this.jungleRatio = jungleRatio;
        this.startNumOfAnimals = startNumOfAnimals;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
    }

    public int getWidthX() {
        return widthX;
    }

    public int getHeightY() {
        return heightY;
    }

    public int getJungleRatio() {
        return jungleRatio;
    }

    public int getStartNumOfAnimals() {
        return startNumOfAnimals;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    @Override
    public String toString() {
        return "Config{" +
                "widthX=" + widthX +
                ", heightY=" + heightY +
                ", jungleRatio=" + jungleRatio +
                ", startNumOfAnimals=" + startNumOfAnimals +
                ", startEnergy=" + startEnergy +
                ", moveEnergy=" + moveEnergy +
                ", plantEnergy=" + plantEnergy +
                '}';
    }
}
