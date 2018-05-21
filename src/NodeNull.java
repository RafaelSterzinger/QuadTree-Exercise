public class NodeNull implements Node {

    @Override
    public void add(String name, double x, double y, String type) {
        this = new NodeData(name, x, y, type);
    }

    @Override
    public double X() {
        return 0;
    }

    @Override
    public double Y() {
        return 0;
    }

    @Override
    public String Type() {
        return null;
    }

    @Override
    public void setX(double x) {
    }

    @Override
    public void setY(double y) {
    }
}
