package model;

import java.util.ArrayList;
import java.util.List;

public class Face {

	private HalfEdge outerHalfEdge;
	private List<HalfEdge> holes = new ArrayList<HalfEdge>();
//	private List<Point> points;

	public Face(HalfEdge outerHalfEdge, List<HalfEdge> holes) {
		this.outerHalfEdge = outerHalfEdge;
		this.holes = holes;
	}

	public Face(HalfEdge halfEdge) {
		this.outerHalfEdge = halfEdge;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Face:\n");

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

		return sb.toString();
	}

}
