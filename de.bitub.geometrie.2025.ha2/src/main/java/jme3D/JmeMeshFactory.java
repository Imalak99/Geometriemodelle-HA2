package jme3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import logic.Triangulation;
import model.Face;
import model.Point;
import model.Polyhedron;

public class JmeMeshFactory {
	/**
	 * Erstellt für jede Fläche eines gegebenen Polyeders dreieckige Geometrien in
	 * jMonkeyEngine, fügt sie dem Szenengraphen hinzu und verknüpft sie über
	 * bidirektionale Mappings mit dem zugrundeliegenden Datenmodell.
	 *
	 * Jede Fläche wird trianguliert und die resultierenden Dreiecke in individuelle
	 * {@link Geometry}-Objekte überführt. Diese erhalten eine Kopie des übergebenen
	 * Materials, um spätere Farbänderungen zu ermöglichen.
	 *
	 * @param polyhedron       das zu visualisierende Polyeder
	 * @param faceToGeometries Mapping von Flächen auf zugehörige Geometrieobjekte
	 * @param geometryToFace   Mapping von Geometrien zurück auf das jeweilige Face
	 * @param rootNode         jME-Rootknoten, dem alle Geometrien hinzugefügt
	 *                         werden
	 * @param materialTemplate Vorlage für das Material aller erzeugten Geometrien
	 */
	public static void createGeometries(Polyhedron polyhedron, Map<Face, List<Geometry>> faceToGeometries,
			Map<Geometry, Face> geometryToFace, Node rootNode, Material materialTemplate) {

		for (Face face : polyhedron.getFaces()) {
			faceToGeometries.put(face, new ArrayList<>());
			List<List<Point>> triangles = Triangulation.triangulateFace(face);

			for (List<Point> tri : triangles) {
				float[] verts = new float[9]; // 3 Punkte à 3 Koordinaten
				int i = 0;
				for (Point p : tri) {
					verts[i++] = (float) p.getX();
					verts[i++] = (float) p.getY();
					verts[i++] = (float) p.getZ();
				}

				Mesh mesh = new Mesh();
				mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verts));
				mesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(new int[] { 0, 1, 2 }));
				mesh.updateBound();

				Geometry geom = new Geometry("Triangle", mesh);

				// Kopiere Material (damit später individuelle Farben möglich sind)
				Material mat = materialTemplate.clone();
				mat.setColor("Color", ColorRGBA.LightGray);
				geom.setMaterial(mat);

				rootNode.attachChild(geom);
				geometryToFace.put(geom, face);
				faceToGeometries.get(face).add(geom);
			}
		}
	}
}
