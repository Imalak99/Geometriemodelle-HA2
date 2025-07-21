package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polyhedron {

	private List<Face> faces;
	private List<HalfEdge> edges;
	private List<Point> points;
	private String name;

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
		for (Face face : faces) {
			face.setBelongsToPolyhedron(this);
		}
	}

	public Polyhedron(List<Face> faces, String name) {
		this.faces = faces;
		this.name = name;
		for (Face face : faces) {
			face.setBelongsToPolyhedron(this);
		}
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

	public String getName() {
		return name;
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

	public static Polyhedron cubeNonWaterTight() {
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
				// Top Face
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
		Polyhedron cubeNonWaterTight = new Polyhedron(faces, "Cube Non-WaterTight");
		// Dem cube die Punkte und Kanten zuweisen
		cubeNonWaterTight.setPoints(points);
		cubeNonWaterTight.setEdges(edges);
		return cubeNonWaterTight;
	}

	public static Polyhedron icosahedron() {
		double t = (1.0 + Math.sqrt(5.0)) / 2.0;
		double s = 1.0; // optional skalieren

		List<Point> points = Arrays.asList(new Point(-s, t, 0), new Point(s, t, 0), new Point(-s, -t, 0),
				new Point(s, -t, 0), new Point(0, -s, t), new Point(0, s, t), new Point(0, -s, -t), new Point(0, s, -t),
				new Point(t, 0, -s), new Point(t, 0, s), new Point(-t, 0, -s), new Point(-t, 0, s));

		int[][] indexFaceList = { { 0, 11, 5 }, { 0, 5, 1 }, { 0, 1, 7 }, { 0, 7, 10 }, { 0, 10, 11 }, { 1, 5, 9 },
				{ 5, 11, 4 }, { 11, 10, 2 }, { 10, 7, 6 }, { 7, 1, 8 }, { 3, 9, 4 }, { 3, 4, 2 }, { 3, 2, 6 },
				{ 3, 6, 8 }, { 3, 8, 9 }, { 4, 9, 5 }, { 2, 4, 11 }, { 6, 2, 10 }, { 8, 6, 7 }, { 9, 8, 1 } };

		List<Face> faces = new ArrayList<>();
		List<HalfEdge> edges = new ArrayList<>();

		for (int[] idx : indexFaceList) {
			List<Point> facePoints = Arrays.asList(points.get(idx[0]), points.get(idx[1]), points.get(idx[2]));
			HalfEdge he = HalfEdgeUtil.buildPolygon(facePoints);
			List<HalfEdge> all = HalfEdge.allHalfEdgesPerFace(he);
			edges.addAll(all);
			faces.add(new Face(he));
		}

		for (int i = 0; i < faces.size(); i++) {
			for (int j = i + 1; j < faces.size(); j++) {
				HalfEdgeUtil.connectTwoPolygons(faces.get(i).getOuterHalfEdge(), faces.get(j).getOuterHalfEdge());
			}
		}

		Polyhedron icosahedron = new Polyhedron(faces, "Icosahedron");
		icosahedron.setPoints(points);
		icosahedron.setEdges(edges);
		return icosahedron;

	}

	public static Polyhedron torus() {
		return torus(24, 12);
	}

	public static Polyhedron cuboidGenus2() {
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

		// Holepunkkte des zweiten Lochs vorder und Rückseite
		Point p16 = new Point(3, 1, 0);
		Point p17 = new Point(4, 1, 0);
		Point p18 = new Point(4, 2, 0);
		Point p19 = new Point(3, 2, 0);
		Point p20 = new Point(3, 1, -1);
		Point p21 = new Point(4, 1, -1);
		Point p22 = new Point(4, 2, -1);
		Point p23 = new Point(3, 2, -1);

		// Liste aller Punkte
		List<Point> points = Arrays.asList(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16,
				p17, p18, p19, p20, p21, p22, p23);

		// leere Facelist erstellen
		List<Face> faces = new ArrayList<>();
		// leere Edge-Liste erstellen
		List<HalfEdge> edges = new ArrayList<>();

		// Starthalfedges der Flächen
		HalfEdge heF1outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p0, p1, p2, p3)); // Außenkontur (Counterclockwise)
		HalfEdge heF1hole1 = HalfEdgeUtil.buildPolygon(Arrays.asList(p8, p11, p10, p9)); // Innenkontur (Loch 1)
		HalfEdge heF1hole2 = HalfEdgeUtil.buildPolygon(Arrays.asList(p16, p19, p18, p17)); // Innenkontur (Loch 2)
		List<HalfEdge> holes1 = new ArrayList<>();
		holes1.add(heF1hole1); // Lochkante 1 hinzufügen
		holes1.add(heF1hole2); // Lochkante 2 hinzufügen

		HalfEdge heF2outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p4, p7, p6, p5)); // Außenkontur der Rückseite
		HalfEdge heF2hole1 = HalfEdgeUtil.buildPolygon(Arrays.asList(p12, p13, p14, p15)); // Innenkontur (Loch 1)
		HalfEdge heF2hole2 = HalfEdgeUtil.buildPolygon(Arrays.asList(p20, p21, p22, p23)); // Innenkontur (Loch 2)
		List<HalfEdge> holes2 = new ArrayList<>();
		holes2.add(heF2hole1); // Lochkante 1 hinzufügen
		holes2.add(heF2hole2); // Lochkante 2 hinzufügen

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

		// Innenkanten Loch2
		HalfEdge heF11outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p23, p19, p16, p20));
		HalfEdge heF12outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p23, p22, p18, p19));
		HalfEdge heF13outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p18, p22, p21, p17));
		HalfEdge heF14outer = HalfEdgeUtil.buildPolygon(Arrays.asList(p20, p16, p17, p21));

		// Alles HalfEdges sammeln und der Edge-Liste hinzufügen
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1hole1));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF1hole2));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2hole1));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF2hole2));

		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF3outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF4outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF5outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF6outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF7outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF8outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF9outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF10outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF11outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF12outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF13outer));
		edges.addAll(HalfEdge.allHalfEdgesPerFace(heF14outer));

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
		Face face11 = new Face(heF11outer);
		Face face12 = new Face(heF12outer);
		Face face13 = new Face(heF13outer);
		Face face14 = new Face(heF14outer);

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
		faces.add(face11);
		faces.add(face12);
		faces.add(face13);
		faces.add(face14);

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
		Polyhedron cuboidGenus2 = new Polyhedron(faces, "Cuboid Genus 2");
		cuboidGenus2.setPoints(points);
		cuboidGenus2.setEdges(edges);
		return cuboidGenus2;

	}

	public static Polyhedron torus(int majorSegs, int minorSegs) {
		double majorR = 2.0;
		double minorR = 0.5;

		List<Point> points = new ArrayList<>();
		for (int i = 0; i < majorSegs; i++) {
			double theta = 2 * Math.PI * i / majorSegs;
			for (int j = 0; j < minorSegs; j++) {
				double phi = 2 * Math.PI * j / minorSegs;
				double x = (majorR + minorR * Math.cos(phi)) * Math.cos(theta);
				double y = (majorR + minorR * Math.cos(phi)) * Math.sin(theta);
				double z = minorR * Math.sin(phi);
				points.add(new Point(x, y, z));
			}
		}

		List<Face> faces = new ArrayList<>();
		List<HalfEdge> edges = new ArrayList<>();

		for (int i = 0; i < majorSegs; i++) {
			for (int j = 0; j < minorSegs; j++) {
				int i2 = (i + 1) % majorSegs;
				int j2 = (j + 1) % minorSegs;

				int p0 = i * minorSegs + j;
				int p1 = i2 * minorSegs + j;
				int p2 = i2 * minorSegs + j2;
				int p3 = i * minorSegs + j2;

				List<Point> facePoints = Arrays.asList(points.get(p0), points.get(p1), points.get(p2), points.get(p3));
				HalfEdge he = HalfEdgeUtil.buildPolygon(facePoints);
				edges.addAll(HalfEdge.allHalfEdgesPerFace(he));
				faces.add(new Face(he));
			}
		}

		for (int i = 0; i < faces.size(); i++) {
			for (int j = i + 1; j < faces.size(); j++) {
				HalfEdgeUtil.connectTwoPolygons(faces.get(i).getOuterHalfEdge(), faces.get(j).getOuterHalfEdge());
			}
		}

		Polyhedron torus = new Polyhedron(faces, "Torus");
		torus.setPoints(points);
		torus.setEdges(edges);
		return torus;
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
