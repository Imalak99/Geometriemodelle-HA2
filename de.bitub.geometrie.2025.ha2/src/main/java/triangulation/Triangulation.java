package triangulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import javafx.scene.shape.TriangleMesh;
import model.Face;
import model.Point;
import model.Polyhedron;
import projection.PolygonProjection;

public class Triangulation {

	public static TriangleMesh createTriangleMesh(Polyhedron polyhedron) {
		System.out.println("Methode createTriangleMesh aufgerufen");
		TriangleMesh mesh = new TriangleMesh();

		List<Face> faces = polyhedron.getFaces();
		System.out.println("Liste der Faces: " + faces);

		Map<Point, Integer> pointIndexMap = new HashMap<>();
		int indexCounter = 0;

		for (Face face : faces) {

			List<Point> boundary = face.getOrderedBoundaryPoints();

			List<List<Point>> boundaries = List.of(boundary); // aktuell keine Löcher
			System.out.println("Das sind die Boundariepoints des Face:\n " + boundaries);
			List<List<PolygonPoint>> points2D = new ArrayList<>();

			Map<PolygonPoint, Point> pointMap = PolygonProjection.projectTo2D(boundaries, points2D);
			List<PolygonPoint> projectedPoints = points2D.get(0);

			Polygon polygon = new Polygon(projectedPoints);
			Poly2Tri.triangulate(polygon);
			List<DelaunayTriangle> triangles = polygon.getTriangles();

			for (DelaunayTriangle triangle : triangles) {
				for (PolygonPoint pp : new PolygonPoint[] { (PolygonPoint) triangle.points[0],
						(PolygonPoint) triangle.points[1], (PolygonPoint) triangle.points[2] }) {
					Point p = pointMap.get(pp);
					if (!pointIndexMap.containsKey(p)) {
						mesh.getPoints().addAll((float) p.xyz[0], (float) p.xyz[1], (float) p.xyz[2]);
						pointIndexMap.put(p, indexCounter++);
					}
				}

				// Triangle indices (JavaFX needs vertexIndex, texCoordIndex) → we use
				// texCoordIndex = 0
				int i0 = pointIndexMap.get(pointMap.get((PolygonPoint) triangle.points[0]));
				int i1 = pointIndexMap.get(pointMap.get((PolygonPoint) triangle.points[1]));
				int i2 = pointIndexMap.get(pointMap.get((PolygonPoint) triangle.points[2]));

				mesh.getFaces().addAll(i0, 0, i1, 0, i2, 0);
			}

		}

		mesh.getTexCoords().setAll(0, 0);

		return mesh;
	}

}