package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polyhedron {

	private List<Face> faces;
	private List<HalfEdge> edges;
	private List<Point> points;

	/**
	 * Konstruktor für Polyhedron, der die Listen von Faces, HalfEdges und Vertices
	 * initialisiert.
	 *
	 * @param faces     die Liste der Faces des Polyeders
	 * @param halfEdges die Liste der HalfEdges des Polyeders
	 * @param vertices  die Liste der Vertices des Polyeders
	 */
	public Polyhedron(List<Face> faces) {
		this.faces = faces;
	}

	public Polyhedron() {
		this.faces = new ArrayList<>();
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	public List<HalfEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<HalfEdge> edges) {
		this.edges = edges;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
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
		return cube;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Polyhedron with ").append(faces.size()).append(" faces:\n");
		for (Face face : faces) {
			sb.append(face);
			sb.append("\n");
		}
		return sb.toString();
	}

}
