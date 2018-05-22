import java.util.ArrayList;

public class QuadTree {

    private NodeData junction;
    private Point topLeftPoint;
    private Point botRightPoint;
    private QuadTree topLeft,topRight,botLeft,botRight;
    private static ArrayList<NodeData> content;

    private QuadTree(Point topLeftPoint, Point botRightPoint){
        this.topLeftPoint = topLeftPoint;
        this.botRightPoint = botRightPoint;
    }

    public QuadTree(ArrayList<NodeData> junctions) {

        double minX = Double.MAX_VALUE,minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE,maxY = Double.MIN_VALUE;
        for (NodeData junction: junctions) {                                                                          //Ermittelt Dimension der Ebene
            if (junction.X() > maxX){       maxX = junction.X(); }
            else if (junction.X() < minX){  minX = junction.X(); }
            if (junction.Y() > maxY){       maxY = junction.Y(); }
            else if (junction.Y() < minY){  minY = junction.Y(); }
        }

        this.topLeftPoint = new Point(minX,maxY);
        this.botRightPoint = new Point(maxX,minY);

        content = junctions;
        fill();
    }

    private void fill(){
       for (NodeData insert: content) {
                add(insert);
                //StdDraw.point(insert.X(),insert.Y());
       }
    }

    private void add(NodeData junction) {
        if (junction == null) {
            return;
        }
        if (!inBoundary(new QuadTree(topLeftPoint, botRightPoint), new Point(junction.X(), junction.Y()))) {
            return;
        }

        if (Math.abs(topLeftPoint.getY() - junction.Y()) <= 1 && Math.abs(botRightPoint.getX() - junction.X()) <= 1) {
            this.junction = junction;
            return;
        }

        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;
        Point newOrigin = Origin();

        if (xMittelwert <= junction.X()) {                                                                               ////Kontrolle auf zu untersuchendem Quadrant

            if (yMittlewert <= junction.Y()) {

                if (topRight == null) {
                    topRight = new QuadTree(new Point(newOrigin.getX(), topLeftPoint.getY()), new Point(botRightPoint.getX(), newOrigin.getY()));
                }
                topRight.add(junction);

            } else {
                if (botRight == null) {
                    botRight = new QuadTree(newOrigin, botRightPoint);
                }
                botRight.add(junction);
            }

        } else {
            if (yMittlewert <= junction.Y()) {

                if (topLeft == null) {
                    topLeft = new QuadTree(topLeftPoint, newOrigin);
                }
                topLeft.add(junction);

            } else {
                if (botLeft == null) {
                    botLeft = new QuadTree(new Point(topLeftPoint.getX(), newOrigin.getY()), new Point(newOrigin.getX(), botRightPoint.getY()));
                }
                botLeft.add(junction);


            }
        }
    }

    //Schaut um der Knoten in den Quadranten passt.
    private boolean inBoundary(QuadTree quadrant, Point point){
        double x = point.getX();
        double y = point.getY();

        if (quadrant.topLeftPoint.getX() < x && quadrant.botRightPoint.getX() > x){
            if (quadrant.topLeftPoint.getY() > y && quadrant.botRightPoint.getY() < y){
                return true;
            }
        }
        return false;
    }

    private Point Origin(){
        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;

        return new Point(xMittelwert, yMittlewert);
    }

    public int[] junctionsInRadius(double xPoint, double yPoint, double radius) {
        return junctionsInRadius(xPoint, yPoint, radius, new int[2]);
    }

    private int[] junctionsInRadius(double xPoint, double yPoint, double radius, int[] count) {
        if (junction != null) {
            double distance = Math.sqrt(Math.pow(xPoint - junction.X(), 2) + Math.pow(yPoint - junction.Y(), 2));      //Satz von Pythagoras

            if (distance < radius) {
                if (junction.getType().equals("TRAINSTATION")) {
                    count[1]++;
                } else {
                    count[0]++;
                }
            }
            return count;
        }

        Point Origin = Origin();
        Point[] circleSquare = new Point[4];
        QuadTree[] quadranten = new QuadTree[4];
        QuadTree[] realQuads = new QuadTree[4];

        quadranten[0] = new QuadTree(new Point(Double.MIN_VALUE, Double.MAX_VALUE), Origin);
        quadranten[1] = new QuadTree(new Point(Origin.getX(), Double.MAX_VALUE), new Point(Double.MAX_VALUE, Origin.getY()));
        quadranten[2] = new QuadTree(new Point(Double.MIN_VALUE, Origin.getY()), new Point(Origin.getX(), Double.MIN_VALUE));
        quadranten[3] = new QuadTree(Origin, new Point(Double.MAX_VALUE, Double.MIN_VALUE));

        realQuads[0] = topLeft;
        realQuads[1] = topRight;
        realQuads[2] = botLeft;
        realQuads[3] = botRight;



        circleSquare[0] = new Point(xPoint - radius, yPoint + radius);                                        //TL
        circleSquare[1] = new Point(xPoint + radius, yPoint + radius);                                        //TR
        circleSquare[2] = new Point(xPoint - radius, yPoint - radius);                                        //BL
        circleSquare[3] = new Point(xPoint + radius, yPoint - radius);                                        //BR

        for (int quadrant = 0; quadrant < 4; quadrant++) {
            if (realQuads[quadrant]!=null) {
            for (int point = 0; point < 4; point++) {
                if (inBoundary(quadranten[quadrant], circleSquare[point])) {
                        realQuads[quadrant].junctionsInRadius(xPoint, yPoint, radius, count);
                        break;
                    }
                }
            }
        }
        return count;
    }

        /*
        //angenommen topRight

        if (Math.abs(xPoint - Origin.getX()) <= radius){                                                                //Distanzu <= radius
            if (Math.abs(yPoint - Origin.getY()) <= radius) {
                if (xPoint >= Origin.getX()) {
                    if(yPoint >= Origin.getY()){
                        topLeft.junctionsInRadius(xPoint, yPoint, radius, count);
                        topRight.junctionsInRadius(xPoint, yPoint, radius, count);
                    }
                }














                else {
                    topRight.junctionsInRadius(xPoint, yPoint, radius, count);
                }
                else{
                    if (xPoint >= Origin.getX()) {
                        topLeft.junctionsInRadius(xPoint, yPoint, radius, count);
                    } else {
                        topRight.junctionsInRadius(xPoint, yPoint, radius, count);
                    }
                }

            }
        }

        if (Math.abs(yPoint - Origin.getY()) >= radius){                                                                //Distanzu <= radius
            if (yPoint >= Origin.getY()){
                botLeft.junctionsInRadius(xPoint, yPoint, radius, count);
            }
            else{
                botRight.junctionsInRadius(xPoint, yPoint, radius, count);
            }
        }


        if (xPoint - Origin.getX() >= radius){
            topLeft.junctionsInRadius(xPoint, yPoint, radius, count);
        }
        else if {xPoint - Origin.getX()

        }






        if (junction != null) {
            NodeData check = new NodeData(null, xPoint, yPoint, null);

            if (inBoundary(topLeft, check)) {
                count = topLeft.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (inBoundary(topRight, check)) {
                count = topRight.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (inBoundary(botLeft, check)) {
                count = botLeft.junctionsInRadius(xPoint, yPoint, radius);
                countAirports += count[0];
                countTrainstation += count[1];
            } else if (inBoundary(botRight, check)) {
                count = botRight.junctionsInRadius(xPoint, yPoint, radius);
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
