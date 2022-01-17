package projekt1.simulation.elements;

import projekt1.Main;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private final static int LEN = 32;
    private final static int genesRange = 8;

    private final int[] genes = new int[LEN];   // czy enum nie byłby lepszy niż int?

    public Genome(Random rand) {    // nie lepiej bezparametrowy, a Random sobie stworzyć jako private static final?
        for (int i = 0; i < LEN; i++) {
            genes[i] = rand.nextInt(genesRange);
        }
        Arrays.sort(genes);
    }

    public Genome(int[] left, int[] right) {    // brak kontroli poprawności; nie lepiej przyjąć dwa genotypy?
        int i = 0;
        for (int l : left) {
            genes[i] = l;
            i++;
        }
        for (int r : right) {
            genes[i] = r;
            i++;
        }
        Arrays.sort(genes);
    }

    public int[] getGenes() {
        return genes;   // zwraca Pan na zewnątrz obiekt modyfikowalny
    }

    public int getLen() {
        return LEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genome genome = (Genome) o;

        return Arrays.equals(genes, genome.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
