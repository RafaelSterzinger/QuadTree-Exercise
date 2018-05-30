import java.util.ArrayList;

public class NaiveSolution {
    private ArrayList<NodeData> data;

    public NaiveSolution(ArrayList<NodeData> data) {
        this.data = data;
    }

    public void junctionsInRadiusPrint(double x, double y, double radius) {
        int[] junctions = junctionsInRadius(x, y, radius);
        System.out.println("Junctions less than " + radius + " units from x= " + x + " y= " + y +
                "\n\t> Airports: " + junctions[0] + " Trainstations: " + junctions[1]);
    }

    //calculates how many junctions there are from a set point (x, y) with a given radius
    public int[] junctionsInRadius(double x, double y, double radius) {
        int countTrainstation = 0;
        int countAirports = 0;

        for (NodeData controlPoint: data) {
                                                                                                                                                //Math.Pow erhöht Laufzeit immens
            double distance = Math.sqrt((x - controlPoint.X()) * (x - controlPoint.X()) + (y - controlPoint.Y()) * (y - controlPoint.Y()));     //Satz von Pythagoras
            if (distance < radius){
                if (controlPoint.getType() == Type.TRAINSTATION) {
                    countTrainstation++;
                }
                else {
                    countAirports++;
                }
            }
        }

        return new int[] {countAirports, countTrainstation};
    }

    public void airportsWithInTrainstationsAmountPrint(int trainstationAmount, double radius){
        int count = airportsWithInTrainstationsAmount(trainstationAmount, radius);
        System.out.println("Airports with at least " + trainstationAmount + " Trainstations less than " + radius + " units away" +
                "\n\t" + "> " + count);
    }

    //return the number of airports that have got more than trainstationAmount trainstations within a given radius
    public int airportsWithInTrainstationsAmount(int trainstationAmount, double radius){
        int count = 0;

        for (NodeData airport: data) {
            if (airport.getType() == Type.AIRPORT) {
                int[] junctions = junctionsInRadius(airport.X(),airport.Y(),radius);                                    //Array with the amount of {trainstations,airports}
                if (junctions[1] >= trainstationAmount){
                    count++;
                }
            }
        }

        return count;
    }
}
