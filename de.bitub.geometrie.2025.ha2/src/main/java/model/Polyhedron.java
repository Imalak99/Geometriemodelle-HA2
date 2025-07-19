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
		System.out.println("Methode cube aufgerufen");
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(1, 1, 0);
		Point p3 = new Point(0, 1, 0);
		Point p4 = new Point(0, 0, 1);
		Point p5 = new Point(1, 0, 1);
		Point p6 = new Point(1, 1, 1);
		Point p7 = new Point(0, 1, 1);
		List<Point> points = new ArrayList<>(Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7));
//		System.out.println(points);
		int[][] indexFaceList = { { 0, 3, 2, 1 }, // Bottom Face
				{ 4, 5, 6, 7 }, // Top Face
				{ 0, 1, 5, 4 }, // Left Face
				{ 2, 3, 7, 6 }, // Right Face
				{ 0, 4, 7, 3 }, // Front Face
				{ 1, 2, 6, 5 } // Back Face
		};

		// Facelist erstellen
		List<Face> faces = new ArrayList<>();
		// Edges erstellen um Faces zu erstellen
		List<HalfEdge> edges = new ArrayList<>();
		for (int[] oneFaceAsIndexList : indexFaceList) {
			List<Point> facePoints = new ArrayList<>(
					Arrays.asList(points.get(oneFaceAsIndexList[0]), points.get(oneFaceAsIndexList[1]),
							points.get(oneFaceAsIndexList[2]), points.get(oneFaceAsIndexList[3])));
			HalfEdge he = HalfEdgeUtil.buildPolygon(facePoints);
			List<HalfEdge> allHalfEdgesPerFace = HalfEdge.allHalfEdgesPerFace(he);
//			System.out.println(allHalfEdgesPerFace);
			edges.addAll(allHalfEdgesPerFace);
			Face face = new Face(he);
			faces.add(face);
		}

		// Fuer jede HalfEdge den Twin setzen
		for (int i = 0; i < faces.size(); i++) {
			for (int j = i + 1; j < faces.size(); j++) {
				HalfEdgeUtil.connectTwoPolygons(faces.get(i).getHalfEdge(), faces.get(j).getHalfEdge());
			}
		}

//		System.out.println(faces);
		Polyhedron cube = new Polyhedron(faces);
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
