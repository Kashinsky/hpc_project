import java.util.Random;
public class AI {
    public static final int MAX_GENE_LENGTH = 10;
    private float[] gene;
    private double fitness;

    public AI() {
        //generate random gene sequence for initial construction
        Random rand = new Random();
        float[] g = new float[MAX_GENE_LENGTH];
        for(int i = 0; i < g.length; i++) {
            g[i] = (rand.nextFloat() * 4) - 2;
        }
        this(g);
    }

    public AI(float[] gene) {
        this.gene = gene;
    }

    public void play(AI other) {
        // make them fight to the death
        // store fitness value
    }
    
    public double getFitness() {
        return this.fitness;
    }

    public float[] getGene() {
        return this.gene;
    }

    public float getGeneSegment(int i) {
        return this.gene[i];
    }

    public void setGeneSegment(int i, float val) {
        this.gene[i] = val;
    }

}
