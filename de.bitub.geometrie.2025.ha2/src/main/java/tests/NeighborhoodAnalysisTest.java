package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Face;
import model.HalfEdge;
import model.HalfEdgeUtil;
import model.Point;
import model.Polyhedron;

public class NeighborhoodAnalysisTest {

	public static void main(String[] args) {

		Polyhedron faceWithHoleAnAdditionalFace = faceWithHoleAnAdditionalFace();
//		Polyhedron cube = cube();
//		Polyhedron threeFacesPoly = threeFacesPoly();

	}

	public static Polyhedron faceWithHoleAnAdditionalFace() {
		System.out.println("Methode faceWithHoleAnAdditionalFace aufgerufen");

		// Alle Punkte erzeugen
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(3, 0, 0);
		Point p2 = new Point(3, 3, 0);
		Point p3 = new Point(0, 3, 0);
		Point p4 = new Point(1, 1, 0);
		Point p5 = new Point(2, 1, 0);
		Point p6 = new Point(2, 2, 0);
		Point p7 = new Point(1, 2, 0);
		Point p8 = new Point(1, 2, -3);
		Point p9 = new Point(1, 1, -3);
		Point p10 = new Point(3, 3, -3);
		Point p11 = new Point(3, 0, -3);

		// Liste aller Punkte
		List<Point> points = new ArrayList<>(Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		// leere Facelist erstellen
		List<Face> faces = new ArrayList<>();
		// leere Edge-Liste erstellen
		List<HalfEdge> edges = new ArrayList<>();

		// Starthalfedges der Flächen
		HalfEdge heF1 = HalfEdgeUtil.buildPolygon(Arrays.asList(p0, p3, p2, p1)); // Außenkontur (Counterclockwise)
		HalfEdge heF1hole = HalfEdgeUtil.buildPolygon(Arrays.asList(p4, p7, p6, p5)); // Innenkontur (Loch) (Clockwise)
		List<HalfEdge> holes = new ArrayList<>();
		holes.add(heF1hole); // Lochkante hinzufügen
		HalfEdge heF2 = HalfEdgeUtil.buildPolygon(Arrays.asList(p7, p4, p9, p8));
//		HalfEdge heF3 = HalfEdgeUtil.buildPolygon(Arrays.asList(p1, p2, p10, p11));

		// Alles HalfEdges sammeln und der Edge-Liste hinzufügen
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1hole));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2));
//		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF3));

		// Faces erzeugen mit start HalfEdges und der Facelist hinzufügen
		Face face1 = new Face(heF1, holes);
		Face face2 = new Face(heF2);
//		Face face3 = new Face(heF3);
		faces.addAll(Arrays.asList(face1, face2));

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

		// Polyhedron erzeugen mit den Faces
		Polyhedron faceWithHoleAnAdditionalFace = new Polyhedron(faces);
		faceWithHoleAnAdditionalFace.setEdges(edges);
		faceWithHoleAnAdditionalFace.setPoints(points);

		System.out.println("Anzahl der HalfEdges: " + edges.size());
		System.out.println("Alle Edges: \n" + edges);
		return faceWithHoleAnAdditionalFace;

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

		System.out.println(edges);

		return threeFacesPoly;

	}

	public static Polyhedron cube() {
		// Alle Punkte des Würfels
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(1, 1, 0);
		Point p3 = new Point(0, 1, 0);
		Point p4 = new Point(0, 0, 1);
		Point p5 = new Point(1, 0, 1);
		Point p6 = new Point(1, 1, 1);
		Point p7 = new Point(0, 1, 1);
		// Punkte in eine Liste packen
		List<Point> points = new ArrayList<>(Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7));
		// Indizes der Punkte für die Faces des Würfels
		int[][] indexFaceList = { { 0, 3, 2, 1 }, // Bottom Face
				{ 4, 5, 6, 7 }, // Top Face
				{ 0, 1, 5, 4 }, // Left Face
				{ 2, 3, 7, 6 }, // Right Face
				{ 0, 4, 7, 3 }, // Front Face
				{ 1, 2, 6, 5 } // Back Face
		};
		// leere Facelist erstellen
		List<Face> faces = new ArrayList<>();
		// leere Edge-Liste erstellen
		List<HalfEdge> edges = new ArrayList<>();
		// Durch indexFaceList iterieren
		for (int[] oneFaceAsIndexList : indexFaceList) {
			// Liste der Punkte für eine Face erstellen
			List<Point> facePoints = new ArrayList<>(
					Arrays.asList(points.get(oneFaceAsIndexList[0]), points.get(oneFaceAsIndexList[1]),
							points.get(oneFaceAsIndexList[2]), points.get(oneFaceAsIndexList[3])));
			// Für diese Face eine HalfEdge erstellen
			HalfEdge he = HalfEdgeUtil.buildPolygon(facePoints);
			// Alle HalfEdges der Face sammeln
			List<HalfEdge> allHalfEdgesPerFace = HalfEdge.allHalfEdgesPerFace(he);
			// HalfEdges zu der Edge-Liste hinzufügen
			edges.addAll(allHalfEdgesPerFace);
			// Face erstellen mit der start HalfEdge
			Face face = new Face(he);
			// Face zu der Facelist hinzufügen
			faces.add(face);
		}
		// Fuer jede HalfEdge den Twin setzen
		// doppelt durch die faces iterieren, um jede Face mit jeder anderen Face zu
		// vergleichen
		for (int i = 0; i < faces.size(); i++) {
			for (int j = i + 1; j < faces.size(); j++) {
				HalfEdgeUtil.connectTwoPolygons(faces.get(i).getOuterHalfEdge(), faces.get(j).getOuterHalfEdge());
			}
		}
		// Cube erstellen mit den Faces
		Polyhedron cube = new Polyhedron(faces);
		// Dem cube die Punkte und Kanten zuweisen
		cube.setPoints(points);
		cube.setEdges(edges);
		System.out.println(edges);
		return cube;
	}

}
