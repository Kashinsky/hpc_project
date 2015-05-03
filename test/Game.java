import reversi_serial.*;
import ga_serial.AI;
public class Game {
    public Reversi game;
    public Game() {
        this.game = new Reversi();
    }

    public void play(AI a1, AI a2) {
       this.game.run(a1, a2); 
    }

    public static void main(String [] args) {
        AI a1 = new AI();
        AI a2 = new AI();
        Game game = new Game(); 
        game.play(a1, a2);
        System.out.println("A1>\t" + a1.getFitness()); 
        System.out.println("A2>\t" + a2.getFitness()); 
    }

} 
