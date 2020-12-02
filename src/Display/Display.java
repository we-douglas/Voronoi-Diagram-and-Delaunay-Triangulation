package Display;

import Delaunay.Delaunay;
import Graph.Edge;
import Graph.Graph;
import Graph.Point;
import Voronoi.Voronoi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;



public class Display{
    private Graph graph;



    public Display(Graph graph) {
        this.graph =graph;
        System.out.println("Display Ready");
    }


    public void drawPoints(){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(.01);

        for (Point p: graph.pointList) {
            StdDraw.point(p.x, p.y);
        }
    }

    public void drawEdges(){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(.002);
        for (Edge e: graph.edgeLineList) {
            StdDraw.line(e.start.x, e.start.y, e.end.x, e.end.y);
        }
    }

    public void onClick() throws InterruptedException {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(.01);

        Boolean det =true;
        while (det) {

            if (StdDraw.isMousePressed()) {
                StdDraw.waitForMouse();
                det=false;
                double x=StdDraw.mouseX();
                double y=StdDraw.mouseY();
                graph.pointList.add(new Point(x,y));




            }


        }
    }


    public void drawGraph() throws InterruptedException, IOException {

        StdDraw.setBackgroundColor(StdDraw.CYAN);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 10));
        StdDraw.setPenColor(Color.BLACK);

        while (true) {
            onClick();
            graph.edgeLineList=new ArrayList<Edge>();
            Voronoi v =new Voronoi(graph);
            StdDraw.setBackgroundColor(Color.CYAN);

            drawPoints();
            drawEdges();

            Delaunay d =new Delaunay(graph);




        }


        // delaunay(pointList,max);



       // new Voronoi(newPoint);


    }






}
