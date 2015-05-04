package ga_threaded;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Breeder extends Thread {

    public static final double CROSSOVER_CHANCE = 0.5; // the chance a gene can be inherited
    public static final double MUTATION_CHANCE = 0.015; // the chance a gene will be mutated 
    public Semaphore sem;
    public AI a1;
    public AI a2;
    public float[] resultingGene;
    
    public Breeder(AI a1, AI a2, Semaphore sem) {
        this.a1 = a1;
        this.a2 = a2;
        this.sem = sem;
        this.resultingGene = new float[AI.MAX_GENE_LENGTH];
    }

    public void run() {
        try {
            sem.acquire();
            crossbreed();
            mutate();
            sem.release();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void crossbreed() {
        Random rand = new Random();
        for(int i = 0; i < resultingGene.length; i++) {
            if(rand.nextDouble() <= CROSSOVER_CHANCE)
                resultingGene[i] = a1.getGeneSegment(i);
            else
                resultingGene[i] = a2.getGeneSegment(i);
        }
    }

    public void mutate() {
        Random rand = new Random();
        for(int i = 0; i < AI.MAX_GENE_LENGTH; i++) {
            if(rand.nextDouble() <= MUTATION_CHANCE) {
                resultingGene[i] = (rand.nextFloat() * 4)- 2;
            }
        }
    }

    public AI getAI() {
        return new AI(resultingGene);
    }


}
