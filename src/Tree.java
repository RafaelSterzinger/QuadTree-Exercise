import java.util.ArrayList;

public class Tree {
    private ArrayList<NodeData> content;
    private QuadTree root;

    //calculates the MAX - MIN dimensions of the QuadTree and fills it with the given content
    public Tree(ArrayList<NodeData> content) {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (NodeData junction : content) {
            if (junction.X() > maxX) {
                maxX = junction.X();
            } else if (junction.X() < minX) {
                minX = junction.X();
            }
            if (junction.Y() > maxY) {
                maxY = junction.Y();
            } else if (junction.Y() < minY) {
                minY = junction.Y();
            }
        }
        root = new QuadTreeRoot(new Point(minX, maxY),new Point(maxX, minY));
        this.content = content;
        fill();
    }

    private void fill(){
        for (NodeData insert: content) {
            root = root.add(insert);
        }
    }

    public void junctionsInRadiusPrint(double xPoint, double yPoint, double radius) {
        double time = System.currentTimeMillis();
        int[] junctions = new int[2];
        root.junctionsInRadius(xPoint, yPoint, radius, junctions);
        time -= System.currentTimeMillis();
        System.out.println(time);
        System.out.println("Junctions less than " + radius + " units from x= " + xPoint + " y= " + yPoint +
                "\n\t> Airports: " + junctions[0] + " Trainstations: " + junctions[1]);

    }

    public void airportsWithInTrainstationsAmountPrint(int trainstationAmount, int radius){
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

        for (NodeData airport: content) {
            if (airport.getType() == Type.AIRPORT) {
                int junctions[] = new int[2];
                root.junctionsInRadius(airport.X(),airport.Y(),radius, junctions);                                    //Array with the amount of {trainstations,airports}
                if (junctions[1] >= trainstationAmount){
                    count++;
                }
            }
        }

        return count;
    }
}
