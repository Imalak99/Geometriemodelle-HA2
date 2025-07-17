package model;

/**
 * 
 * @author FG Bauinformatik
 *
 */
public class HalfEdge {

	private Point org;
	private HalfEdge prev, next, twin;

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HalfEdge[");
		sb.append("org=").append(org != null ? org.toString() : "null");
		sb.append(", prev org=").append(prev != null ? prev.org.toString() : "null");
		sb.append(", next org=").append(next != null ? next.org.toString() : "null");
		sb.append(", twin org=").append(twin != null ? twin.org.toString() : "null");
		sb.append("]");
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