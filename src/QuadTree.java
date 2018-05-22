public interface QuadTree {
    Point topLeftPoint = null;
    Point botRightPoint = null;
    QuadTree add(NodeData junction);
    void junctionsInRadius(double x, double y, double radius, int[] count);
}
