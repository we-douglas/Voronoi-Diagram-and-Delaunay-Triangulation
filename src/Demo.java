import Display.Display;
import Graph.Graph;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws InterruptedException, IOException {

        Graph graph = new Graph();
        Display display = new Display(graph);
        display.drawGraph();



    }
}
