package ga_serial;
import java.util.Random;
import reversi_serial.Reversi;
import java.awt.Point;
import java.util.*;

public class AI {
    public static final int MAX_GENE_LENGTH = 10;
    private float[] gene;
    private double fitness;
    private final int[][] weightBoard = new int[][] {
        {0, 1, 2, 3, 3, 2, 1, 0},
        {1, 2, 3, 4, 4, 3, 2, 1},
        {4, 5, 6, 7, 7, 6, 5, 4},
        {5, 6, 7, 8, 8, 7, 6, 5},
        {5, 6, 7, 8, 8, 7, 6, 5},
        {4, 5, 6, 7, 7, 6, 5, 4},
        {1, 2, 3, 4, 4, 3, 2, 1},
        {0, 1, 2, 3, 3, 2, 1, 0}
    };

    public AI() {
        //generate random gene sequence for initial construction
        Random rand = new Random();
        float[] g = new float[MAX_GENE_LENGTH];
        for(int i = 0; i < g.length; i++) {
            g[i] = (rand.nextFloat() * 4) - 2;
        }
        this.gene = g;
    }

    public AI(float[] gene) {
        this.gene = gene;
    }

    public void play(AI other) {
        Reversi game = new Reversi();
        game.run(this, other);
        // make them fight to the death
        // store fitness value
    }

    public Point move(Set<Point> moveSet) {
        
        return null;
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

    public void setFitness(double val) {
        this.fitness = val;
    }
}
