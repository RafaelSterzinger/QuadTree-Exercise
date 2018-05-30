public interface QuadTree {
    //returns a changed subTree with added nodes
    QuadTreeRoot add(NodeData junction);

    //calculates junctions from a point (x, y) within a set radius and saves them in the global array count
    void junctionsInRadius(double x, double y, double radius, int[] count);

    //returns the corresponding points of the QuadTree
    Point getTopLeftPoint();
    Point getBotRightPoint();

}
