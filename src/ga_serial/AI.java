package ga_serial;
import java.util.Random;
import reversi_serial.Reversi;
import java.awt.Point;
import java.util.*;

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
