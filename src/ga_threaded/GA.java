package ga_threaded;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class GA {

    public static final double CROSSOVER_CHANCE = 0.5; // the chance a gene can be inherited
    public static final double MUTATION_CHANCE = 0.015; // the chance a gene will be mutated 
    private int generation;// the current generation number
    private int MAX_GEN; // max generation number
    private int MAX_POP; // max population size
    public List<AI> popList; // the list of AI's (ie. the population)

    public GA (int maxGen, int maxPop) {
        this.MAX_GEN = maxGen;
        this.MAX_POP = maxPop;
        this.popList = new ArrayList<AI>();
        fillPopulation();
    }
    
    public void fillPopulation() {
        while(popList.size() < MAX_POP) {
            popList.add(new AI());
        }
    }

    public void processGen() {
        for(int i = 0; i < popList.size(); i=i+2) {
            if(i < popList.size() -1) {
                (popList.get(i)).play(popList.get(i+1));
            }
        }
        this.generation++;
    }

    public void performCrossBreeding() {
        int currPopSize =  popList.size();
        for(int i = 0; i < currPopSize; i= i+2) {
            if(i < currPopSize-1) {
                AI a1 = popList.get(i);
                AI a2 = popList.get(i+1);
                popList.add(crossBreed(a1, a2));
            }
        }

    }

    public AI crossBreed(AI ai1, AI ai2) {
        float[] f = new float[AI.MAX_GENE_LENGTH];
        Random rand = new Random();
        for(int i = 0; i < f.length; i++) {
            if(rand.nextDouble() <= CROSSOVER_CHANCE)
                f[i] = ai1.getGeneSegment(i);
            else
                f[i] = ai2.getGeneSegment(i);
        }
        return new AI(f);
    }
    
    public void applyMutations() {
        for(int i = 0; i < popList.size(); i++)
            mutate(popList.get(i));
    }

    public void mutate(AI ai) {
        Random rand = new Random();
        for(int i = 0; i < AI.MAX_GENE_LENGTH; i++) {
            if(rand.nextDouble() <= MUTATION_CHANCE) {
                ai.setGeneSegment(i, (rand.nextFloat() * 4)- 2);
            }
        }
    }

    public double getAverageFitness() {
        double avg = 0;
        for(int i = 0; i < popList.size(); i++) {
            avg += popList.get(i).getFitness();
        }
        return (avg / (double) popList.size());
    }

    public void removeUnfit(double avg) {
        for(int i = 0; i < popList.size(); i++) {
            if(popList.get(i).getFitness() < avg)
                popList.remove(i);
        }
    }

    public double findPeak() {
        double max = 0;
        for(int i = 0; i < popList.size(); i++) {
            double temp = popList.get(i).getFitness();
            if(temp > max)
                max = temp; 
        }
        return max;
    }

    public void run(int steps) {
        while(steps > 0 && generation < MAX_GEN) {
            processGen();
            double avg = getAverageFitness();
            removeUnfit(avg);
            System.out.printf("Generation: %d\nAverageFitness: %f\nPeakFitness %f\n", this.generation, avg, findPeak());
            performCrossBreeding();
            applyMutations();
            fillPopulation();
            steps--;
        }
    }
}
