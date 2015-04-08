public class GA {

    private int generation;// the current generation number
    private int population;// the current population
    private int MAX_GEN; // max generation number
    private int MAX_POP; // max population size
    public List<AI> popList; // the list of AI's (ie. the population)
    public static final double crossoverChance = 0.5;
    public static final double mutationChance = 0.015;
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
        
        }
    } 

    public AI performCrossBreeding(AI ai1, AI ai2) {
        byte[] b = new byte[AI.MAX_GENE_LENGTH];
        for(int i = 0; i < b.length; i++) {
            
        } 

    }
}
