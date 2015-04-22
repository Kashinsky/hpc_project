package reversi_ga;
import java.util.Random;
public class GA {

    public static final double CROSSOVER_CHANCE = 0.5; // the chance a gene can be inherited
    public static final double MUTATION_CHANCE = 0.015; // the chance a gene will be mutated 
    private int generation;// the current generation number
    private int population;// the current population
    private int MAX_GEN; // max generation number
    private int MAX_POP; // max population size
    public List<AI> popList; // the list of AI's (ie. the population)

    public GA (int maxGen, int maxPop) {
        this.MAX_GEN = maxGen;
        this.MAX_POP = maxPop;
        this.population = new ArrayList<AI>();
    }
    
    public void createInitPopulation() {
        for(int i = 0; i < MAX_POP; i++) {
            popList.add(new AI());
        }
        this.population = this.MAX_POP;
    }

    public void createNewGen() {
        for(int i = 0; i < popList.size(); i=i+2) {
            if(i < popList.size() -1) {
                (popList.get(i)).play(popList.get(i));
            }
        }
    } 

    public AI performCrossBreeding(AI ai1, AI ai2) {
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

    public void performMutation(AI ai) {
        Random rand = new Random();
        for(int i = 0; i < AI.MAX_GENE_LENGTH; i++) {
            if(rand.nextDouble() <= MUTATION_CHANCE) {
                ai.setGeneSegment(i, (rand.nextFloat() * 4)- 2);
            }
        }
    }
}
