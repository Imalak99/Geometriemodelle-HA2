package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logic.EulerOperations;
import model.Face;
import model.HalfEdge;
import model.HalfEdgeUtil;
import model.Point;
import model.Polyhedron;

public class EulerTest {

	public static void main(String[] args) {
		cubeWithHole();
	}

	public static Polyhedron cubeWithHole() {
		// Alle Punkte erzeugen
		// Eckpunkte der Vorder und Rückseite
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(5, 0, 0);
		Point p2 = new Point(5, 3, 0);
		Point p3 = new Point(0, 3, 0);
		Point p4 = new Point(0, 0, -1);
		Point p5 = new Point(5, 0, -1);
		Point p6 = new Point(5, 3, -1);
		Point p7 = new Point(0, 3, -1);

		// Holepunkkte des ersten Lochs vorder und Rückseite
		Point p8 = new Point(1, 1, 0);
		Point p9 = new Point(2, 1, 0);
		Point p10 = new Point(2, 2, 0);
		Point p11 = new Point(1, 2, 0);
		Point p12 = new Point(1, 1, -1);
		Point p13 = new Point(2, 1, -1);
		Point p14 = new Point(2, 2, -1);
		Point p15 = new Point(1, 2, -1);

		// Liste aller Punkte
		List<Point> points = Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);

		// leere Facelist erstellen
		List<Face> faces = new ArrayList<>();
		// leere Edge-Liste erstellen
		List<HalfEdge> edges = new ArrayList<>();

		// Starthalfedges der Flächen
		HalfEdge heF1outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p0, p1, p2, p3)); // Außenkontur (Counterclockwise)
		HalfEdge heF1hole1 = HalfEdgeUtil.buildPolygon(Arrays.asList(p8, p11, p10, p9)); // Innenkontur (Loch 1)
		List<HalfEdge> holes1 = new ArrayList<>();
		holes1.add(heF1hole1); // Lochkante 1 hinzufügen

		HalfEdge heF2outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p4, p7, p6, p5)); // Außenkontur der Rückseite
		HalfEdge heF2hole1 = HalfEdgeUtil.buildPolygon(Arrays.asList(p12, p13, p14, p15)); // Innenkontur (Loch 1)
		List<HalfEdge> holes2 = new ArrayList<>();
		holes2.add(heF2hole1); // Lochkante 1 hinzufügen

		// Außenkonturen der Seitenflächen
		HalfEdge heF3outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p0, p3, p7, p4));
		HalfEdge heF4outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p3, p2, p6, p7));
		HalfEdge heF5outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p6, p2, p1, p5));
		HalfEdge heF6outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p5, p1, p0, p4));

		// Innenkanten Loch1
		HalfEdge heF7outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p8, p12, p15, p11));
		HalfEdge heF8outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p15, p14, p10, p11));
		HalfEdge heF9outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p14, p13, p9, p10));
		HalfEdge heF10outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p8, p9, p13, p12));

		// Alles HalfEdges sammeln und der Edge-Liste hinzufügen
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1hole1));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2hole1));

		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF3outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF4outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF5outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF6outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF7outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF8outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF9outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF10outer));

		// Faces erzeugen mit start HalfEdges und der Facelist hinzufügen
		Face face1 = new Face(heF1outer, holes1);
		Face face2 = new Face(heF2outer, holes2);
		Face face3 = new Face(heF3outer);
		Face face4 = new Face(heF4outer);
		Face face5 = new Face(heF5outer);
		Face face6 = new Face(heF6outer);
		Face face7 = new Face(heF7outer);
		Face face8 = new Face(heF8outer);
		Face face9 = new Face(heF9outer);
		Face face10 = new Face(heF10outer);

		// Alle faces der Facelist hinzufügen
		faces.add(face1);
		faces.add(face2);
		faces.add(face3);
		faces.add(face4);
		faces.add(face5);
		faces.add(face6);
		faces.add(face7);
		faces.add(face8);
		faces.add(face9);
		faces.add(face10);

		// Fuer jede HalfEdge den Twin setzen
		for (int i = 0; i < faces.size(); i++) {
			Face faceA = faces.get(i);
			List<HalfEdge> loopsA = new ArrayList<>();
			loopsA.add(faceA.getOuterHalfEdge());
			loopsA.addAll(faceA.getHoles());

			for (int j = i + 1; j < faces.size(); j++) {
				Face faceB = faces.get(j);
				List<HalfEdge> loopsB = new ArrayList<>();
				loopsB.add(faceB.getOuterHalfEdge());
				loopsB.addAll(faceB.getHoles());

				for (HalfEdge loopA : loopsA) {
					for (HalfEdge loopB : loopsB) {
						HalfEdgeUtil.connectTwoPolygons(loopA, loopB);
					}
				}
			}
		}

		// Cube erstellen mit den Faces
		Polyhedron cubeWithHole = new Polyhedron(faces, "Cube With Hole");
		cubeWithHole.setPoints(points);
		cubeWithHole.setEdges(edges);

		EulerOperations.calcEulerPoincareCharacteristic(cubeWithHole);
		return cubeWithHole;

	}
}