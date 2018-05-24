public interface QuadTree {
    //Kommentar
    QuadTreeRoot add(NodeData junction);

    //Kommentar
    void junctionsInRadius(double x, double y, double radius, int[] count);
    Point getTopLeftPoint();
    Point getBotRightPoint();

}
