package model;

import java.util.List;

/**
 * 
 * @author FG Bauinformatik
 *
 */
public class HalfEdgeUtil {

	/**
	 * boundary twins are null
	 * 
	 * @param points
	 * @return
	 */
	public static HalfEdge buildPolygon(List<Point> points) {
		if (points == null || points.size() < 3) {
			throw new IllegalArgumentException("Polygon needs at least 3 points");
		}
		HalfEdge first = new HalfEdge(points.get(0));
		HalfEdge current = first;

		for (int i = 1; i < points.size(); i++) {
			HalfEdge next = new HalfEdge(points.get(i));
			current.setNext(next);
			next.setPrev(current);
			current = next;
		}
		current.setNext(first);
		first.setPrev(current);
		return first;
	}

	/**
	 * Removes a half-edge from a polygon
	 * 
	 * @param start is the start half-edge of the polygon
	 * 
	 * @param point is the origin of the half-edge that will be removed
	 */
	public static void removePointFromPolygon(HalfEdge start, Point point) {
		HalfEdge curr = start;
		do {
			if (curr.getOrg() == point) {
				removeHalfEdge(curr);
				System.out.println("Punkt " + point + " entfernt");
				return;
			}
			curr = curr.getNext();
		} while (curr != start);
		System.out.println("Der Punkt liegt nicht im Polygon und wurde nicht entfernt: Punkt" + point);
	}

	/**
	 * Removes a half-edge by connecting its predecessor and successor
	 * 
	 * @param edge
	 */
	public static void removeHalfEdge(HalfEdge edge) {
		edge.getPrev().setNext(edge.getNext());

		edge.getNext().setPrev(edge.getPrev());

	}

	/**
	 * adds a half-edge between a given edge and its successor
	 * 
	 * @param edge
	 * @param toAdd
	 */
	public static void addHalfEdgeAsNext(HalfEdge edge, HalfEdge toAdd) {

		HalfEdge successor = edge.getNext();

		successor.setPrev(toAdd);
		toAdd.setNext(successor);
		edge.setNext(toAdd);
		toAdd.setPrev(edge);

	}

	/**
	 * connects two polygons at every edge that they share (twins the edges)
	 * 
	 * @param start1 is an (arbitrary) half-edge of the first polygon
	 * @param start2 is an (arbitrary) half-edge of the second polygon
	 */
	public static void connectTwoPolygons(HalfEdge start1, HalfEdge start2) {
		HalfEdge current1 = start1;

		do {
			HalfEdge current2 = start2;
			do {
				if (current1.getOrg() == current2.getNext().getOrg()
						&& current1.getNext().getOrg() == current2.getOrg()) {
					current1.setTwin(current2);
					current2.setTwin(current1);
				}
				current2 = current2.getNext();
			} while (current2 != start2);

			current1 = current1.getNext();
		} while (current1 != start1);
	}

}