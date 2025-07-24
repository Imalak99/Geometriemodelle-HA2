package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author FG Bauinformatik
 *
 */
public class HalfEdge {

	private Point org;
	private HalfEdge prev, next, twin;
	private Face belongsToFace;

	public HalfEdge(Point org) {
		this.org = org;
	}

	public Point getOrg() {
		return org;
	}

	public void setOrg(Point org) {
		this.org = org;
	}

	public HalfEdge getPrev() {
		return prev;
	}

	public void setPrev(HalfEdge prev) {
		this.prev = prev;
	}

	public HalfEdge getNext() {
		return next;
	}

	public void setNext(HalfEdge next) {
		this.next = next;
	}

	public HalfEdge getTwin() {
		return twin;
	}

	public void setTwin(HalfEdge twin) {
		this.twin = twin;
	}

	public Face getBelongsToFace() {
		return belongsToFace;
	}

	public void setBelongsToFace(Face belongsToFace) {
		this.belongsToFace = belongsToFace;
	}

	public static List<HalfEdge> allHalfEdgesPerFace(HalfEdge start) {
		List<HalfEdge> result = new ArrayList<>();
		HalfEdge current = start;

		int count = 0;
		do {
			result.add(current);
			current = current.getNext();
			count++;
			if (count > 1000) {
				System.err.println("Loop exceeded limit – possibly broken structure.");
				break;
			}
		} while (current != null && current != start);

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("he: org = ").append(org.getX()).append("  ").append(org.getY()).append("  ").append(org.getZ())
				.append("    ");
		if (twin != null && twin.getOrg() != null) {
			sb.append("twin = ").append(twin.getOrg().getX()).append("  ").append(twin.getOrg().getY()).append("  ")
					.append(twin.getOrg().getZ());
		} else {
			sb.append("twin = null");
		}
		return sb.toString();
	}

	public String toStringFullLoop() {
		StringBuilder sb = new StringBuilder();
		sb.append("HalfEdge Loop: ");
		HalfEdge start = this;
		HalfEdge current = start;
		int count = 0;
		do {
			sb.append(current.getOrg());
			current = current.getNext();
			if (current != start) {
				sb.append(" -> ");
			}
			count++;
			if (count > 1000) { // Schutz gegen Endlosschleife bei fehlerhafter Verkettung
				sb.append("... (loop break)");
				break;
			}
		} while (current != null && current != start);
		return sb.toString();
	}

}