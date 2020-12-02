package Voronoi;

import Graph.*;

public class Action implements Comparable <Action>{

    public Point point;
    public static int SITE = 0;
    public static int CIRCLE = 1;
    public Arc circleEventArcLine;
    public int action;

    public Action(Point point, int action) {
        this.point = point;

        this.action = action;
        this.circleEventArcLine = null; // stays null until we need a circle event //
    }
    public int compareTo(Action other) {return this.point.compareTo(other.point);} // comparator to order our events//

}
