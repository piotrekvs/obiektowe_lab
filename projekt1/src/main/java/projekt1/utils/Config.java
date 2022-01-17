package projekt1.utils;

public class Config {
    private int widthX; // to by nie mogło być finalne?
    private int heightY;
    private int jungleRatio;
    private int startNumOfAnimals;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private boolean isSim1Magical;
    private boolean isSim2Magical;

    public Config(int widthX, int heightY, int jungleRatio, int startNumOfAnimals,
                  int startEnergy, int moveEnergy, int plantEnergy, boolean isSim1Magical, boolean isSim2Magical) {
        setConfig(widthX, heightY, jungleRatio, startNumOfAnimals, startEnergy, moveEnergy, plantEnergy, isSim1Magical, isSim2Magical);
    }

    public void setConfig(int widthX, int heightY, int jungleRatio, int startNumOfAnimals,
                          int startEnergy, int moveEnergy, int plantEnergy, boolean isSim1Magical, boolean isSim2Magical) { // czy na pewno chcemy udostępniać taki setter? i czy chcemy zmieniać ustawienia w trakcie działania?
        this.widthX = widthX;
        this.heightY = heightY;
        this.jungleRatio = jungleRatio;
        this.startNumOfAnimals = startNumOfAnimals;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.isSim1Magical = isSim1Magical;
        this.isSim2Magical = isSim2Magical;
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

    public boolean isSim1Magical() {
        return isSim1Magical;
    }

    public boolean isSim2Magical() {
        return isSim2Magical;
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
                ", isSim1Magical=" + isSim1Magical +
                ", isSim2Magical=" + isSim2Magical +
                '}';
    }
}
