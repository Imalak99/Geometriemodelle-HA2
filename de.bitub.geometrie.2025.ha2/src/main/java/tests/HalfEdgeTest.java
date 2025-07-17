package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.HalfEdge;
import model.HalfEdgeUtil;
import model.Point;

public class HalfEdgeTest {

	public static void main(String[] args) {
		Point p0 = new Point(0, 0, 0);
		Point p1 = new Point(1, 0, 0);
		Point p2 = new Point(1, 1, 0);
		Point p3 = new Point(0, 1, 0);
		List<Point> points = new ArrayList<>(Arrays.asList(p0, p1, p2, p3));
		HalfEdge he0 = HalfEdgeUtil.buildPolygon(points);

		System.out.println(he0.toStringFullLoop());
	}

}
