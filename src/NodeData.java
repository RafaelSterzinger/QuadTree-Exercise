public class NodeData {
    private String name,type;
    private double x,y;

    public NodeData(String name,  double x, double y, String type) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double X() {
        return x;
    }

    public double Y() {
        return y;
    }

    public String getType() {
        return type;
    }
}
