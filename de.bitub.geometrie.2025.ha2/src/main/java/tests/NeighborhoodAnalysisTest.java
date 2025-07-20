package tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import logic.NeighborhoodAnalysis;
import model.Face;
import model.HalfEdge;
import model.HalfEdgeUtil;
import model.Point;
import model.Polyhedron;

public class NeighborhoodAnalysisTest {

	public static void main(String[] args) {
		Polyhedron threeFacesPoly = threeFacesPoly();
		System.out.println(threeFacesPoly);
		Face startFace = threeFacesPoly.getFaces().get(0); // z. B. Face mit ID 0
		Map<Face, Integer> levels = NeighborhoodAnalysis.bfsFaceLevels(startFace);

		for (Map.Entry<Face, Integer> entry : levels.entrySet()) {
			System.out.println("Face ID " + entry.getKey().getId() + " → Level " + entry.getValue());
		}

	}

	public static Polyhedron threeFacesPoly() {
		System.out.println("Methode threeFacesPoly aufgerufen");

		// Alle Punkte erzeugen
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(2, 0, 0);
		Point p3 = new Point(2, 0, 1);
		Point p4 = new Point(0, 1, 0);
		Point p5 = new Point(1, 1, 0);
		Point p6 = new Point(2, 1, 0);
		Point p7 = new Point(2, 1, 1);
		Point p8 = new Point(0, -1, 0);
		Point p9 = new Point(1, -1, 0);
		// List der aueßeren und inneren Punkte der Fläche mit Loch
		List<Point> pointsF1 = Arrays.asList(p0, p4, p5, p1);
		List<Point> pointsF2 = Arrays.asList(p1, p5, p6, p2);
		List<Point> pointsF3 = Arrays.asList(p2, p6, p7, p3);
		List<Point> pointsF4 = Arrays.asList(p8, p0, p1, p9);
		// Starthalfedge der äußeren und inneren Kanten erzeugen
		HalfEdge heF1Start = HalfEdgeUtil.buildPolygon(pointsF1);
		HalfEdge heF2Start = HalfEdgeUtil.buildPolygon(pointsF2);
		HalfEdge heF3Start = HalfEdgeUtil.buildPolygon(pointsF3);
		HalfEdge heF4Start = HalfEdgeUtil.buildPolygon(pointsF4);

		// Alle HalfEdges sammeln
		List<HalfEdge> edges = HalfEdge.allHalfEdgesPerFace(heF1Start);
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2Start));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF3Start));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF4Start));

		// Face mit äußerer Kante erzeugen
		Face face1 = new Face(heF1Start);
		Face face2 = new Face(heF2Start);
		Face face3 = new Face(heF3Start);
		Face face4 = new Face(heF4Start);
		List<Face> faces = Arrays.asList(face1, face2, face3, face4);

		// Hier noch für alle Kanten die twins setzen
		for (int i = 0; i < faces.size(); i++) {
			for (int j = i + 1; j < faces.size(); j++) {
				HalfEdgeUtil.connectTwoPolygons(faces.get(i).getOuterHalfEdge(), faces.get(j).getOuterHalfEdge());
			}
		}

		Polyhedron threeFacesPoly = new Polyhedron(faces);
		threeFacesPoly.setEdges(edges);
		threeFacesPoly.setPoints(Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9));

		return threeFacesPoly;

	}

}
