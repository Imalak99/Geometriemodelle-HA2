package model;

import java.util.ArrayList;
import java.util.List;

public class Face {

	private HalfEdge outerHalfEdge;
	private List<HalfEdge> holes = new ArrayList<HalfEdge>();
	private Polyhedron belongsToPolyhedron;
	private static int count = 0; // Zähler für die Anzahl der HalfEdges, die zu dieser Face gehören
	private int id = 0; // ID der Face, um sie eindeutig zu identifizieren

	public Face(HalfEdge outerHalfEdge, List<HalfEdge> holes) {
		this.outerHalfEdge = outerHalfEdge;
		this.holes = holes;
		assignToFace(outerHalfEdge);
		for (HalfEdge hole : holes) {
			assignToFace(hole);
		}
		this.id = ++count; // ID wird bei der Erstellung der Face inkrementiert
	}

	public Face(HalfEdge halfEdge) {
		this.outerHalfEdge = halfEdge;
		this.holes = new ArrayList<>();
		assignToFace(halfEdge);
		this.id = ++count; // ID wird bei der Erstellung der Face inkrementiert
	}

	private void assignToFace(HalfEdge start) {
		HalfEdge current = start;
		do {
			current.setBelongsToFace(this);
			current = current.getNext();
		} while (current != start);
	}

	public HalfEdge getOuterHalfEdge() {
		return outerHalfEdge;
	}

	public void setHalfEdge(HalfEdge halfEdge) {
		this.outerHalfEdge = halfEdge;
	}

	public List<HalfEdge> getHoles() {
		return holes;
	}

	public void setHole(HalfEdge hole) {
		this.holes.add(hole);
	}

	public int getId() {
		return id;
	}

	public List<Point> getOrderedBoundaryPoints() {
		List<Point> points = new ArrayList<>();
		HalfEdge start = this.getOuterHalfEdge(); // Startkante des äußeren Randes
		if (start == null)
			return points;
		HalfEdge current = start;
		do {
			points.add(current.getOrg());
			current = current.getNext();
		} while (current != null && current != start);
		return points;
	}

	public static List<Point> getPointsFromStartHalfEdge(HalfEdge start) {
		List<Point> points = new ArrayList<>();
		if (start == null)
			return points;
		HalfEdge e = start;
		do {
			points.add(e.getOrg());
			e = e.getNext();
		} while (e != null && e != start);
		return points;
	}

	public Polyhedron getBelongsToPolyhedron() {
		return belongsToPolyhedron;
	}

	public void setBelongsToPolyhedron(Polyhedron belongsToPolyhedron) {
		this.belongsToPolyhedron = belongsToPolyhedron;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Face:\n");
		sb.append("  ID: ").append(id).append("\n");
		// Outer boundary
		if (outerHalfEdge != null) {
			sb.append("  Outer: ");
			sb.append(outerHalfEdge.toString());
			sb.append("\n");
		}
		// Holes
		if (holes != null && !holes.isEmpty()) {
			int i = 1;
			for (HalfEdge hole : holes) {
				sb.append("  Hole ").append(i).append(": ");
				sb.append(hole.toString());
				sb.append("\n");
				i++;
			}
		}
		if (belongsToPolyhedron != null) {
			sb.append("  Belongs to Polyhedron: ").append(belongsToPolyhedron.getName()).append("\n");
		} else {
			sb.append("  Belongs to Polyhedron: null\n");
		}
		return sb.toString();
	}

}
