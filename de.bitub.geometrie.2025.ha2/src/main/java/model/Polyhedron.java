package model;

import java.util.ArrayList;
import java.util.List;

public class Polyhedron {

	private List<Face> faces;
	private List<HalfEdge> halfEdges;
	private List<Point> vertices;

	/**
	 * Konstruktor für Polyhedron, der die Listen von Faces, HalfEdges und Vertices
	 * initialisiert.
	 *
	 * @param faces     die Liste der Faces des Polyeders
	 * @param halfEdges die Liste der HalfEdges des Polyeders
	 * @param vertices  die Liste der Vertices des Polyeders
	 */
	public Polyhedron(List<Face> faces, List<HalfEdge> halfEdges, List<Point> vertices) {
		this.faces = faces;
		this.halfEdges = halfEdges;
		this.vertices = vertices;
	}

	public Polyhedron() {
		this.faces = new ArrayList<>();
		this.halfEdges = new ArrayList<>();
		this.vertices = new ArrayList<>();
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	public List<HalfEdge> getHalfEdges() {
		return halfEdges;
	}

	public void setHalfEdges(List<HalfEdge> halfEdges) {
		this.halfEdges = halfEdges;
	}

	public List<Point> getVertices() {
		return vertices;
	}

	public void setVertices(List<Point> vertices) {
		this.vertices = vertices;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Polyhedron with ").append(faces.size()).append(" faces, ").append(halfEdges.size())
				.append(" half-edges, and ").append(vertices.size()).append(" vertices.\n");
		for (Face face : faces) {
			sb.append(face.toString()).append("\n");
		}
		return sb.toString();
	}

	public static Polyhedron createExampleFace() {
		// TODO Auto-generated method stub
		return null;
	}

	public Polyhedron exampleCube() {
		Point p = new Point(0, 0, 0);
		double edgeLength = 1;
		initVertecies(p, edgeLength);
		return null;
	}

	private void initVertecies(Point p, double edgeLenght) {
		vertices = new ArrayList<>(8);
		vertices.add(p);
		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2]));
		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2]));
		vertices.add(new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2]));

		vertices.add(new Point(p.xyz[0], p.xyz[1], p.xyz[2] + edgeLenght));
		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2] + edgeLenght));
		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght));
		vertices.add(new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght));

//		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2]));
//		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2] + edgeLenght));
//		vertices.add(new Point(p.xyz[0], p.xyz[1], p.xyz[2] + edgeLenght));
//		vertices.add(new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2]));
//		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2]));
//		vertices.add(new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght));
//		vertices.add(new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght));

	}

}
