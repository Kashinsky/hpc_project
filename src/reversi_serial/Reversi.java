package reversi_serial;
import java.util.*;
import java.awt.Point;
import ga_serial.*;

public class Reversi {
    //fields
    public static final int DEFAULT_BOARD_SIZE = 8;
    private Gameboard gameboard;

    public Reversi() {
        this(DEFAULT_BOARD_SIZE);
    }
    
    public Reversi(int size) {
        this.gameboard = new Gameboard(size);
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
    
    public void run(AI p1, AI p2) {
        boolean playable = true;
        int player = 1;
        Set<Point> moveSet = this.gameboard.getMoveSet(player);
        Point chosenMove;
        while(playable) {
            if(player == 1) {
                System.out.println("P1 TURN");
                chosenMove = p1.move(moveSet);
                gameboard.setSpaceRecurse(chosenMove, player);
                player = 2;
            } else {
                System.out.println("P2 TURN");
                chosenMove = p2.move(moveSet);
                gameboard.setSpaceRecurse(chosenMove, player);
                player = 1;
            }
            moveSet = this.gameboard.getMoveSet(player);
            System.out.println(gameboard.toString());
            playable = !moveSet.isEmpty();
        }
        p1.setFitness(gameboard.getPieceCount(1));
        p2.setFitness(gameboard.getPieceCount(2));    
    }

    public void humanRun() {
        Scanner sc = new Scanner(System.in);
        int turn = 1;
        while(true) {
            System.out.println(printBoard());
            System.out.println(gameboard.getMoveSet(turn));
            //System.out.println(printPieces(turn));
            System.out.printf("Player(%d)> ", turn);
            int row = sc.nextInt();
            int col = sc.nextInt();
            if(turn == 1) {
                if(!gameboard.isLegal(new Point(row,col), 1, false)) {
                    System.out.println("You done goofed");
                    continue;
                }
                gameboard.setSpaceRecurse(new Point(row, col), 1);
                turn = 2;
            } else {
                if(!gameboard.isLegal(new Point(row,col), 2, false)) {
                    System.out.println("You done goofed");
                    continue;
                }
                gameboard.setSpaceRecurse(new Point(row, col), 2);
                turn = 1;
            }
        }
    }

    public static void main(String [] args) {
        Reversi game;
        if(args.length > 0) {
            game = new Reversi(Integer.parseInt(args[0]));
        } else {
            game = new Reversi(); 
        }
        game.humanRun();
    }

}
