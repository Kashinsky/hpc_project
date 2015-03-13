import java.util.*;
import java.awt.Point;
import java.util.regex.*;
public class Gameboard {
    //fields
    private int[][]board;
    private int size;
    private static final Point [] DELTAS = {new Point(-1,-1), new Point(-1, 0), new Point(-1,1),
                                            new Point(0,-1),                    new Point(0,1),
                                            new Point(1, -1), new Point(1,0),   new Point(1, 1)}; 

    public Gameboard (int size) {
        this.board = new int[size][];
        for(int i = 0; i < size; i++) {
            this.board[i] = new int[size];
        }
        this.size = size;
        int firstPos = size / 2 - 1;
        setSpace(new Point(firstPos,firstPos),1);
        setSpace(new Point(firstPos,firstPos+1),2);
        setSpace(new Point(firstPos+1,firstPos),2);
        setSpace(new Point(firstPos+1,firstPos+1),1);
    }

    public String toString() {
        String b = "";
        b += "  0 1 2 3 4 5 6 7\n";
        for(int i = 0; i < size; i++) {
            b += i;
            for(int j = 0; j < size; j++) {
                b += " " + getSpace(new Point(i,j));
            }
            b += "\n"; 
        }
        return b;
    }
    
    public Set<Point> getPieces(int value) {
        Set<Point> pieceLoc = new HashSet<Point>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Point p = new Point(i,j);
                int space = getSpace(p);
                if(space == value)
                    pieceLoc.add(p);
            }
        }
        return pieceLoc; 
    }

    public int getSpace(Point p) {
        if (p.x < 0 || p.y < 0 || p.x >= this.size || p.y >= this.size)
            return -1;
        return board[p.y][p.x];
    }

    private int setSpace(Point p, int val) {
        board[p.y][p.x] = val;
        return board[p.y][p.x];
    }


    public int setSpaceRecurse(Point p, int val) {
        if(isLegal(p, val, true)) {
            setSpace(p, val);
        }
        return val;
    }

    public boolean isLegal(Point p, int val, boolean convert) {
        boolean b = false;
        for(int i = 0; i < DELTAS.length; i++) {
            if(getSpace(new Point(p.x + DELTAS[i].x, p.y + DELTAS[i].y)) == val || getSpace(new Point(p.x + DELTAS[i].x, p.y + DELTAS[i].y)) == -1)
                continue;
            if(isLegal((Point) p.clone(), DELTAS[i], val, convert)) {
                b = true;
            }
        }
        return b;
    }

    private boolean isLegal(Point p, Point d, int val, boolean convert) {
        p.translate(d.x, d.y);
        if (p.x < 0 || p.y < 0 || p.x >= this.size || p.y >= this.size || getSpace(p) == 0)
            return false;
        if(isLegal((Point) p.clone(), d, val, convert)) {
           if(convert)
            setSpace(p, val);
           return true; 
        }
        if(getSpace(p) == val)
            return true;
        return false;
    }
}
