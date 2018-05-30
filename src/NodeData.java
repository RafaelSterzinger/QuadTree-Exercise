public class NodeData {
    private String name;
    Type type;
    private double x,y;

    public NodeData(String name,  double x, double y, String type) {
        this.name = name;
        this.x = x;
        this.y = y;
        if(type.equals("AIRPORT")) {
            this.type = Type.AIRPORT;
        }
        else {
            this.type = Type.TRAINSTATION;
        }
    }

    public double X() {
        return x;
    }

    public double Y() {
        return y;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
