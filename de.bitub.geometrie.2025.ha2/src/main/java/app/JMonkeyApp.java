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
import jme3D.NeighborhoodHandlerJME;
import model.Face;
import model.Polyhedron;

public class JMonkeyApp extends SimpleApplication {

	private final Map<Face, List<Geometry>> faceToGeometries = new HashMap<>();
	private final Map<Geometry, Face> geometryToFace = new HashMap<>();

	public static void main(String[] args) {
		new JMonkeyApp().start();
	}

	@Override
	public void simpleInitApp() {
		// Beispiel-Polyeder laden
		Polyhedron icosahedron = Polyhedron.icosahedron();
		Polyhedron torus = Polyhedron.torus(48, 24);
		Polyhedron cubeNonWaterTight = Polyhedron.cubeNonWaterTight();

		// Material-Vorlage erstellen
		Material materialTemplate = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		materialTemplate.setColor("Color", ColorRGBA.LightGray);

		// Darstellung erzeugen
		JmeMeshFactory.createGeometries(cubeNonWaterTight, faceToGeometries, geometryToFace, rootNode,
				materialTemplate);

		// UI & Kamera Einstellungen
		this.setShowSettings(false);
		this.setDisplayStatView(false);
		this.inputManager.setCursorVisible(true);
		this.flyCam.setDragToRotate(true);

		// Maus-Klick-Handler vorbereiten
		inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(clickListener, "Pick");
	}

	private final ActionListener clickListener = new ActionListener() {
		@Override
		public void onAction(String name, boolean isPressed, float tpf) {
			if (!isPressed)
				return;
			pickFace();
		}
	};

	private void pickFace() {
		Vector2f click2d = inputManager.getCursorPosition();
		Vector3f origin = cam.getWorldCoordinates(click2d, 0f).clone();
		Vector3f direction = cam.getWorldCoordinates(click2d, 1f).subtractLocal(origin).normalizeLocal();
		Ray ray = new Ray(origin, direction);

		CollisionResults results = new CollisionResults();
		rootNode.collideWith(ray, results);

		if (results.size() > 0) {
			Geometry clickedGeometry = results.getClosestCollision().getGeometry();
			Face clickedFace = geometryToFace.get(clickedGeometry);
			if (clickedFace != null) {
				NeighborhoodHandlerJME.colorNeighborhood(clickedFace, faceToGeometries);
			}
		}
	}
}
