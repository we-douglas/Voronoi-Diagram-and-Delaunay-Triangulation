package Voronoi;

import Graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Voronoi {

    public ArrayList<String> dul;
    static PriorityQueue<Action> actionList;
    Arc bst; // bst for line
    double sweepY; // pos of the sweep line
    public ArrayList<Point> tri;
    double winMax = 1; // used to scale the window

    private Graph graph;

    public Voronoi(Graph graph){
        this.graph=graph;
        buildVor();
    }

    private void buildVor() {
        sortEventList();
        while (actionList.size()>0){
            Action currentAction = actionList.remove();
            sweepY = currentAction.point.y;
            if (currentAction.action == Action.SITE){
                eventSite(currentAction.point);
            }
            else{
                eventCircle(currentAction);
            }
        }

        sweepY =10* winMax;   // set sweep to be out of bounds //

        findEdgeEnds(bst);
    }



    private void findEdgeEnds(Arc arcLine) {

        if (arcLine.type == Arc.Vert) {
            double projectedX = getXEdgeIntercept(arcLine);
            double projectedY = (projectedX* arcLine.edge.slope)+ arcLine.edge.yint;
            arcLine.edge.end = new Point(projectedX, projectedY);
            graph.edgeLineList.add(arcLine.edge);
            findEdgeEnds(arcLine.left);
            findEdgeEnds(arcLine.right);
        }

    }
    private void eventSite(Point point) {
        if (bst==null) {bst = new Arc(point); }
        else{
            Arc rootArc = bst;
            double xx = 0;
            while (rootArc.type == Arc.Vert) {
                xx = getXEdgeIntercept(rootArc);
                if (xx>point.x) rootArc = rootArc.left;
                else rootArc = rootArc.right;
            }
            Arc arc = rootArc;
            if (arc.action !=null) {
                actionList.remove(arc.action);
                arc.action =null; }


            Edge rightDE=createDangleEdge(point,arc);
            Arc arcA = new Arc(arc.point);
            Arc arcB = new Arc(arc.point);
            Arc arcC = new Arc(point);
            arc.setLLeaf(arcA);
            arc.setRLeaf(new Arc());
            arc.right.edge = rightDE;
            arc.right.setRLeaf(arcB);
            arc.right.setLLeaf(arcC);
            checkCircleEvent(arcA);
            checkCircleEvent(arcB);
        }}
    private Edge createDangleEdge(Point point, Arc arc ){
        arc.type = Arc.Vert;
        Point startSite = new Point(point.x, getY(arc.point, point.x));
        Edge eLeft = new Edge(startSite, arc.point, point);
        Edge eRight = new Edge(startSite, point, arc.point);

        eRight.neighbor = eLeft;
        eLeft.neighbor = eRight;
        arc.edge = eLeft;
        return eRight;
    }
    private void sortEventList(){
        actionList = new PriorityQueue<>();
        for (Point p : graph.pointList) {
            actionList.add(new Action(p, Action.SITE));}
    }
    private void eventCircle(Action e) {
        Arc p1 = e.circleEventArcLine;
        Arc xl = Arc.getLParent(p1);
        Arc xr = Arc.getRightParent(p1);
        Arc p0 = Arc.getChild(xl,1);
        Arc p2 = Arc.getChild(xr,0);


        if (p0.action != null) {
            actionList.remove(p0.action);p0.action = null;}
        if (p2.action != null) {
            actionList.remove(p2.action);p2.action = null;}

        // The New Vertex//
        Point p = new Point(e.point.x, getY(p1.point, e.point.x));

        // End EdgeLines//
        xl.edge.end = p;
        xr.edge.end = p;
        graph.edgeLineList.add(xl.edge);
        graph.edgeLineList.add(xr.edge);


        Arc higher = new Arc();
        Arc arc = p1;
        while (arc != bst) { arc = arc.parent;
            if (arc == xl){ higher = xl;}
            if (arc == xr) {higher = xr;}
        }
        higher.edge = new Edge(p, p0.point, p2.point);

        Arc older = p1.parent.parent;
        if (p1.parent.left == p1) {
            if(older.right == p1.parent) older.setRLeaf(p1.parent.right);
            if(older.left == p1.parent) older.setLLeaf(p1.parent.right);
        }
        else {
            if(older.left == p1.parent) older.setLLeaf(p1.parent.left);
            if(older.right == p1.parent) older.setRLeaf(p1.parent.left);
        }
        p1.parent = null;

        checkCircleEvent(p0);
        checkCircleEvent(p2);
    }
    private void checkCircleEvent(Arc b) {
        Arc lp = Arc.getLParent(b);
        Arc rp = Arc.getRightParent(b);

        if (lp == null || rp == null) return;

        Arc a = Arc.getChild(lp,1);
        Arc c = Arc.getChild(rp,0);

        if (a == null || c == null || a.point == c.point) return;
        if (((b.point.x-a.point.x)*(c.point.y-a.point.y) - (b.point.y-a.point.y)*(c.point.x-a.point.x)) <= 0) return;


        Edge sX=lp.edge;
        Edge sY=rp.edge;
        Point start=new Point((sY.yint - sX.yint)/(sX.slope - sY.slope), (sX.slope*(sY.yint - sX.yint)/(sX.slope - sY.slope) + sX.yint));
        if (start == null) return;

        // compute radius
        double dx = b.point.x - start.x;
        double dy = b.point.y - start.y;
        double d = Math.sqrt((dx*dx) + (dy*dy));
        if (start.y + d < sweepY) return;

        Point ep = new Point(start.x, start.y + d);



        Action e = new Action(ep, Action.CIRCLE);
        e.circleEventArcLine = b;
        b.action = e;
        actionList.add(e);
    }
    private double getY(Point point, double inputX) {
        double dp = 2*(point.y - sweepY);
        double a1 = 1/dp;
        double b1 = -2*point.x/dp;
        double c1 = (point.x*point.x + point.y*point.y - sweepY * sweepY)/dp;
        return (a1*inputX*inputX + b1*inputX + c1);
    }
    private double getXEdgeIntercept(Arc par) {
        Arc left = Arc.getChild(par,1);
        Arc right = Arc.getChild(par,0);

        Point p = left.point;
        Point r = right.point;
        // double tempY=(((ycurr*ycurr*-1)+(r.y*r.y)+(r.x*x)-(2*x*tempX)+(tempX*tempX))/((-2*ycurr)+(2*y)));
        //double tempX=(((ycurr*ycurr*-1)+(y*y)+(x*x)-(2*x*tempX)+(tempX*tempX))/((-2*ycurr)+(2*y)));



        double a2 = 1/( 2*(r.y - sweepY));
        double b2 = -2*r.x/( 2*(r.y - sweepY));


        double a = (1/(2*(p.y - sweepY)))-a2;
        double b = ( -2*p.x/(2*(p.y - sweepY)))-b2;
        double c = ((p.x*p.x + p.y*p.y - sweepY * sweepY)/(2*(p.y - sweepY)))-( (r.x*r.x + r.y*r.y - sweepY * sweepY)/( 2*(r.y - sweepY)));

        double disc = b*b - 4*a*c;
        double x1 = (-b + Math.sqrt(disc))/(2*a);
        double x2 = (-b - Math.sqrt(disc))/(2*a);


        double ry;
        if (p.y >= r.y) ry = Math.max(x1, x2);
        else ry = Math.min(x1, x2);

        return ry;
    }
}
