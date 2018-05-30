public class QuadTreeLeaf implements QuadTree{
    private NodeData junction;
    private Point topLeftPoint;
    private Point botRightPoint;

    public QuadTreeLeaf(NodeData junction, Point topLeftPoint, Point botRightPoint) {
        this.junction = junction;
        this.topLeftPoint = topLeftPoint;
        this.botRightPoint = botRightPoint;
    }

    @Override
    //creates a new QuadTree inplace of the Leaf and then adds the old leaf as well as the new junction to the QuadTree
    public QuadTreeRoot add(NodeData junction) {
        QuadTreeRoot subRoot = new QuadTreeRoot(topLeftPoint, botRightPoint);
        subRoot = subRoot.add(this.junction);
        subRoot = subRoot.add(junction);

        return subRoot;
    }

    public void junctionsInRadius(double x, double y, double radius, int[] count){
        double distance = Math.sqrt((x - junction.X()) * (x - junction.X()) + (y - junction.Y()) * (y - junction.Y()));      //Satz von Pythagoras
        if (distance <= radius){
            if (junction.getType() == Type.TRAINSTATION) {
                count[1]++;
            }
            else {
                count[0]++;
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