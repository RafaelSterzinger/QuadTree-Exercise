import java.util.ArrayList;

public class Tree {
    private static ArrayList<NodeData> content;
    private QuadTree root;

    public Tree(ArrayList<NodeData> content) {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (NodeData junction : content) {                                                                          //Ermittelt Dimension der Ebene
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
            //StdDraw.point(insert.X(),insert.Y());
        }
    }

    public void junctionsInRadiusPrint(double xPoint, double yPoint, double radius) {
        int[] junctions = new int[2];
        root.junctionsInRadius(xPoint, yPoint, radius, junctions);
        System.out.println("Junctions less than " + radius + " units from x= " + xPoint + " y= " + yPoint +
                "\n\t> Airports: " + junctions[0] + " Trainstations: " + junctions[1]);
    }
}
