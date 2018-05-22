public class QuadTreeRoot implements QuadTree {

    private Point topLeftPoint;
    private Point botRightPoint;
    private QuadTree topLeft,topRight,botLeft,botRight;                             //QuadTree interface


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

    public QuadTree add(NodeData junction) {
        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;
        Point newOrigin = Origin();

        if (xMittelwert <= junction.X()) {                                                                               ////Kontrolle auf zu untersuchendem Quadrant
            if (yMittlewert <= junction.Y()) {
                if (topRight == null) {                                                                                 //wenn da noch kein Knoten -> erzeuge leaf und geh da hinein
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

        return new QuadTreeRoot(topLeftPoint, botRightPoint, topLeft, topRight, botLeft, botRight);                     //neuer QuadTreeRoot
    }

    /*
    //Schaut um der Knoten in den Quadranten passt.
    private boolean inBoundary(QuadTree quadrant, Point point){
        if (quadrant == null){
            return false;
        }

        double x = point.getX();
        double y = point.getY();
        if (quadrant.topLeftPoint.getX() < x && quadrant.botRightPoint.getX() > x){
            if (quadrant.topLeftPoint.getY() > y && quadrant.botRightPoint.getY() < y){
                return true;
            }
        }
        return false;
    }
    */

    private Point Origin(){
        double xMittelwert = (topLeftPoint.getX() + botRightPoint.getX()) / 2;
        double yMittlewert = (topLeftPoint.getY() + botRightPoint.getY()) / 2;

        return new Point(xMittelwert, yMittlewert);
    }

    private boolean intersectQuadrant(QuadTree quadrant, double x, double y, double radius){
        if (quadrant==null){
            return false;
        }

        double calcX;
        double calcY;

        double TLX = quadrant.topLeftPoint.getX();
        double TLY = quadrant.topLeftPoint.getY();
        double BRX = quadrant.botRightPoint.getX();
        double BRY = quadrant.botRightPoint.getY();


        if (x <= TLX){
            calcX = TLX;
        }
        else if(x >= BRX){
            calcX = BRX;
        }
        else {
            calcX = x;
        }

        if (y <= TLY){
            calcY = TLY;
        }
        else if(y >= BRY){
            calcY = BRY;
        }
        else {
            calcY = y;
        }

        double distance = Math.sqrt(Math.pow(x - calcX, 2) + Math.pow(y - calcY, 2));      //Satz von Pythagoras
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

        for (int i = 0; i < 4; i++) {
            if (intersectQuadrant(realQuads[i], xPoint,yPoint, radius)){
                realQuads[i].junctionsInRadius(xPoint, yPoint, radius, count);
            }
        }
    }
}
