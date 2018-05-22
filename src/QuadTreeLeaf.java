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
    public QuadTree add(NodeData junction) {

        QuadTreeRoot subRoot = new QuadTreeRoot(topLeftPoint, botRightPoint);
        subRoot.add(junction);

        return subRoot;
    }

    public void junctionsInRadius(double x, double y, double radius, int[] count){
        double distance = Math.sqrt(Math.pow(x - junction.X(), 2) + Math.pow(y - junction.Y(), 2));      //Satz von Pythagoras
        if (distance <= radius){
            if (junction.getType().equals("TRAINSTATION")) {
                count[1]++;
            }
            else {
                count[0]++;
            }
        }
    }
}