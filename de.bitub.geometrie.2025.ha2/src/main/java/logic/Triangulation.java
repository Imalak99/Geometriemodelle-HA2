package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import model.Face;
import model.HalfEdge;
import model.Point;
import projection.PolygonProjection;

public class Triangulation {

	/**
	 * Trianguliert eine beliebige Face und gibt eine Liste mit den Dreiecken zurück
	 * 
	 * @param face
	 * @return Liste der Dreiecke
	 */
	public static List<List<Point>> triangulateFace(Face face) {
		// Schritt 1: 3D-Ränder sammeln
		List<List<Point>> boundaries3d = new ArrayList<>();
		boundaries3d.add(face.getOrderedBoundaryPoints());
		for (HalfEdge holeStart : face.getHoles()) {
			if (holeStart == null)
				continue;
			boundaries3d.add(Face.getPointsFromStartHalfEdge(holeStart));
		}
		// Schritt 2: Projektion in 2D
		List<List<PolygonPoint>> points2D = new ArrayList<>();
		Map<PolygonPoint, Point> pointMap = PolygonProjection.projectTo2D(boundaries3d, points2D);
		// Schritt 3: Poly2Tri
		Polygon polygon2d = new Polygon(points2D.get(0));
		for (int i = 1; i < points2D.size(); i++) {
			polygon2d.addHole(new Polygon(points2D.get(i)));
		}
		Poly2Tri.triangulate(polygon2d);
		// Schritt 4: Rückabbildung der Dreiecke in 3D
		List<List<Point>> triangles3D = new ArrayList<>();
		for (DelaunayTriangle triangle2D : polygon2d.getTriangles()) {
			List<Point> triangle3D = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				triangle3D.add(pointMap.get(triangle2D.points[i]));
			}
			triangles3D.add(triangle3D);
		}
		return triangles3D;
	}

}