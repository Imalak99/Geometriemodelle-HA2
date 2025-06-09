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

}
