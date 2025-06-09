package projection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testet die VectorUtils-Klasse, die für die Vektoroperationen in der
 * PolygonProjection-Klasse verantwortlich ist.
 */
public class VectorUtilsTest {

	// Konstante für den Vergleich von Gleitkommazahlen
	private static final double DELTA = 1e-10;

	@Test
	@DisplayName("Test für die subtract-Methode")
	public void testSubtract() {
		double[] v1 = { 3.0, 4.0, 5.0 };
		double[] v2 = { 1.0, 2.0, 3.0 };

		double[] result = PolygonProjection.VectorUtils.subtract(v1, v2);

		assertEquals(3, result.length);
		assertEquals(2.0, result[0], DELTA);
		assertEquals(2.0, result[1], DELTA);
		assertEquals(2.0, result[2], DELTA);
	}

	@Test
	@DisplayName("Test für die crossProduct-Methode")
	public void testCrossProduct() {
		double[] v1 = { 2.0, 3.0, 4.0 };
		double[] v2 = { 5.0, 6.0, 7.0 };

		double[] result = PolygonProjection.VectorUtils.crossProduct(v1, v2);

		assertEquals(3, result.length);
		assertEquals(-3.0, result[0], DELTA);
		assertEquals(6.0, result[1], DELTA);
		assertEquals(-3.0, result[2], DELTA);
	}

	@Test
	@DisplayName("Test für die dotProduct-Methode")
	public void testDotProduct() {
		double[] v1 = { 1.0, 2.0, 3.0 };
		double[] v2 = { 4.0, 5.0, 6.0 };

		double result = PolygonProjection.VectorUtils.dotProduct(v1, v2);

		assertEquals(32.0, result, DELTA);
	}

	@Test
	@DisplayName("Test für die normalize-Methode mit gültigem Vektor")
	public void testNormalizeValidVector() {
		double[] v = { 3.0, 0.0, 4.0 };

		double[] result = PolygonProjection.VectorUtils.normalize(v);

		assertEquals(3, result.length);
		assertEquals(0.6, result[0], DELTA);
		assertEquals(0.0, result[1], DELTA);
		assertEquals(0.8, result[2], DELTA);
	}

	@Test
	@DisplayName("Test für die normalize-Methode mit Nullvektor")
	public void testNormalizeZeroVector() {
		double[] v = { 0.0, 0.0, 0.0 };

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			PolygonProjection.VectorUtils.normalize(v);
		});

		assertTrue(exception.getMessage().contains("Cannot normalize a zero-length vector"));
	}

	@Test
	@DisplayName("Test für die getLength-Methode")
	public void testGetLength() {
		double[] v = { 3.0, 4.0, 0.0 };

		double result = PolygonProjection.VectorUtils.getLength(v);

		assertEquals(5.0, result, DELTA);
	}

	@Test
	@DisplayName("Test für die getLength-Methode mit Nullvektor")
	public void testGetLengthZeroVector() {
		double[] v = { 0.0, 0.0, 0.0 };

		double result = PolygonProjection.VectorUtils.getLength(v);

		assertEquals(0.0, result, DELTA);
	}
}