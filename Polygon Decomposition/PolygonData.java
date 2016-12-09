public class PolygonData {
    public double x;
    public double y;
    public int node;

    public void initPolygonData () {
        this.node = -1;
        this.x = Double.NEGATIVE_INFINITY;
        this.y = Double.NEGATIVE_INFINITY;
    }

    public void setNode (int inputNode) {
        this.node = inputNode;
    }

    public void setX (double inputX) {
        this.x = inputX;
    }

    public void setY (double inputY) {
        this.y = inputY;
    }
}
