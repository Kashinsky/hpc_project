package ga_threaded;

import reversi_threaded.*;;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class GA {

    private int generation;// the current generation number
    private int MAX_GEN; // max generation number
    private int MAX_POP; // max population size
    public List<AI> popList; // the list of AI's (ie. the population)
    public Semaphore sem;

    public GA (int maxGen, int maxPop) {
        this.MAX_GEN = maxGen;
        this.MAX_POP = maxPop;
        this.popList = new ArrayList<AI>();
        sem = new Semaphore(MAX_POP/2, true);
        fillPopulation();
    }
    
    public void fillPopulation() {
        while(popList.size() < MAX_POP) {
            popList.add(new AI());
        }
    }

    public void processGen() {
        List<ReversiThreaded> threads = new ArrayList<ReversiThreaded>();
        for(int i = 0; i < popList.size(); i=i+2) {
            if(i < popList.size()-1) {
                threads.add(new ReversiThreaded(popList.get(i), popList.get(i+1), sem));
            }
        }
        for(int i = 0; i < threads.size(); i++)
            (threads.get(i)).start();
        try {
            sem.acquire(MAX_POP/2);
            sem.release(MAX_POP/2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        this.generation++;
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

    public void breedGen() {
        List<Breeder> breeders = new ArrayList<Breeder>();
        for(int i = 0; i < popList.size(); i=i+2) {
            if(i < popList.size()-1) {
                breeders.add(new Breeder(popList.get(i), popList.get(i+1), sem));
            }
        }
        for(int i = 0; i < breeders.size(); i++)
            (breeders.get(i)).start();
        try {
            sem.acquire(MAX_POP/2);
            sem.release(MAX_POP/2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        for(int i = 0; i < breeders.size(); i++) {
            popList.add((breeders.get(i)).getAI());

        }

    }

    public void run(int steps) {
        while(steps > 0 && generation < MAX_GEN) {
            processGen();
            double avg = getAverageFitness();
            removeUnfit(avg);
            breedGen();
            System.out.printf("Generation: %d\nAverageFitness: %f\nPeakFitness %f\n", this.generation, avg, findPeak());
            fillPopulation();
            steps--;
        }
    }
}
