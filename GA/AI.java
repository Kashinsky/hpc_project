import java.util.Random;
public class AI {
    public static final int MAX_GENE_LENGTH = 64;
    private byte[] gene;
    private double fitness;

    public AI() {
        //generate random gene sequence for initial construction
        Random rand = new Random()
        this.gene = new byte[MAX_GENE_LENGTH];
        rand.nextBytes(this.gene);
    }

    public AI(byte[] gene) {
        this.gene = gene;
    }

    public void play(AI other) {
        // make them fight to the death
        // store fitness value
    }
    
    public double getFitness() {
        return this.fitness;
    }

    public byte[] getGene() {
        return this.gene;
    }

    public byte getGeneSegment(int i) {
        return this.gene[i];
    }

    public void setGeneSegment(int i, byte val) {
        this.gene[i] = val;
    }
}
