Overview:
To run compile and execute the demo.Java file within the main directory. A java swing UI should appear, and this will act as the interface for the demo. Clicking anywhere on the plane will add a point. If more than two points are added to the plane a respective Voronoi diagram will be drawn. Continuing to add points (via clicking) will update this diagram dynamically. If three or more points exist on the plane a Delaunay triangulation will be drawn, this triangulation is also updated dynamically with the addition of new points.  

Voronoi Diagram:
Each black point on the plane, added by clicking, represents the seed of a region called a Voronoi cell. The boundary of these cells are represented on the graph by the black edges. Voronoi diagrams are interesting in that any point within a Voronoi cell is closer to its respective seed than any other seed on the plane. For this demo, I implemented Fortune's plane sweep algorithm to construct a Voronoi diagram in O(n log n) time from the users input.

Delaunay Triangulation:
The Delaunay triangulation for the set of added points on the plane is represented by red lines. Each Delaunay triangulation is the dual graph of the Voronoi Diagram The current implementation of the Delaunay triangulation uses an incremental approach to construct the Triangulation however a more efficient divide and conquer algorithm will be implemented in future updates.
