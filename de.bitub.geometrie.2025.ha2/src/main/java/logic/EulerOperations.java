package logic;

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
		System.out.println("calcEulerPoincareCharacteristic aufgerufen");
		int v = calcV(polyhedron);
		int e = calcE(polyhedron);
		int f = calcF(polyhedron);
		int l = calcL(polyhedron);
		int s = calcS(polyhedron);
		int g = calcG(polyhedron);

		return v - e + f - (l - f) - 2 * (s - g);
	}

	/**
	 * Berechnet die Anzahl der eindeutigen Punkte (Vertices) des Polyeders.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Punkte
	 */
	private static int calcV(Polyhedron polyhedron) {
		return polyhedron.getPoints().size();
	}

	/**
	 * Berechnet die Anzahl der eindeutigen Kanten (Edges) des Polyeders. Hinweis:
	 * Es gibt jeweils zwei HalfEdges pro Kante.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Kanten
	 */
	private static int calcE(Polyhedron polyhedron) {
		// Das hier muss noch angepasst werden, da bei Geometrien mit Faces die "offen"
		// sind keine doppelten Kanten vorhanden sind !!!!!
		return polyhedron.getEdges().size() / 2; // Jede Kante hat zwei HalfEdges
	}

	/**
	 * Berechnet die Anzahl der Flächen des Polyeders.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Flächen
	 */
	private static int calcF(Polyhedron polyhedron) {
		return polyhedron.getFaces().size();
	}

	/**
	 * Berechnet die Gesamtanzahl der Ränder (Loops), d. h. äußere und innere
	 * Umrandungen (z. B. Löcher) aller Flächen.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der Ränder (Loops)
	 */
	private static int calcL(Polyhedron polyhedron) {
		// TODO: Implement logic
		int l = 1;
		return l;
	}

	/**
	 * Berechnet die Anzahl der verbundenen Komponenten (Shells) des Polyeders.
	 * Meistens 1, wenn das Modell zusammenhängend ist.
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return Anzahl der verbundenen Komponenten
	 */
	private static int calcS(Polyhedron polyhedron) {
		// TODO: Implement logic
		int s = 1;
		return s;
	}

	/**
	 * Berechnet den Genus des Polyeders, also die Anzahl der "Henkel" bzw. Tunnel.
	 * Beispiel: Würfel → Genus 0, Torus → Genus 1
	 *
	 * @param polyhedron das zu analysierende Polyeder
	 * @return der Genus des Polyeders
	 */
	private static int calcG(Polyhedron polyhedron) {
		// TODO: Implement logic
		int g = 1;
		return g;
	}
}
