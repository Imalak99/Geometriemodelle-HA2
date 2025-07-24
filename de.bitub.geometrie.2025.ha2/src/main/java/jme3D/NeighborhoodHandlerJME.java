package jme3D;

import java.util.List;
import java.util.Map;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

import logic.EulerOperations;
import logic.NeighborhoodAnalysis;
import model.Face;
import model.Polyhedron;

public class NeighborhoodHandlerJME {

	/**
	 * Führt eine kombinierte topologische Analyse in jMonkeyEngine durch, bestehend
	 * aus der Berechnung der Euler-Poincaré-Charakteristik und einer
	 * Nachbarschaftsanalyse mittels BFS. Anschließend werden alle betroffenen
	 * Geometrien farblich entsprechend ihrer topologischen Entfernung von der
	 * Startfläche eingefärbt.
	 *
	 * @param startFace        die vom Benutzer selektierte Startfläche
	 * @param faceToGeometries Mapping von Flächen auf ihre zugehörigen
	 *                         Geometrieobjekte
	 */
	public static void colorNeighborhood(Face startFace, Map<Face, List<Geometry>> faceToGeometries) {
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
			double t = (double) level / maxLevel;

			ColorRGBA color = hueGradient(t);

			List<Geometry> geometries = faceToGeometries.get(face);
			if (geometries != null) {
				for (Geometry g : geometries) {
					Material mat = g.getMaterial();
					mat.setColor("Color", color);
				}
			}
		}
	}

	/**
	 * Interpoliert eine Farbe entlang eines HSB-Farbverlaufs von Blau (t = 0) bis
	 * Rot (t = 1) und konvertiert das Ergebnis in ein {@link ColorRGBA}-Objekt zur
	 * Verwendung in jMonkeyEngine.
	 *
	 * @param t normierter Wert im Bereich [0, 1], der die Farbposition im Verlauf
	 *          angibt
	 * @return entsprechende RGBA-Farbe im jME-Format
	 */
	private static ColorRGBA hueGradient(double t) {
		// 240° (Blau) → 0° (Rot)
		float hue = (float) (240 - 240 * t); // 240 bis 0
		float saturation = 1.0f;
		float brightness = 1.0f;

		java.awt.Color awtColor = java.awt.Color.getHSBColor(hue / 360f, saturation, brightness);
		return new ColorRGBA(awtColor.getRed() / 255f, awtColor.getGreen() / 255f, awtColor.getBlue() / 255f, 1.0f);
	}
}
