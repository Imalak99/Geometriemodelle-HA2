package projection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import model.Point;

/**
 * Testet die PolygonProjection-Klasse, insbesondere die projectTo2D-Methode und
 * die Funktionalität, die normalerweise in der main-Methode ausgeführt wird.
 */
public class PolygonProjectionTest {

	/**
	 * Testet die Projektion eines einfachen Quadrats auf die XY-Ebene.
	 */
	@Test
	@DisplayName("Test der Projektion eines Quadrats auf die XY-Ebene")
	public void testProjectSquareToXYPlane() {
		// Ein Quadrat auf der XY-Ebene (Z = 5) erstellen
		List<Point> square = new ArrayList<>();
		square.add(new Point(0, 0, 5));
		square.add(new Point(10, 0, 5));
		square.add(new Point(10, 10, 5));
		square.add(new Point(0, 10, 5));

		List<List<PolygonPoint>> points2D = new ArrayList<>();
		Map<PolygonPoint, Point> pointMap = PolygonProjection.projectTo2D(List.of(square), points2D);

		// Überprüfen, ob die Projektion die richtige Anzahl von Punkten hat
		assertEquals(1, points2D.size());
		assertEquals(4, points2D.get(0).size());

		// Überprüfen, ob die Zuordnung zwischen 2D- und 3D-Punkten korrekt ist
		assertEquals(4, pointMap.size());

		// Überprüfen der projizierten Koordinaten
		// Die Projektion sollte etwa den ursprünglichen X,Y-Koordinaten entsprechen
		for (PolygonPoint pp : points2D.get(0)) {
			Point p3d = pointMap.get(pp);
			// Wir erwarten, dass die X,Y-Koordinaten der Projektion ungefähr den
			// X,Y-Koordinaten des 3D-Punktes entsprechen
			// (mit einer gewissen Toleranz)
			assertTrue(Math.abs(pp.getX() - p3d.xyz[0]) < 0.001 || Math.abs(pp.getY() - p3d.xyz[1]) < 0.001);
		}
	}

	/**
	 * Testet die Projektion eines Polygons mit einem Loch (wie im Beispiel der
	 * main-Methode).
	 */
	@Test
	@DisplayName("Test der Projektion eines Polygons mit einem Loch")
	public void testProjectPolygonWithHole() {
		// Äußeres Polygon erstellen
		List<Point> outerPolygon = new ArrayList<>();
		outerPolygon.add(new Point(0, 10, 0));
		outerPolygon.add(new Point(5, 10, 0));
		outerPolygon.add(new Point(10, 10, 0));
		outerPolygon.add(new Point(10, 10, 5));
		outerPolygon.add(new Point(0, 10, 5));

		// Loch erstellen
		List<Point> hole = new ArrayList<>();
		hole.add(new Point(1, 10, 1));
		hole.add(new Point(8, 10, 1));
		hole.add(new Point(8, 10, 4));
		hole.add(new Point(1, 10, 4));

		// Loch umkehren (wichtig für Poly2Tri)
		List<Point> reversedHole = hole.reversed();

		// Projektion durchführen
		List<List<PolygonPoint>> points2D = new ArrayList<>();
		Map<PolygonPoint, Point> pointMap = PolygonProjection.projectTo2D(List.of(outerPolygon, reversedHole),
				points2D);

		// Überprüfen, ob die Projektion die richtige Anzahl von Listen und Punkten hat
		assertEquals(2, points2D.size());
		assertEquals(5, points2D.get(0).size());
		assertEquals(4, points2D.get(1).size());

		// Überprüfen, ob die Zuordnung zwischen 2D- und 3D-Punkten korrekt ist
		assertEquals(9, pointMap.size()); // 5 + 4 Punkte insgesamt

		// Polygon für Triangulation erstellen
		Polygon polygon = new Polygon(points2D.get(0));
		polygon.addHole(new Polygon(points2D.get(1)));

		// Triangulation durchführen
		Poly2Tri.triangulate(polygon);

		// Überprüfen, ob Dreiecke erzeugt wurden
		List<DelaunayTriangle> triangles = polygon.getTriangles();
		assertFalse(triangles.isEmpty(), "Die Triangulation sollte Dreiecke erzeugen");

		// Überprüfen der Dreiecke
		for (DelaunayTriangle triangle : triangles) {
			assertEquals(3, triangle.points.length);
			// Alle Eckpunkte sollten in der pointMap enthalten sein
			for (int i = 0; i < 3; i++) {
				assertTrue(pointMap.containsKey(triangle.points[i]));
			}
		}
	}

	/**
	 * Testet die Fehlerbehandlung bei unzureichenden Eingabepunkten.
	 */
	@Test
	@DisplayName("Test der Fehlerbehandlung bei zu wenigen Punkten")
	public void testInsufficientPoints() {
		// Liste mit nur zwei Punkten erstellen
		List<Point> twoPoints = new ArrayList<>();
		twoPoints.add(new Point(0, 0, 0));
		twoPoints.add(new Point(1, 1, 1));

		List<List<PolygonPoint>> points2D = new ArrayList<>();

		// Es sollte eine IllegalArgumentException ausgelöst werden
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			PolygonProjection.projectTo2D(List.of(twoPoints), points2D);
		});

		System.out.println(exception.getMessage());
		assertTrue(exception.getMessage().contains("Not enough points to form a polygon"));
	}

	/**
	 * Testet die Fehlerbehandlung bei kollinearen Punkten.
	 */
	@Test
	@DisplayName("Test der Fehlerbehandlung bei kollinearen Punkten")
	public void testCollinearPoints() {
		// Liste mit drei kollinearen Punkten erstellen
		List<Point> collinearPoints = new ArrayList<>();
		collinearPoints.add(new Point(0, 0, 0));
		collinearPoints.add(new Point(1, 1, 1));
		collinearPoints.add(new Point(2, 2, 2));

		List<List<PolygonPoint>> points2D = new ArrayList<>();

		// Es sollte eine IllegalArgumentException ausgelöst werden
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			PolygonProjection.projectTo2D(List.of(collinearPoints), points2D);
		});

		assertTrue(exception.getMessage().contains("form a line, not a polygon"));
	}

	/**
	 * Testet die Fehlerbehandlung bei Null-Eingabeparametern.
	 */
	@Test
	@DisplayName("Test der Fehlerbehandlung bei Null-Eingabeparametern")
	public void testNullParameters() {
		List<List<PolygonPoint>> points2D = new ArrayList<>();

		// Test mit null als boundaries
		assertThrows(NullPointerException.class, () -> {
			PolygonProjection.projectTo2D(null, points2D);
		});

		// Liste erstellen
		List<Point> points = new ArrayList<>();
		points.add(new Point(0, 0, 0));
		points.add(new Point(1, 0, 0));
		points.add(new Point(0, 1, 0));

		// Test mit null als points2D
		assertThrows(NullPointerException.class, () -> {
			PolygonProjection.projectTo2D(List.of(points), null);
		});
	}
}