package jme3D;

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
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;

/**
 * 3D scene with clickable box that changes color and camera controls. Left
 * click: toggle box color between red/blue Right click: enable camera movement
 */
public class SimpleBoxPicking extends SimpleApplication implements ActionListener {

	private Material redMaterial, blueMaterial;

	@Override
	public void simpleInitApp() {
		setupInput();
		setupCamera();
		createScene();
	}

	/** Create the 3D scene with box and coordinate axes */
	private void createScene() {

		// Create coordinate system (X=red, Y=green, Z=blue)
		createAxis(new Vector3f(2f, 0f, 0f), ColorRGBA.Red);
		createAxis(new Vector3f(0f, 2f, 0f), ColorRGBA.Green);
		createAxis(new Vector3f(0f, 0f, 2f), ColorRGBA.Blue);

		redMaterial = createMaterial(ColorRGBA.Red, "red");
		blueMaterial = createMaterial(ColorRGBA.Blue, "blue");

		// Create clickable box
		Node boxNode = new MyCubeJMonkey(2f).createMeshes(assetManager);
		boxNode.getChildren().forEach(g -> g.setMaterial(redMaterial));
		boxNode.setLocalTranslation(0, -2, 1);
		rootNode.attachChild(boxNode);
	}

	/** Configure mouse input controls */
	private void setupInput() {
		inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("CameraControl", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
		inputManager.addListener(this, "Pick", "CameraControl");
	}

	/** Position camera and configure cursor visibility */
	private void setupCamera() {
		cam.setLocation(new Vector3f(5, 0, 0));
		cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Z);
		flyCam.setUpVector(cam.getUp());
		cam.setLocation(new Vector3f(5, 0, 3));
		inputManager.setCursorVisible(true);
		flyCam.setEnabled(false);
	}

	/** Helper to create colored material */
	private Material createMaterial(ColorRGBA color, String name) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		mat.setName(name);
		return mat;
	}

	/** Create a coordinate axis arrow with specified direction and color */
	private void createAxis(Vector3f direction, ColorRGBA color) {
		Geometry axis = new Geometry("Axis", new Arrow(direction));
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		mat.getAdditionalRenderState().setWireframe(true);
		mat.getAdditionalRenderState().setLineWidth(8f);
		axis.setMaterial(mat);
		rootNode.attachChild(axis);
	}

	/** Handle mouse input events */
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("Pick") && !isPressed) {
			handleBoxClick();
		} else if (name.equals("CameraControl")) {
			toggleCameraControl(isPressed);
		}
	}

	/** Toggle box color when clicked */
	private void handleBoxClick() {
		CollisionResults results = getMouseCollision();
		if (results.size() > 0) {
			Geometry geo = results.getClosestCollision().getGeometry();
			boolean isRed = geo.getMaterial().getName().equals("red");
			geo.setMaterial(isRed ? blueMaterial : redMaterial);
			System.out.println("Box clicked! Changed to " + (isRed ? "blue" : "red"));
		} else {
			System.out.println("Nothing clicked!");
		}
	}

	/** Cast ray from mouse position and check for collisions */
	private CollisionResults getMouseCollision() {
		Vector2f mousePos = inputManager.getCursorPosition();
		Vector3f rayStart = cam.getWorldCoordinates(mousePos, 0f);
		Vector3f rayEnd = cam.getWorldCoordinates(mousePos, 1f);
		Vector3f rayDir = rayEnd.subtract(rayStart).normalizeLocal();

		Ray ray = new Ray(rayStart, rayDir);
		CollisionResults results = new CollisionResults();
		rootNode.collideWith(ray, results);
		return results;
	}

	/** Enable/disable camera movement with right mouse button */
	private void toggleCameraControl(boolean enable) {
		flyCam.setEnabled(enable);
		inputManager.setCursorVisible(!enable);
	}
}