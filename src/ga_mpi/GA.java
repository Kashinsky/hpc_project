package ga_mpi;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import mpi.*;
public class GA {

    public static final double CROSSOVER_CHANCE = 0.5; // the chance a gene can be inherited
    public static final double MUTATION_CHANCE = 0.015; // the chance a gene will be mutated 
    //private int generation;// the current generation number
    //private int MAX_GEN; // max generation number
    //private int MAX_POP; // max population size
    //public List<AI> popList; // the list of AI's (ie. the population)
    
    //added for MPI stuff
    public static final int MAX_GEN = 100;
    public static final int MAX_POP = 10000;
    
    //private int rank;
    //private int size;
    //private List<Float> geneList; // list of genes which correspond to AIs
    //private int chunkSize;
    
    /*public GA (int maxGen, int maxPop, String[] args) throws MPIException {
        this.MAX_GEN = maxGen;
        this.MAX_POP = maxPop;
        this.popList = new ArrayList<AI>();
   
        // tweaks for mpi     
        MPI.Init(args);
        //rank = MPI.COMM_WORLD.getRank();
        //size = MPI.COMM_WORLD.getSize();
        //fillPopulation();
    }*/
    
    public static void fillPopulation(List<AI> popList) {
        while(popList.size() < MAX_POP) {
            popList.add(new AI());
        }
    }

    public static void processGen(List<AI> popList) {
        for(int i = 0; i < popList.size(); i=i+2) {
            if(i < popList.size() -1) {
                (popList.get(i)).play(popList.get(i+1));
            }
        }
        //this.generation++;
    }

    public static void performCrossBreeding(List<AI> popList) {
        int currPopSize =  popList.size();
        for(int i = 0; i < currPopSize; i= i+2) {
            if(i < currPopSize-1) {
                AI a1 = popList.get(i);
                AI a2 = popList.get(i+1);
                popList.add(crossBreed(a1, a2));
            }
        }

    }

    public static AI crossBreed(AI ai1, AI ai2) {
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
    
    public static void applyMutations(List<AI> popList) {
        for(int i = 0; i < popList.size(); i++)
            mutate(popList.get(i));
    }

    public static void mutate(AI ai) {
        Random rand = new Random();
        for(int i = 0; i < AI.MAX_GENE_LENGTH; i++) {
            if(rand.nextDouble() <= MUTATION_CHANCE) {
                ai.setGeneSegment(i, (rand.nextFloat() * 4)- 2);
            }
        }
    }

    public static double getAverageFitness(List<AI> popList) {
        double avg = 0;
        for(int i = 0; i < popList.size(); i++) {
            avg += popList.get(i).getFitness();
        }
        return (avg / (double) popList.size());
    }

    public static void removeUnfit(double avg, List<AI> popList) {
        for(int i = 0; i < popList.size(); i++) {
            if(popList.get(i).getFitness() < avg)
                popList.remove(i);
        }
    }

    public static double findPeak(List<AI> popList) {
        double max = 0;
        for(int i = 0; i < popList.size(); i++) {
            double temp = popList.get(i).getFitness();
            if(temp > max)
                max = temp; 
        }
        return max;
    }

    /*public void run(int steps) {
        rank = MPI.COMM_WORLD.getRank();
        size = MPI.COMM_WORLD.getSize();
        // only the head node needs the full list
        if (rank == 0)
            fillPopulation();
    
        while(steps > 0 && generation < MAX_GEN) {
            if (rank == 0) {
            }
            
        
            processGen();
            
            if (rank == 0) {
                double avg = getAverageFitness();
                removeUnfit(avg);
                System.out.printf("Generation: %d\nAverageFitness: %f\nPeakFitness %f\n", this.generation, avg, findPeak());
                performCrossBreeding();
                applyMutations();
                fillPopulation();
                steps--;
            }
        }
    }*/
    
    public static void main(String args[]) throws MPIException {
        MPI.Init(args);
        int generation = 0;// the current generation number
        //private int MAX_GEN; // max generation number
        //private int MAX_POP; // max population size
        List<AI> popList = new ArrayList<AI>(); // the list of AI's (ie. the population)
        int rank = MPI.COMM_WORLD.getRank();
        int size = MPI.COMM_WORLD.getSize();
        int chunkSize = MAX_POP/(size-1);
        
        //steps have been removed
        //while(steps > 0 && generation < MAX_GEN)
        
        while(generation < MAX_GEN) {
            if (rank == 0) {
                fillPopulation(popList);
                
                //break population into chunks and send them
                for (int i = 1; i < MAX_POP/chunkSize; i++) {
                    float[] chunk = new float[chunkSize * 10];
                    for (int j = 0; j < chunkSize; j++) {
                        float[] curGenes = popList.get((i-1) * chunkSize + j).getGene();
                        for (int k = 0; k < AI.MAX_GENE_LENGTH; k++) {
                            chunk[j * chunkSize + k] = curGenes[k];
                        }
                    }
                    MPI.COMM_WORLD.send(chunk,
                                        chunkSize * 10,
                                        MPI.FLOAT,
                                        i,
                                        23);
                }
                
                for (int i = 1; i < MAX_POP/chunkSize; i++) {
                    double[] chunk = new double[chunkSize];
                    MPI.COMM_WORLD.recv(chunk,
                                        chunkSize,
                                        MPI.DOUBLE,
                                        i,
                                        37);
                    for (int j = 0; j < chunkSize; j++) {
                        popList.get((i-1) * chunkSize + j).setFitness(chunk[j]);
                    }
                }
                
                
                double avg = getAverageFitness(popList);
                removeUnfit(avg, popList);
                System.out.printf("Generation: %d\nAverageFitness: %f\nPeakFitness %f\n", generation, avg, findPeak(popList));
                performCrossBreeding(popList);
                applyMutations(popList);
                fillPopulation(popList);
                
            }
            else {
                popList = new ArrayList<AI>();
                float[] geneChunk = new float[chunkSize * 10];
                MPI.COMM_WORLD.recv(geneChunk,
                                    chunkSize * 10,
                                    MPI.FLOAT,
                                    rank,
                                    23);
                for (int i = 0; i < chunkSize * 10; i++) {
                    float[] gene = new float[AI.MAX_GENE_LENGTH];
                    for (int j = 0; j < AI.MAX_GENE_LENGTH; j++) {
                        gene[j] = geneChunk[i+j];
                    }
                    AI ai = new AI(gene);
                    popList.add(ai);
                }
                
                processGen(popList);
                
                double[] fitnesses = new double[chunkSize];
                for (int i = 0; i < chunkSize; i++) {
                    fitnesses[i] = popList.get(i).getFitness();
                }
                MPI.COMM_WORLD.send(fitnesses,
                                    chunkSize,
                                    MPI.DOUBLE,
                                    0,
                                    37);
            }
            generation++;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
