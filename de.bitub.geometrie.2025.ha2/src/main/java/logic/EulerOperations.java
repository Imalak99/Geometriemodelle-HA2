package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Face;
import model.HalfEdge;
import model.Polyhedron;

/**
 * Utility-Klasse zur Berechnung der Euler-Poincaré-Charakteristik und zur
 * topologischen Analyse eines Polyeders.
 */
public class EulerOperations {

	/**
	 * Berechnet die Euler-Poincaré-Charakteristik eines gegebenen Polyeders.
	 * Formel: χ = V − E + F − (L − F) − 2 · (S − G)
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return die berechnete Euler-Poincaré-Charakteristik
	 */
	public static int calcEulerPoincareCharacteristic(Polyhedron polyhedron) {
		int v = calcV(polyhedron);
		int e = calcE(polyhedron);
		int f = calcF(polyhedron);
		int l = calcL(polyhedron);
		int s = calcS(polyhedron);

		// Klassische Euler-Charakteristik (z. B. χ = 2 für Kugel, 0 für Torus)
		int chiClassic = v - e + f - (l - f);

		// Genus berechnen basierend auf klassischem χ
		int g = calcG(chiClassic, s);

		// Angepasster χ-Wert zur Validierung (sollte 0 sein bei watertight Struktur)
		int chiAdjusted = chiClassic - 2 * (s - g);

		boolean watertight = isWatertight(polyhedron);

		String resultString = String.format(
				"χ (klassisch)  = V - E + F - (L - F)\nχ = %d - %d + %d - (%d - %d) = %d\n" + "G (Genus)       = %d\n"
						+ "χ (angepasst)   = χ - 2 * (S - G) = %d - 2 * (%d - %d) = %d\n" + "Watertight:     %s",
				v, e, f, l, f, chiClassic, g, chiClassic, s, g, chiAdjusted, watertight ? "✔" : "✘");

		polyhedron.setEulerPoinCareString(resultString);
		polyhedron.setEulerPoinCare(chiAdjusted);

		return chiAdjusted;
	}

	/**
	 * Berechnet die Anzahl der eindeutigen Punkte (Vertices) des Polyeders.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Punkte
	 */
	private static int calcV(Polyhedron polyhedron) {
		int v = polyhedron.getPoints().size();
//		System.out.println("V (Vertecies): " + v);
		return v;
	}

	/**
	 * Berechnet die Anzahl der eindeutigen Kanten (Edges) des Polyeders. Hinweis:
	 * Es gibt jeweils zwei HalfEdges pro Kante.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Kanten
	 */
	private static int calcE(Polyhedron polyhedron) {
		List<HalfEdge> edges = polyhedron.getEdges();
		int e = eulerEdges(edges).size();
//		System.out.println("E (Edges): " + e);
		return e;
	}

	/**
	 * Berechnet die Anzahl der Flächen des Polyeders.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Flächen
	 */
	private static int calcF(Polyhedron polyhedron) {
		int f = polyhedron.getFaces().size();
//		System.out.println("F (Faces): " + f);
		return f;
	}

	/**
	 * Berechnet die Gesamtanzahl der Ränder (Loops) im Polyeder. Jeder Außenrand
	 * und jedes Loch zählt als eigener Loop.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Gesamtanzahl der Loops (L)
	 */
	private static int calcL(Polyhedron polyhedron) {
		int l = 0;
		for (Face face : polyhedron.getFaces()) {
			// 1 Außenrand + Anzahl Löcher
			l += 1 + face.getHoles().size();
		}
//		System.out.println("L (Loops): " + l);
		return l;
	}

	/**
	 * Berechnet die Anzahl der verbundenen Komponenten (Shells) des Polyeders.
	 * Aktuell wird davon ausgegangen, dass das Modell eine einzige zusammenhängende
	 * Komponente besitzt (S = 1).
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Shells
	 */
	private static int calcS(Polyhedron polyhedron) {
		int s = 1;
//		System.out.println("S (Shells): " + s);
		return s;
	}

	private static int calcG(int chi, int s) {
		int g = s - chi / 2;
		return g;
	}

	/**
	 * Gibt eine Liste aller "einzigartigen" Kanten zurück, wobei jede Kante nur
	 * einmal gezählt wird (unabhängig von ihrer Richtung).
	 *
	 * @param edges Liste aller HalfEdges im Polyeder
	 * @return Liste eindeutiger HalfEdges (Euler-Kanten)
	 */
	public static List<HalfEdge> eulerEdges(List<HalfEdge> edges) {
		Set<HalfEdge> visited = new HashSet<>();
		List<HalfEdge> uniqueEdges = new ArrayList<>();

		for (HalfEdge edge : edges) {
			HalfEdge twin = edge.getTwin();

			// Vermeide Doppelzählung: nur aufnehmen, wenn weder edge noch twin schon drin
			// sind
			if (!visited.contains(edge) && (twin == null || !visited.contains(twin))) {
				uniqueEdges.add(edge);
				visited.add(edge);
				if (twin != null) {
					visited.add(twin);
				}
			}
		}

		return uniqueEdges;
	}

	public static boolean isWatertight(Polyhedron polyhedron) {
		List<HalfEdge> edges = polyhedron.getEdges();
		Set<HalfEdge> visited = new HashSet<>();

		for (HalfEdge edge : edges) {
			if (visited.contains(edge))
				continue;

			HalfEdge twin = edge.getTwin();
			if (twin == null || twin.getTwin() != edge) {
				return false;
			}

			// beide markieren, damit sie nicht doppelt geprüft werden
			visited.add(edge);
			visited.add(twin);
		}

		return true;
	}

}
