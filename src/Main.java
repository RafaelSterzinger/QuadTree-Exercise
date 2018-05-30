import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Naive Solution
        NaiveSolution testNaive = new NaiveSolution(read());
        testNaive.junctionsInRadiusPrint(0,0,575);
        testNaive.junctionsInRadiusPrint(1818.54657,5813.29982, 100);
        testNaive.airportsWithInTrainstationsAmountPrint(5,1);
        testNaive.airportsWithInTrainstationsAmountPrint(20,15);

        System.out.println();
        //QuadTree
        Tree testQuad = new Tree(read());
        testQuad.junctionsInRadiusPrint(0,0,575);
        testQuad.junctionsInRadiusPrint(1818.54657,5813.29982, 100);
        testQuad.airportsWithInTrainstationsAmountPrint(5,1);
        testQuad.airportsWithInTrainstationsAmountPrint(20,15);


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

    /*Fragen Allgemein:
    Weil wir uns darunter am meisten vorstellen konnten.(Ebenen ident)
    Bei dichten Datenpunkten viele rekursive Aufrufe. Gleichmäßig verteilt bzw. große Abstände führen zu keinem Geschwindigkeitsverlust.
    War ident. Datenstruktur mussten Fehler ausgemerzt werden (In jedem Knoten war Inhalt), Abfrage erst durchdacht werden. Beides hat gleichviel Zeit gekostet.
    Bei sehr kleinem Radius ist die Abfrage schneller, sonst gewinnt die Konstruktion überwiegend.
    1 zu 7. Naive 1 Stunde und Effizent 7 Stunden.

    Fragen QuadTree:
    Es muss darauf geachtet werden, dass keine identen Daten hinzugefügt werden(endlos Rekursion, bei gleichen (x,y) Werten)
    Bei engzusammenliegenden Knoten könnte der QuadTree zu einer Liste entarten.

     */

}