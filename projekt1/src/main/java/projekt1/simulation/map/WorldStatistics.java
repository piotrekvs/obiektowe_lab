package projekt1.simulation.map;

import projekt1.simulation.elements.Genome;

public class WorldStatistics {
    private int currentDay = 0;
    private int deadAnimals = 0;
    private int aliveAnimals = 0;
    private float avgLifespanOfDeadAnimals = 0;
    private int avgEnergyLvl = 0;
    private int avgChildrenNum = 0;
    private int numOfGrass = 0;
    private int magicalEvents = 0;
    private Genome dominantGenome = new Genome(new int[]{0}, new int[]{0});

    public void addDeadAnimal(int lifeSpan) {
        float tempResult1 = ((float) deadAnimals / (float)(deadAnimals + 1)) * avgLifespanOfDeadAnimals;
        float tempResult2 = (float) lifeSpan / (float)(deadAnimals + 1);
        avgLifespanOfDeadAnimals = tempResult1 + tempResult2;
        deadAnimals += 1;
    }

    public void magicalEvent() {
        magicalEvents++;
    }

    public void nextDay() {
        currentDay++;
    }

    public void setAvgEnergyLvl(int avgEnergyLvl) {
        this.avgEnergyLvl = avgEnergyLvl;
    }

    public void setAvgChildrenNum(int avgChildrenNum) {
        this.avgChildrenNum = avgChildrenNum;
    }

    public int getAliveAnimals() {
        return aliveAnimals;
    }

    public void setAliveAnimals(int aliveAnimals) {
        this.aliveAnimals = aliveAnimals;
    }

    public int getMagicalEvents() {
        return magicalEvents;
    }

    public int getAvgEnergyLvl() {
        return avgEnergyLvl;
    }

    public int getAvgChildrenNum() {
        return avgChildrenNum;
    }

    public int getNumOfGrass() {
        return numOfGrass;
    }

    public void setNumOfGrass(int numOfGrass) {
        this.numOfGrass = numOfGrass;
    }

    public Genome getDominantGenome() {
        return dominantGenome;
    }

    public void setDominantGenome(Genome dominantGenome) {
        this.dominantGenome = dominantGenome;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public float getAvgLifespanOfDeadAnimals() {
        return avgLifespanOfDeadAnimals;
    }

    public int getDeadAnimals() {
        return deadAnimals;
    }
}
