import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NaiveSolution {
    private ArrayList<NodeData> data;

    public NaiveSolution() {
        this.data = read();
    }

    public static ArrayList<NodeData> read() {
        ArrayList<NodeData> data = new ArrayList<>();

        try (Scanner s = new Scanner(
                new File(System.getProperty("user.dir") +
                        "/data/junctions.csv"), "UTF-8")) {
            // Benutzen Sie das Scanner-Objekt s hier
            s.useDelimiter(";|\\n");
            while (s.hasNext()) {
                data.add(new NodeData(s.next(), Double.valueOf(s.next()), Double.valueOf(s.next()), s.next()));             //neuer Knoten erstellen mit 4 nexts und adden
            }
        } catch (FileNotFoundException e) {
            // junctions.csv wurde nicht gefunden
            System.exit(1);
        }
        return data;
    }

    public int[] junctionsInRadius(double xPoint, double yPoint, double radius) {
        int countTrainstation = 0;
        int countAirports = 0;

        for (NodeData controlPoint: data) {

            double distance = Math.sqrt(Math.pow(xPoint - controlPoint.X(), 2) + Math.pow(yPoint - controlPoint.Y(), 2));      //Satz von Pythagoras

            if (distance < radius){
                if (controlPoint.getType().equals("TRAINSTATION")) {
                    countTrainstation++;
                }
                else {
                    countAirports++;
                }
            }

        }

        return new int[] {countTrainstation,countAirports};
    }

    public void junctionsInRadiusPrint(double xPoint, double yPoint, double radius) {
        int countTrainstation = 0;
        int countAirports = 0;

        for (NodeData controlPoint: data) {

            double distance = Math.sqrt(Math.pow(xPoint - controlPoint.X(), 2) + Math.pow(yPoint - controlPoint.Y(), 2));      //Satz von Pythagoras

            if (distance < radius){
                if (controlPoint.getType().equals("TRAINSTATION")) {
                    countTrainstation++;
                }
                else {
                    countAirports++;
                }
            }

        }

        System.out.println("Junctions less than " + radius + " units from x= " + xPoint + " y= " + yPoint +
                                "\n\t> Airports: " + countAirports + " Trainstations: " + countTrainstation);
    }


    //return the number of airports that have got more than trainstationAmount trainstations within a given radius

    public int airportsWithInTrainstationsAmount(int trainstationAmount, int radius){
        int count = 0;

        for (NodeData airport: data) {
            if (airport.getType().equals("AIRPORT")) {
                int[] junctions = junctionsInRadius(airport.X(),airport.Y(),radius);                                    //Array with the amount of {trainstations,airports]
                if (junctions[0] >= trainstationAmount){
                    count++;
                }
            }
        }

        System.out.println("Airports with at least " + trainstationAmount + " Trainstations less than " + radius + " units away" +
                                "\n\t" + "> " + count);

        return count;
    }
}
