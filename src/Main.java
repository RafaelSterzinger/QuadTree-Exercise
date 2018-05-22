import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Draw
//        StdDraw.setCanvasSize(1000, 1000 );
//        StdDraw.setXscale(-25000, 25000);
//        StdDraw.setYscale(-25000, 25000);



        //Naive Solution
//        NaiveSolution test = new NaiveSolution(read());
//        test.junctionsInRadiusPrint(0,0,575);
//        test.junctionsInRadiusPrint(1818.54657,5813.29982, 100);
//        test.airportsWithInTrainstationsAmountPrint(5,1);
//        test.airportsWithInTrainstationsAmountPrint(20,15);

        //QuadTree
        QuadTree test = new QuadTree(read());
        System.out.println();
        System.out.println(test.junctionsInRadius(1818.54657,5813.29982, 100)[1]);
    }

    //Read wird für beide Lösungen verwendet
    private static ArrayList<NodeData> read() {
        ArrayList<NodeData> data = new ArrayList<>();

        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {
            // Benutzen Sie das Scanner-Objekt s hier
            s.useDelimiter(";|\\r\\n");
            while (s.hasNext()) {
                data.add(new NodeData(s.next(), Double.valueOf(s.next()), Double.valueOf(s.next()), s.next()));             //neuer Knoten erstellen mit 4 nexts und adden
            }
        } catch (FileNotFoundException e) {
            // junctions.csv wurde nicht gefunden
            System.exit(1);
        }
        return data;
    }

}