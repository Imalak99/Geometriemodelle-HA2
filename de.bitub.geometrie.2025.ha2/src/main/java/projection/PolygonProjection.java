package projection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import model.Point;

/**
 * This class provides functionality to project 3D polygons onto a 2D plane and
 * perform triangulation on the projected polygons. Use the
 * {@link #projectTo2D(List, List)} method to create 2D point list and as input
 * for Poly2Tri and use the returned map to find the corresponding 3D points.
 */
public class PolygonProjection {

	/**
	 * Threshold used for determining if points are collinear.
	 */
	private static final double COLLINEARITY_THRESHOLD = 1e-4;

	/**
	 * Projects a list of 3D polygons onto a 2D plane and returns a map associating
	 * 2D points with their corresponding 3D points.
	 *
	 * @param boundaries List of 3D polygons represented as lists of Point objects.
	 * @param points2D   (Empty) List to store the resulting 2D polygons.
	 * @return A map associating 2D points with their corresponding 3D points.
	 * @throws IllegalArgumentException if the input is invalid or insufficient for
	 *                                  projection
	 */
	public static Map<PolygonPoint, Point> projectTo2D(List<List<Point>> boundaries,
			List<List<PolygonPoint>> points2D) {
		// Validate input parameters
		Objects.requireNonNull(boundaries, "Boundaries cannot be null");
		Objects.requireNonNull(points2D, "Points2D list cannot be null");

		if (boundaries.isEmpty()) {
			throw new IllegalArgumentException("Boundaries list cannot be empty");
		}

		// Extract 3D coordinates from the points
		double[][] planePoints = findIndependentVectors(boundaries.get(0).stream()
				.map(p -> Objects.requireNonNull(p, "Point cannot be null").xyz).toArray(double[][]::new));

		// Calculate normal and basis vectors for the plane
		double[] normal = calculateNormal(planePoints[0], planePoints[1], planePoints[2]);
		normal = VectorUtils.normalize(normal);

		double[] basisX = VectorUtils.normalize(VectorUtils.subtract(planePoints[1], planePoints[0]));
		double[] basisY = VectorUtils.normalize(VectorUtils.crossProduct(normal, basisX));

		// Map to store the relationship between 2D points and their corresponding 3D
		// points
		Map<PolygonPoint, Point> pointMap = new HashMap<>();

		for (List<Point> points3D : boundaries) {
			if (points3D == null) {
				continue;
			}

			List<PolygonPoint> projectedPoints = new ArrayList<>();

			for (Point p : points3D) {
				if (p == null) {
					continue;
				}

				double[] vector = VectorUtils.subtract(p.xyz, planePoints[0]);
				PolygonPoint pp = new PolygonPoint(VectorUtils.dotProduct(vector, basisX),
						VectorUtils.dotProduct(vector, basisY));
				pointMap.put(pp, p);
				projectedPoints.add(pp);
			}

			points2D.add(projectedPoints);
		}

		return pointMap;
	}

	/**
	 * Finds three independent points from a list of 3D points that define a plane.
	 *
	 * @param points3d Array of 3D points represented as double arrays.
	 * @return A 2D array containing three independent points.
	 * @throws IllegalArgumentException if the points do not form a valid polygon
	 */
	private static double[][] findIndependentVectors(double[][] points3d) {
		if (points3d.length < 3) {
			throw new IllegalArgumentException("Not enough points to form a polygon (minimum 3 required)");
		}

		double[] p0 = points3d[0];
		double[] p1 = points3d[1];

		for (int i = 2; i < points3d.length; i++) {
			double[] p2 = points3d[i];

			if (!isCollinear(p0, p1, p2)) {
				return new double[][] { p0, p1, p2 };
			}
		}

		throw new IllegalArgumentException("The points form a line, not a polygon");
	}

	/**
	 * Checks if three points are collinear.
	 *
	 * @param p0 The first point.
	 * @param p1 The second point.
	 * @param p2 The third point.
	 * @return True if the points are collinear, false otherwise.
	 */
	private static boolean isCollinear(double[] p0, double[] p1, double[] p2) {
		double[] normal = calculateNormal(p0, p1, p2);
		double length = VectorUtils.getLength(normal);
		return Math.abs(length) < COLLINEARITY_THRESHOLD;
	}

	/**
	 * Calculates the normal vector of the plane defined by three points.
	 *
	 * @param p0 The first point.
	 * @param p1 The second point.
	 * @param p2 The third point.
	 * @return The normal vector of the plane.
	 */
	private static double[] calculateNormal(double[] p0, double[] p1, double[] p2) {
		double[] v1 = VectorUtils.subtract(p1, p0);
		double[] v2 = VectorUtils.subtract(p2, p0);
		return VectorUtils.crossProduct(v1, v2);
	}

