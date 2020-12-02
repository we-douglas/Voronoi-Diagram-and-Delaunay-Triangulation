package Graph;

import Voronoi.Action;
public class Arc {

    public static int Focal = 0;
    public static int Vert = 1;


    public int type;
    public Point point;
    public Edge edge;
    public Action action;
    public Arc parent;
    public Arc left;
    public Arc right;

    public Arc() {type = Vert; }
    public Arc(Point point) {this.point = point;type = Focal;}




    public void setRLeaf(Arc line) {
        right = line;
        line.parent = this;
    }


    public void setLLeaf(Arc point) {
        left = point;
        point.parent = this;
    }


    public static Arc getLParent(Arc point) {
        Arc parent = point.parent;
        if (parent == null) return null;
        Arc last = point;

        while (parent.left == last) {
            if(parent.parent == null){return null;}
            last = parent;
            parent = parent.parent;
        }


        return parent;
    }

    // returns the closest parent on the right
    public static Arc getRightParent(Arc p) {
        Arc parent = p.parent;
        if (parent == null) return null;
        Arc last = p;
        while (parent.right == last) {
            if(parent.parent == null) return null;
            last = parent;
            parent = parent.parent;
        }
        return parent;
    }

    // returns closest site (focus of another arcline) to the left
    public static Arc getChild(Arc point, int i) {
        if (point == null) return null;
        if(i==1){
            Arc child = point.left;
            while(child.type == Vert){ child = child.right;}
            return child;
        }
        else{
            Arc child = point.right;
            while(child.type == Vert) child = child.left;
            return child;}
    }




}
