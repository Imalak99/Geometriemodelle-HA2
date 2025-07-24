package fx3D;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import logic.EulerOperations;
import logic.NeighborhoodAnalysis;
import model.Face;
import model.Polyhedron;

public class NeighborhoodHandler {
	/**
	 * Führt eine kombinierte topologische Analyse durch, bestehend aus der
	 * Berechnung der Euler-Poincaré-Charakteristik und einer Nachbarschaftsanalyse
	 * mittels Breitensuche (BFS) ausgehend von einer selektierten Fläche. Das
	 * Ergebnis wird in der Konsole ausgegeben und in der Visualisierung durch
	 * farbliche Markierung der Flächenebenen dargestellt.
	 *
	 * @param startFace    die vom Benutzer selektierte Startfläche
	 * @param faceToMeshes Mapping von Flächen auf ihre zugehörigen MeshViews in der
	 *                     Szene
	 */
	public static void handler(Face startFace, Map<Face, List<MeshView>> faceToMeshes) {
		Polyhedron polyhedron = startFace.getBelongsToPolyhedron();
		EulerOperations.calcEulerPoincareCharacteristic(polyhedron);
		System.out.println(polyhedron.getName());
		System.out.println(polyhedron.getEulerPoinCareString());
		System.out.println("\n");

		Map<Face, Integer> levels = NeighborhoodAnalysis.bfsFaceLevels(startFace);
		int maxLevel = levels.values().stream().max(Integer::compareTo).orElse(1);
		for (Map.Entry<Face, Integer> entry : levels.entrySet()) {
			Face face = entry.getKey();
			int level = entry.getValue();
			double t = (double) level / maxLevel; // normalisiert auf [0, 1]
			Color color = hueGradient(t); // ← statt interpolateColor()
			for (MeshView mv : faceToMeshes.get(face)) {
				mv.setMaterial(new PhongMaterial(color));
			}
		}
	}

	/**
	 * Berechnet eine Farbe entlang eines HSB-Farbverlaufs von Blau (0) bis Rot (1),
	 * basierend auf einem normierten Wert im Intervall [0, 1]. Wird z.B. zur
	 * farblichen Kodierung von Nachbarschaftsleveln verwendet.
	 *
	 * @param t normierter Wert zwischen 0 (blau) und 1 (rot)
	 * @return interpolierte Farbe als {@link Color}
	 */
	private static Color hueGradient(double t) {
		// Hue von 240° (blau) bis 0° (rot) – t ∈ [0, 1]
		double hue = 240 - 240 * t; // 240 = Blau, 0 = Rot
		return Color.hsb(hue, 1.0, 1.0);
	}

}
