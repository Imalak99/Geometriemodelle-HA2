package model;

import java.util.List;

public class Face {

	private HalfEdge outerHalfEdge;
	private List<HalfEdge> holes;

	public Face(HalfEdge outerHalfEdge, List<HalfEdge> holes) {
		this.outerHalfEdge = outerHalfEdge;
		this.holes = holes;
	}

	public Face(HalfEdge halfEdge) {
		this.outerHalfEdge = halfEdge;
	}

	public HalfEdge getHalfEdge() {
		return outerHalfEdge;
	}

	public void setHalfEdge(HalfEdge halfEdge) {
		this.outerHalfEdge = halfEdge;
	}

	public List<HalfEdge> getHoles() {
		return holes;
	}

	public void setHoles(List<HalfEdge> holes) {
		this.holes = holes;
	}

	@Override
	public String toString() {
		return "Face [halfEdge=" + outerHalfEdge.getOrg().xyz[0] + ", " + outerHalfEdge.getOrg().xyz[1] + ", "
				+ outerHalfEdge.getOrg().xyz[2] + "]";
	}

}
