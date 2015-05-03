import ga_serial.*;
import reversi_serial.*;
public class GATest {
    public static void main(String [] args) {
        GA ga = new GA(100, 10000);
        ga.run(100);
    }

}
