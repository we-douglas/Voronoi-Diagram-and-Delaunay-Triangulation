package Delaunay;

import Display.StdDraw;
import Graph.*;
import Graph.Point;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Delaunay {
    private Graph graph;

    public Delaunay(Graph graph) {
        this.graph=graph;
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.001);

        Triangulation(graph.pointList);


    }


    private void Triangulation(ArrayList<Point> points){
        boolean newAngle;
        for(int i=0; i<points.size(); i++){
            for(int i1 = i+1; i1<points.size(); i1++) {
                for(int i2 = i1+1;i2<points.size(); i2++) {
                    newAngle = true;
                    for(int a = 0; a <points.size();a++) {
                        if (a == i || a == i1 || a == i2) continue;
                        if (chkRadius(points.get(i), points.get(i1), points.get(i2),points.get(a))) {
                            newAngle = false;
                            break;
                        }}
                    if (newAngle){


                        drawT(points.get(i), points.get(i1), points.get(i2));

                    }
                }}}

    }
    public boolean chkRadius(Point a, Point b, Point c, Point nxt) {
        double x1 = a.x, x2 = b.x, x3 = c.x;
        double y1 = a.y, y2 = b.y, y3 = c.y;

        double[] q=buildCir((float) x1,(float) y1,(float) x2,(float) y2,(float) x3,(float) y3);
        if(q[2]==-1.0){return true;} // if the points are in a line we toss //
        double circle_x = q[0];
        double circle_y = q[1];

        double rad=q[2];
        if ((nxt.x - circle_x) * (nxt.x - circle_x) + (nxt.y - circle_y) * (nxt.y - circle_y) <= rad * rad) {
            return true;
        } else
            return false;
    }
    public void drawT(Point point1,Point point2, Point point3){
        StdDraw.line(point1.x,point1.y,point2.x,point2.y);
        StdDraw.line(point1.x,point1.y,point3.x,point3.y);
        StdDraw.line(point3.x,point3.y,point2.x,point2.y);
    }




    public double[] buildCir(float x1, float y1, float x2, float y2, float x3, float y3) {
        float a = x1 - x2;float b = x1 - x3;float c = y1 - y2;float d = y1 - y3;
        float e = y3 - y1;float f = y2 - y1;float g = x3 - x1;float l = x2 - x1;

        if((2 * ((e) * (a) - (f) * (b)))==0){return new double[]{0, 0, -1};}
        float h = -(((((float)(Math.pow(x1, 2) - Math.pow(x3, 2)))) * (c) + (((float)(Math.pow(y1, 2) - Math.pow(y3, 2)))) * (c) + (((float)(Math.pow(x2, 2) -Math.pow(x1, 2)))) * (d) + (((float)(Math.pow(y2, 2) -Math.pow(y1, 2)))) * (d)) / (2 * ((g) * (c) - (l) * (d))));
        float k = -(((((float)(Math.pow(x1, 2) - Math.pow(x3, 2)))) * (a) + (((float)(Math.pow(y1, 2) - Math.pow(y3, 2)))) * (a) + (((float)(Math.pow(x2, 2) -Math.pow(x1, 2)))) * (b) + (((float)(Math.pow(y2, 2) -Math.pow(y1, 2)))) * (b))  / (2 * ((e) * (a) - (f) * (b))));
        float sqr_of_r = h * h + k * k -(-(float)Math.pow(x1, 2) - (float)Math.pow(y1, 2)-2 * (((((float)(Math.pow(x1, 2) - Math.pow(x3, 2))))
                * (c) + (((float)(Math.pow(y1, 2) - Math.pow(y3, 2)))) * (c) + (((float)(Math.pow(x2, 2) -Math.pow(x1, 2))))*(d)
                + (((float)(Math.pow(y2, 2) -Math.pow(y1, 2)))) * (d)) / (2 * ((g) * (c) - (l) * (d)))) * x1 - 2 *
                (((((float)(Math.pow(x1, 2) - Math.pow(x3, 2)))) * (a) + (((float)(Math.pow(y1, 2) - Math.pow(y3, 2))))
                        * (a) + (((float)(Math.pow(x2, 2) -Math.pow(x1, 2)))) * (b) + (((float)(Math.pow(y2, 2)
                        -Math.pow(y1, 2)))) * (b))  / (2 * ((e) * (a) - (f) * (b)))) * y1);
        double r = Math.sqrt(sqr_of_r);
        return new double[]{h,k,r};
    }

}
