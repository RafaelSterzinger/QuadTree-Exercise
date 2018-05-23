import java.util.ArrayList;

public class NaiveSolution {
    private ArrayList<NodeData> data;

    public NaiveSolution(ArrayList<NodeData> data) {
        this.data = data;
    }

    public void junctionsInRadiusPrint(double xPoint, double yPoint, double radius) {
        int[] junctions = junctionsInRadius(xPoint, yPoint, radius);
        System.out.println("Junctions less than " + radius + " units from x= " + xPoint + " y= " + yPoint +
                "\n\t> Airports: " + junctions[0] + " Trainstations: " + junctions[1]);
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

        return new int[] {countAirports, countTrainstation};
    }

    public void airportsWithInTrainstationsAmountPrint(int trainstationAmount, double radius){
        double time = System.currentTimeMillis();
        int count = airportsWithInTrainstationsAmount(trainstationAmount, radius);
        time -= System.currentTimeMillis();
        System.out.println(time);
        System.out.println("Airports with at least " + trainstationAmount + " Trainstations less than " + radius + " units away" +
                "\n\t" + "> " + count);
    }

    //return the number of airports that have got more than trainstationAmount trainstations within a given radius
    public int airportsWithInTrainstationsAmount(int trainstationAmount, double radius){
        int count = 0;

        for (NodeData airport: data) {
            if (airport.getType().equals("AIRPORT")) {
                int[] junctions = junctionsInRadius(airport.X(),airport.Y(),radius);                                    //Array with the amount of {trainstations,airports]
                if (junctions[1] >= trainstationAmount){
                    count++;
                }
            }
        }

        return count;
    }
}