	/**
	 * Utility class for vector operations.
	 */
	public static class VectorUtils {
		/**
		 * Subtracts one vector from another.
		 *
		 * @param v1 The first vector.
		 * @param v2 The second vector.
		 * @return The result of the subtraction.
		 */
		public static double[] subtract(double[] v1, double[] v2) {
			return new double[] { v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2] };
		}

		/**
		 * Calculates the cross product of two vectors.
		 *
		 * @param v1 The first vector.
		 * @param v2 The second vector.
		 * @return The cross product of the vectors.
		 */
		public static double[] crossProduct(double[] v1, double[] v2) {
			return new double[] { v1[1] * v2[2] - v1[2] * v2[1], v1[2] * v2[0] - v1[0] * v2[2],
					v1[0] * v2[1] - v1[1] * v2[0] };
		}

		/**
		 * Calculates the dot product of two vectors.
		 *
		 * @param v1 The first vector.
		 * @param v2 The second vector.
		 * @return The dot product of the vectors.
		 */
		public static double dotProduct(double[] v1, double[] v2) {
			return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
		}

		/**
		 * Normalizes a vector.
		 *
		 * @param v The vector to be normalized.
		 * @return The normalized vector.
		 */
		public static double[] normalize(double[] v) {
			double length = getLength(v);
			if (Math.abs(length) < COLLINEARITY_THRESHOLD) {
				throw new IllegalArgumentException("Cannot normalize a zero-length vector");
			}
			return new double[] { v[0] / length, v[1] / length, v[2] / length };
		}

		/**
		 * Calculates the length of a vector.
		 *
		 * @param v The vector.
		 * @return The length of the vector.
		 */
		public static double getLength(double[] v) {
			return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
		}
	}

	/**
     * Main method for testing the PolygonProjection class.
     * Example usage: Use the map to get the 3D points from the triangles.
     * 
     * <!-- @formatter:off --> <pre>
     * Polygon (outer and inner boundary):
     * 
     * +-----------------+
     * |                 |
     * |   +--------+    |
     * |   |        |    |
     * |   |        |    |
     * |   +--------+    |
     * |                 |
     * +-----------------+
     * 
     * - The `+` signs represent the vertices of the polygon.
     * - The outer rectangle represents the outer boundary.
     * - The inner rectangle represents the hole.
     * - XZ Plane</pre>
     * <!-- @formatter:on -->
     * @param args Command line arguments.
     */
	public static void main(String[] args) {
		// Create outer boundary
		List<Point> points3D = new ArrayList<>();
		points3D.add(new Point(0, 10, 0));
		points3D.add(new Point(5, 10, 0));
		points3D.add(new Point(10, 10, 0));
		points3D.add(new Point(10, 10, 5));
		points3D.add(new Point(0, 10, 5));

		// Create hole
		List<Point> hole = new ArrayList<>();
		hole.add(new Point(1, 10, 1));
		hole.add(new Point(8, 10, 1));
		hole.add(new Point(8, 10, 4));
		hole.add(new Point(1, 10, 4));

		// Reverse hole points (important for Poly2Tri - holes must be in reverse order)
		// Using List.reversed() method available in Java 21
		List<Point> reversedHole = hole.reversed();

		// Project the 3D polygons to 2D
		List<List<PolygonPoint>> points2D = new ArrayList<>();
		Map<PolygonPoint, Point> pointMap = projectTo2D(List.of(points3D, reversedHole), points2D);

		// Create the polygon with holes
		Polygon polygon = new Polygon(points2D.get(0));

		for (int i = 1; i < points2D.size(); i++) {
			polygon.addHole(new Polygon(points2D.get(i)));
		}

		// Triangulate the polygon
		Poly2Tri.triangulate(polygon);

		// Print the triangulation results
		System.out.println("Triangulation Results:");
		for (DelaunayTriangle tri : polygon.getTriangles()) {
			System.out.println("\nTriangle:");
			for (int i = 0; i < 3; i++) {
				TriangulationPoint pp = tri.points[i];
				Point p3d = pointMap.get(pp);
				System.out.printf("  2D Point: (%.2f, %.2f) -> 3D Point: (%.2f, %.2f, %.2f)%n", pp.getX(), pp.getY(),
						p3d.xyz[0], p3d.xyz[1], p3d.xyz[2]);
			}
		}
	}
}