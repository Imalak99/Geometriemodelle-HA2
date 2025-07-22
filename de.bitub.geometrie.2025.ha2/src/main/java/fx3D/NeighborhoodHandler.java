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

	public static void colorNeighborhood(Face startFace, Map<Face, List<MeshView>> faceToMeshes) {
		// Diese ganze EulerPoincare Logic kann noch ausgelagert werden, sodass es einen
		// allgemienen handler gibt, der BSF und Euler macht

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

	private static Color hueGradient(double t) {
		// Hue von 240° (blau) bis 0° (rot) – t ∈ [0, 1]
		double hue = 240 - 240 * t; // 240 = Blau, 0 = Rot
		return Color.hsb(hue, 1.0, 1.0);
	}

}
