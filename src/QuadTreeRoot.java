public class QuadTreeRoot implements QuadTree {

    private Point topLeftPoint;
    private Point botRightPoint;
    private QuadTree topLeft,topRight,botLeft,botRight;                             //QuadTree interface

    //creates an empty QuadTreeRoot with a given dimension
    public QuadTreeRoot(Point topLeftPoint, Point botRightPoint){
        this.topLeftPoint = topLeftPoint;
        this.botRightPoint = botRightPoint;
    }


    public QuadTreeRoot(Point topLeftPoint, Point botRightPoint, QuadTree topLeft, QuadTree topRight, QuadTree botLeft, QuadTree botRight) {
        this.topLeftPoint = topLeftPoint;
        this.botRightPoint = botRightPoint;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.botLeft = botLeft;
        this.botRight = botRight;
    }

    //adds a junction to the QuadTree at the corresponding position
    public QuadTreeRoot add(NodeData junction) {
        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;
        Point newOrigin = Origin();

        if (xMittelwert <= junction.X()) {                                                                              //checks for position
            if (yMittlewert <= junction.Y()) {
                if (topRight == null) {                                                                                 //if there's no node -> create a leaf
                    topRight = new QuadTreeLeaf(junction,new Point(newOrigin.getX(), topLeftPoint.getY()), new Point(botRightPoint.getX(), newOrigin.getY()));
                }
                else{
                    topRight = topRight.add(junction);
                }
            } else {
                if (botRight == null) {
                    botRight = new QuadTreeLeaf(junction,newOrigin, botRightPoint);
                }
                else{
                    botRight = botRight.add(junction);
                }
            }
        }
        else {
            if (yMittlewert <= junction.Y()) {

                if (topLeft == null) {
                    topLeft = new QuadTreeLeaf(junction,topLeftPoint, newOrigin);
                }
                else{
                    topLeft = topLeft.add(junction);
                }
            } else {
                if (botLeft == null) {
                    botLeft = new QuadTreeLeaf(junction,new Point(topLeftPoint.getX(), newOrigin.getY()), new Point(newOrigin.getX(), botRightPoint.getY()));
                }
                else{
                    botLeft = botLeft.add(junction);
                }
            }
        }

        return new QuadTreeRoot(topLeftPoint, botRightPoint, topLeft, topRight, botLeft, botRight);                     //creates a new QuadTreeRoot with the changed subQuadTrees and returns it
    }

    //calculates the center point of the QuadTree
    private Point Origin(){
        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;

        return new Point(xMittelwert, yMittlewert);
    }


    private Point projectPoint (QuadTree quadrant, double x, double y){
        double calcX;
        double calcY;

        double TLX = quadrant.getTopLeftPoint().getX();
        double TLY = quadrant.getTopLeftPoint().getY();
        double BRX = quadrant.getBotRightPoint().getX();
        double BRY = quadrant.getBotRightPoint().getY();

        if (x <= TLX){
            calcX = TLX;
        }
        else if(x >= BRX){
            calcX = BRX;
        }
        else {
            calcX = x;
        }

        if (y <= BRY){
            calcY = BRY;
        }
        else if(y >= TLY){
            calcY = TLY;
        }
        else {
            calcY = y;
        }
        return new Point(calcX, calcY);
    }

    //checks if the circle intersect a given quadrant
    private boolean intersectQuadrant(QuadTree quadrant, double x, double y, double radius) {
        if (quadrant == null) {
            return false;
        }
        Point projectedPoint = projectPoint(quadrant, x, y);

        double distance = Math.sqrt((x - projectedPoint.getX()) * (x - projectedPoint.getX()) + (y - projectedPoint.getY()) * (y - projectedPoint.getY()));      //Satz von Pythagoras
        if (distance <= radius){
            return true;
        }
        return false;
    }

    public void junctionsInRadius(double xPoint, double yPoint, double radius, int[] count) {

        QuadTree[] realQuads = new QuadTree[4];
        realQuads[0] = topLeft;
        realQuads[1] = topRight;
        realQuads[2] = botLeft;
        realQuads[3] = botRight;

        //for each intersecting quadrant we recurse into that quadrant until we get to leaf where we check if it is within the distance
        for (int i = 0; i < 4; i++) {
                if (intersectQuadrant(realQuads[i], xPoint, yPoint, radius)) {
                    realQuads[i].junctionsInRadius(xPoint, yPoint, radius, count);
                }
        }
    }

    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    public Point getBotRightPoint() {
        return botRightPoint;
    }
}
