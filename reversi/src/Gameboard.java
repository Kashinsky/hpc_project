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
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                b += " " + getSpace(new Point(i,j));
            }
            b += "\n"; 
        }
        return b;
    }

    public boolean isLegal(int row, int col, int player) {
        Point curr = new Point(row, col);
        if(getSpace(curr) != 0) {
            return false;
        }
        // Check top-left
        for(int i = 0; i < DELTAS.length; i++) {
            String s = getValuesInPath(curr, DELTAS[i].x, DELTAS[i].y, player);
            System.out.println(s);
            int otherplayer = player % 2 + 1;
            if(Pattern.matches(player + "[" + otherplayer + "]+" + player + ".*", s))
                return true;   
        }
        return false;
    }

    private String getValuesInPath(Point p, int dx, int dy, int player) {
        String s = "" + player;
        Point temp = new Point(p.x, p.y);
        temp.x += dx;
        temp.y += dy;
        while(temp.x >= 0 && temp.y >= 0 && temp.x < this.size && temp.y < this.size) {
            s += getSpace(temp);
            temp.x += dx;
            temp.y += dy;
        }
        return s;
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
        return board[p.y][p.x];
    }

    private int setSpace(Point p, int val) {
        board[p.y][p.x] = val;
        return board[p.y][p.x];
    }


    public int setSpaceRecurse(Point p, int val) {
        boolean b = false;
        for(int i = 0; i < DELTAS.length; i++) {
            if(convertSpaces((Point) p.clone(), DELTAS[i], val)) {
                b = true;
            }
        }
        if(b) {
            setSpace(p, val);
        }
        return val;
    }

    private boolean convertSpaces(Point p, Point d, int val) {
        p.x += d.x;
        p.y += d.y;
        if (p.x < 0 || p.y < 0 || p.x >= this.size || p.y >= this.size || getSpace(p) == 0)
            return false;
        if(convertSpaces((Point) p.clone(), d, val)) {
           setSpace(p, val);
           return true; 
        }
        if(getSpace(p) == val)
            return true;
        return false;
    }
}
