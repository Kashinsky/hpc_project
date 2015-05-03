package reversi_threaded;
import ga_threaded.*;
import reversi_serial.Gameboard;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.awt.Point;

public class ReversiThreaded extends Thread {
    
    //fields
    public static final int DEFAULT_BOARD_SIZE = 8;
    public AI a1;
    public AI a2;
    private Semaphore sem;
    private Gameboard gameboard;
    
    public ReversiThreaded(AI a1, AI a2, Semaphore sem) {
        this.a1 = a1;
        this.a2 = a2;
        this.sem = sem;
        this.gameboard = new Gameboard(DEFAULT_BOARD_SIZE);
    }

    public String printBoard() {
        return gameboard.toString();
    }

    public String printPieces(int val) {
        String s = "";
        Set<Point> ps = gameboard.getPieces(val);
        for(Point p : ps) {
            s += p.x + " " + p.y + ",";
        }
        return s;
    }

    public void run() {
        try {
            sem.acquire();
            boolean playable = true;
            int player = 1;
            Set<Point> moveSet = this.gameboard.getMoveSet(player);
            Point chosenMove;
            while(playable) {
                if(player == 1) {
                    chosenMove = a1.move(moveSet);
                    gameboard.setSpaceRecurse(chosenMove, player);
                    player = 2;
                } else {
                    chosenMove = a2.move(moveSet);
                    gameboard.setSpaceRecurse(chosenMove, player);
                    player = 1;
                }
                moveSet = this.gameboard.getMoveSet(player);
                playable = !moveSet.isEmpty();
            }
            a1.setFitness(gameboard.getPieceCount(1));
            a2.setFitness(gameboard.getPieceCount(2));
            release();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private synchronized void release() {
        sem.release();
    }
}
