import java.util.ArrayList;

public class QuadTree {

    private NodeData junction;
    private Point topLeft;
    private Point botRight;
    private QuadTree NW,NO,SW,SO;
    private static ArrayList<NodeData> content;

    private QuadTree(Point topLeft, Point botRight){
        this.topLeft = topLeft;
        this.botRight = botRight;
    }

    public QuadTree(ArrayList<NodeData> junctions) {
        double minX = Double.MIN_VALUE,minY = Double.MIN_VALUE;
        double maxX = Double.MAX_VALUE,maxY = Double.MIN_VALUE;
        for (NodeData junction: junctions) {                                                                          //Ermittelt Dimension der Ebene
            if (junction.X() > maxX){       maxX = junction.X(); }
            else if (junction.X() < minX){  minX = junction.X(); }
            if (junction.Y() > maxY){       maxY = junction.Y(); }
            else if (junction.Y() < minY){  minY = junction.Y(); }
        }

        this.topLeft = new Point(minX, maxY);
        this.botRight = new Point(maxX, minY);

        creatLeafs();
        content = junctions;
        fill();
    }

    //Creats children for Quadtree
    private void creatLeafs(){
        double xMittelwert = (topLeft.getX() + botRight.getX())/2;
        double yMittlewert = (topLeft.getY() + botRight.getY())/2;
        Point newOrigin = new Point(xMittelwert, yMittlewert);

        //Blatt links/oben
        NW = new QuadTree(topLeft, newOrigin);
        //Blatt rechts/oben
        NO = new QuadTree(new Point(newOrigin.getX(), topLeft.getY()), new Point(botRight.getX(), newOrigin.getY()));
        //Blatt links/unten
        SW = new QuadTree(new Point(topLeft.getX(), newOrigin.getY()), new Point(newOrigin.getX(), botRight.getY()));
        //Blatt rechts/unten
        SO = new QuadTree(newOrigin, botRight);
    }


    private void fill(){
       for (NodeData insert: content) {
            if (junction == null){
                junction = insert;
            }
            else {
                add(insert);
            }
       }
    }

    private void add(NodeData junction){
        if (this.junction == null){
            this.junction = junction;
            creatLeafs();
        }
        else {
            if (NodeInBoundary(NW, junction)) {
                NW.add(junction);
            } else if (NodeInBoundary(NO, junction)) {
                NO.add(junction);
            } else if (NodeInBoundary(SW, junction)) {
                SW.add(junction);
            } else if (NodeInBoundary(SO, junction)) {
                SO.add(junction);
            }
        }
    }

    //Schaut um der Knoten in den Quadranten passt.
    private boolean NodeInBoundary(QuadTree quadrant, NodeData junction){
        double x = junction.X();
        double y = junction.Y();

        if (quadrant.topLeft.getX() < junction.X() && quadrant.botRight.getX() > junction.X()){
            if (quadrant.topLeft.getY() > junction.Y() && quadrant.botRight.getY() < junction.Y()){
                return true;
            }
        }
        return false;
    }

    //Noch nicht fertig
    public int[] junctionsInRadius(double xPoint, double yPoint, double radius) {
        int[] count = new int[2];
        int countAirports = 0, countTrainstation = 0;
        if (junction != null) {
            NodeData check = new NodeData(null, xPoint, yPoint, null);

            if (NodeInBoundary(NW, check)) {
                count = NW.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (NodeInBoundary(NO, check)) {
                count = NO.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (NodeInBoundary(SW, check)) {
                count = SW.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (NodeInBoundary(SO, check)) {
                count = SO.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            }

            double distance = Math.sqrt(Math.pow(xPoint - junction.X(), 2) + Math.pow(yPoint - junction.Y(), 2));      //Satz von Pythagoras
            if (distance < radius) {
                if (junction.getType().equals("TRAINSTATION")) {
                    countTrainstation += count[1];
                } else {
                    countAirports += count[0];
                }
            }
        }
        count[0] = countAirports;
        count[1] = countTrainstation;
        return count;
    }

    //Must be implemented
    /*
    //return the number of airports that have got more than trainstationAmount trainstations within a given radius
    public int airportsWithInTrainstationsAmount(int trainstationAmount, int radius){
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
    */




}
