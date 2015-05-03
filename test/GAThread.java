import ga_threaded.*;
import reversi_threaded.*;
public class GAThread {
    public static void main(String [] args) {
         ga_threaded.GA ga = new GA(100, 10000);
         ga.run(100);

    }    
} 
