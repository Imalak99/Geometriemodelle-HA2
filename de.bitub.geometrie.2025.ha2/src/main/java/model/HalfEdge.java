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

}