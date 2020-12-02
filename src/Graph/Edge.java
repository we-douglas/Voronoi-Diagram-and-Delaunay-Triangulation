package Graph;

public class Edge {


    public Point start;
    public Point end;

    public Point right;
    public Point left;

    public Edge neighbor;

    public double slope;
    public double yint;

    public Edge(Point first, Point left, Point right) {
        start = first;end = null;
        this.left = left;this.right = right;
        slope = (right.x - left.x)/(left.y - right.y);
        yint = ((left.y+right.y)/2)- slope*((right.x + left.x)/2);
    }

    public String toString() {
        return start + ", " + end;
    }
}
