package tests;

import java.util.Arrays;
import java.util.List;

import model.Face;
import model.HalfEdge;
import model.HalfEdgeUtil;
import model.Point;
import model.Polyhedron;

public class TriangulationTest {

	public static void main(String[] args) {

		Polyhedron cube = Polyhedron.cube();
//		System.out.println(cube);
		Polyhedron faceWithHole = faceWithHole();
//		System.out.println(faceWithHole);
		Polyhedron triangleFace = triangleFace();
//		System.out.println(triangleFace);
		Polyhedron multiPointsFace = multiPointsFace();
		System.out.println(multiPointsFace);

	}

	public static Polyhedron faceWithHole() {
		System.out.println("Methode faceWithHole aufgerufen");

		// Alle Punkte erzeugen
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(3, 0, 0);
		Point p2 = new Point(3, 3, 0);
		Point p3 = new Point(0, 3, 0);
		Point p4 = new Point(1, 1, 0);
		Point p5 = new Point(2, 1, 0);
		Point p6 = new Point(2, 2, 0);
		Point p7 = new Point(1, 2, 0);
		// List der aueßeren und inneren Punkte der Fläche mit Loch
		List<Point> outer = Arrays.asList(p0, p3, p2, p1); // Außenkontur (Counterclockwise)
		List<Point> inner = Arrays.asList(p4, p5, p6, p7); // Innenkontur (Loch) (Clockwise)
		// Starthalfedge der äußeren und inneren Kanten erzeugen
		HalfEdge outerStart = HalfEdgeUtil.buildPolygon(outer);
		HalfEdge holeStart = HalfEdgeUtil.buildPolygon(inner);

		// Alle HalfEdges sammeln
		List<HalfEdge> edges = HalfEdge.allHalfEdgesPerFace(outerStart);
		edges.addAll(HalfEdge.allHalfEdgesPerFace(holeStart));
//		System.out.println(edges);

		// Face mit äußerer Kante erzeugen
		Face face = new Face(outerStart);
		List<Face> faces = Arrays.asList(face);
		// Lochkante setzen
		face.setHole(holeStart);
		Polyhedron faceWithHole = new Polyhedron(faces);
		faceWithHole.setEdges(edges);
		faceWithHole.setPoints(outer);
		faceWithHole.setPoints(inner);

		return faceWithHole;

	}

	public static Polyhedron triangleFace() {
		System.out.println("Methode faceWithHole aufgerufen");

		// Alle Punkte erzeugen
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(3, 0, 0);
		Point p2 = new Point(3, 3, 0);
		// List der aueßeren und inneren Punkte der Fläche mit Loch
		List<Point> outer = Arrays.asList(p0, p2, p1); // Außenkontur (Counterclockwise)
		// Starthalfedge der äußeren und inneren Kanten erzeugen
		HalfEdge outerStart = HalfEdgeUtil.buildPolygon(outer);

		// Alle HalfEdges sammeln
		List<HalfEdge> edges = HalfEdge.allHalfEdgesPerFace(outerStart);

		// Face mit äußerer Kante erzeugen
		Face face = new Face(outerStart);
		List<Face> faces = Arrays.asList(face);
		// Lochkante setzen
		Polyhedron triangleFace = new Polyhedron(faces);
		triangleFace.setEdges(edges);
		triangleFace.setPoints(outer);

		return triangleFace;

	}

	public static Polyhedron multiPointsFace() {
		System.out.println("Methode faceWithHole aufgerufen");

		// Alle Punkte erzeugen
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(1, 1, 0);
		Point p2 = new Point(3, 0, 0);
		Point p3 = new Point(3, 3, 0);
		Point p4 = new Point(2, 4, 0);
		Point p5 = new Point(0, 3, 0);
		// List der aueßeren und inneren Punkte der Fläche mit Loch
		List<Point> outer = Arrays.asList(p0, p5, p4, p3, p2, p1); // Außenkontur (Counterclockwise)
		// Starthalfedge der äußeren und inneren Kanten erzeugen
		HalfEdge outerStart = HalfEdgeUtil.buildPolygon(outer);

		// Alle HalfEdges sammeln
		List<HalfEdge> edges = HalfEdge.allHalfEdgesPerFace(outerStart);

		// Face mit äußerer Kante erzeugen
		Face face = new Face(outerStart);
		List<Face> faces = Arrays.asList(face);
		// Lochkante setzen
		Polyhedron multiPointsFace = new Polyhedron(faces);
		multiPointsFace.setEdges(edges);
		multiPointsFace.setPoints(outer);

		return multiPointsFace;

	}

}
