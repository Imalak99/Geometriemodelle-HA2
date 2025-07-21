package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

import jme3D.JmeMeshFactory;
import model.Face;
import model.Polyhedron;
import tests.TriangulationTest;

public class JMonkeyApp extends SimpleApplication {

	private final Map<Face, List<Geometry>> faceToGeometries = new HashMap<>();
	private final Map<Geometry, Face> geometryToFace = new HashMap<>();

	public static void main(String[] args) {
		new JMonkeyApp().start();
	}

	@Override
	public void simpleInitApp() {
		// Beispiel-Polyeder laden
		Polyhedron polyhedron = TriangulationTest.triangleFace();
		Polyhedron cube = Polyhedron.cube();
		Polyhedron torus = Polyhedron.torus();

		// Material-Vorlage erstellen
		Material materialTemplate = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		materialTemplate.setColor("Color", ColorRGBA.LightGray);

		// Darstellung erzeugen
		JmeMeshFactory.createGeometries(torus, faceToGeometries, geometryToFace, rootNode, materialTemplate);

		// UI & Kamera Einstellungen
		this.setShowSettings(false);
		this.setDisplayStatView(false);
		this.inputManager.setCursorVisible(true);
		this.flyCam.setDragToRotate(true);

		// Maus-Klick-Handler vorbereiten
		inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(clickListener, "Pick");
	}

	// Picking-Handler (noch auskommentiert)
	private final ActionListener clickListener = new ActionListener() {
		@Override
		public void onAction(String name, boolean isPressed, float tpf) {
			if (!isPressed)
				return;
			pickFace();
		}
	};

	private void pickFace() {
		System.out.println("pickFace() called");

		// TODO: Picking-Logik hier einfügen
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f click3d = cam.getWorldCoordinates(click2d, 0f).clone();
		Vector3f dir = cam.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();

		Ray ray = new Ray(click3d, dir);
		CollisionResults results = new CollisionResults();
		rootNode.collideWith(ray, results);

		if (results.size() > 0) {
			Geometry closest = results.getClosestCollision().getGeometry();
			Face clickedFace = geometryToFace.get(closest);
			System.out.println("Face clicked: " + clickedFace);

			highlightFaceGeometries(clickedFace);
		}
	}

	// Optional: später zum Färben verwendbar
	private void highlightFaceGeometries(Face face) {
		List<Geometry> geometries = faceToGeometries.get(face);
		if (geometries != null) {
			for (Geometry g : geometries) {
				g.getMaterial().setColor("Color", ColorRGBA.Red);
			}
		}
	}
}
