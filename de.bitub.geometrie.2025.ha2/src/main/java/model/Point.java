package model;

/**
 * Einfache Punkt-Klasse zur Verwendung mit der PolygonProjection-Klasse. Diese
 * Klasse repräsentiert einen 3D-Punkt mit x, y und z Koordinaten.
 */
public class Point {
	/**
	 * Die x, y und z Koordinaten des Punktes als Array.
	 */
	public final double[] xyz;

	/**
	 * Erstellt einen neuen Punkt mit den angegebenen Koordinaten.
	 *
	 * @param x die x-Koordinate
	 * @param y die y-Koordinate
	 * @param z die z-Koordinate
	 */
	public Point(double x, double y, double z) {
		this.xyz = new double[] { x, y, z };
	}

	/**
	 * Gibt eine String-Repräsentation des Punktes zurück.
	 *
	 * @return eine String-Repräsentation des Punktes
	 */
	@Override
	public String toString() {
		return String.format("Point(%.2f, %.2f, %.2f)", xyz[0], xyz[1], xyz[2]);
	}

	/**
	 * Vergleicht diesen Punkt mit einem anderen Objekt auf Gleichheit.
	 *
	 * @param obj das zu vergleichende Objekt
	 * @return true, wenn die Objekte gleich sind, sonst false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Point other = (Point) obj;

		// Vergleich der Koordinaten mit einer kleinen Toleranz
		final double EPSILON = 1e-10;
		return Math.abs(xyz[0] - other.xyz[0]) < EPSILON && Math.abs(xyz[1] - other.xyz[1]) < EPSILON
				&& Math.abs(xyz[2] - other.xyz[2]) < EPSILON;
	}

	/**
	 * Berechnet den Hashcode für diesen Punkt.
	 *
	 * @return der Hashcode
	 */
	@Override
	public int hashCode() {
		int result = 17;
		for (double coord : xyz) {
			long bits = Double.doubleToLongBits(coord);
			result = 31 * result + (int) (bits ^ (bits >>> 32));
		}
		return result;
	}
}