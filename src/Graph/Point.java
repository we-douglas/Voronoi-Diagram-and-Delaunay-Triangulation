package Graph;

public class Point implements Comparable <Point>{

    public double x;
    public double y;
    public boolean cpy;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;
        cpy=true;
    }

    public String toString() {return "("+x +","+y+")";}

    public int compareTo (Point other) {
        if (this.y == other.y) {

            if (this.x == other.x) return 0;
            else if (this.x > other.x){if(this.cpy){this.y=this.y+0.001;this.cpy=false;} return 1;}
            else {if(!other.cpy){this.y=this.y+0.001;this.cpy=false;} return 0;}

        }
        else if (this.y > other.y) { return 1; }
        else { return -1; }
    }


}
