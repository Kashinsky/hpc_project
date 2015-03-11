import java.util.*;
import java.awt.Point;
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
    
    public void humanRun() {
        Scanner sc = new Scanner(System.in);
        int turn = 1;
        while(true) {
            System.out.println(printPieces(turn));
            System.out.print("PROMPT> ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            if(turn == 1) {
                if(!gameboard.isLegal(row,col,1)) {
                    System.out.println("You done goofed");
                    continue;
                }
                gameboard.setSpace(row, col, 1);
                turn = 2;
            } else {
                if(!gameboard.isLegal(row,col,2)) {
                    System.out.println("You done goofed");
                    continue;
                }
                gameboard.setSpace(row, col, 2);
                turn = 1;
            }
            System.out.println(printBoard());

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