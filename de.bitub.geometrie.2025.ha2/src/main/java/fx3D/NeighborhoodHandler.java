package fx3D;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import logic.NeighborhoodAnalysis;
import model.Face;

public class NeighborhoodHandler {

	private static Color getColorForLevel(int level) {
		return switch (level) {
		case 0 -> Color.RED;
		case 1 -> Color.ORANGE;
		case 2 -> Color.YELLOW;
		case 3 -> Color.LIGHTGREEN;
		default -> Color.LIGHTGRAY;
		};
	}

	public static void colorNeighborhood(Face startFace, Map<Face, List<MeshView>> faceToMeshes) {
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

	private static Color interpolateColor(Color start, Color end, double t) {
		double r = start.getRed() + (end.getRed() - start.getRed()) * t;
		double g = start.getGreen() + (end.getGreen() - start.getGreen()) * t;
		double b = start.getBlue() + (end.getBlue() - start.getBlue()) * t;
		return new Color(r, g, b, 1.0);
	}

}
