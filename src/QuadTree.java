public interface QuadTree {
    QuadTreeRoot add(NodeData junction);
    void junctionsInRadius(double x, double y, double radius, int[] count);
    Point getTopLeftPoint();
    Point getBotRightPoint();
}
