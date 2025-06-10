package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.poly2tri.geometry.polygon.PolygonPoint;

import model.Cube;
import model.Point;
import projection.PolygonProjection;

public class TriangulationTest {

	public static void main(String[] args) {

		Cube c = Cube.createExampelCube();

		List<List<Point>> boundaries = c.getVertexList();

		System.out.println(boundaries);

		List<List<PolygonPoint>> points2D = new ArrayList<List<PolygonPoint>>();

		Map<PolygonPoint, Point> map = PolygonProjection.projectTo2D(boundaries, points2D);

		System.out.println(map);

	}

}
