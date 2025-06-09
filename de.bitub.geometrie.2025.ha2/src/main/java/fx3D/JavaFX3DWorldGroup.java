package fx3D;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * JavaFX 3D world with interactive camera controls and coordinate system.
 * Features: - Mouse drag: Rotate the scene around X and Y axes - Mouse scroll:
 * Zoom in/out along Z axis - Coordinate axes: Red (X), Green (Y), Blue (Z)
 */
public class JavaFX3DWorldGroup extends Group {

	// Scene dimensions
	private static final double WIDTH = 1400;
	private static final double HEIGHT = 1000;

	// Camera and interaction settings
	private static final double SCROLL_SPEED = 0.05;
	private static final double INITIAL_CAMERA_DISTANCE = -15;

	// Mouse interaction state
	private double anchorX, anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;

	// Scene transformations
	private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private final Rotate rotateY = new Rotate(0, Rotate.Z_AXIS);
	private final Translate translateZ = new Translate(0, 0, INITIAL_CAMERA_DISTANCE);

	public Scene subScene;

	public JavaFX3DWorldGroup() {
		subScene = createScene3D();
	}

	/**
	 * Creates the 3D scene with camera, lighting, and coordinate system
	 * 
	 * @return configured 3D scene
	 */
	private Scene createScene3D() {
		// Create scene with anti-aliasing for smoother rendering
		Scene scene = new Scene(this, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.WHITE);

		// Setup perspective camera
		PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.getTransforms().addAll(new Rotate(180, Rotate.Y_AXIS), // Flip camera orientation
				translateZ // Set initial distance
		);
		scene.setCamera(camera);

		// Apply rotation transforms to the root group
		this.getTransforms().addAll(rotateX, rotateY);

		// Add coordinate system to scene
		this.getChildren().add(createCoordinateSystem());

		// Setup mouse interaction handlers
		scene.setOnMousePressed(this::handleMousePressed);
		scene.setOnMouseDragged(this::handleMouseDragged);
		scene.setOnScroll(this::handleScroll);

		return scene;
	}

	/**
	 * Creates RGB coordinate system with cylinder axes
	 * 
	 * @return Group containing X (red), Y (green), Z (blue) axes
	 */
	private Group createCoordinateSystem() {
		final double AXIS_LENGTH = 20;
		final double AXIS_RADIUS = 0.05;

		// Create colored axes
		Cylinder xAxis = createAxis(AXIS_LENGTH, AXIS_RADIUS, Color.RED);
		Cylinder yAxis = createAxis(AXIS_LENGTH, AXIS_RADIUS, Color.GREEN);
		Cylinder zAxis = createAxis(AXIS_LENGTH, AXIS_RADIUS, Color.BLUE);

		// Rotate axes to proper orientations
		// Y-axis is vertical by default
		xAxis.getTransforms().add(new Rotate(90, Rotate.Z_AXIS)); // Horizontal X
		zAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS)); // Forward Z

		return new Group(xAxis, yAxis, zAxis);
	}

	/**
	 * Creates a cylinder axis with specified dimensions and color
	 * 
	 * @param length cylinder height
	 * @param radius cylinder radius
	 * @param color  axis color
	 * @return configured cylinder
	 */
	private Cylinder createAxis(double length, double radius, Color color) {
		Cylinder axis = new Cylinder(radius, length);
		axis.setMaterial(new PhongMaterial(color));
		return axis;
	}

	/**
	 * Handles mouse scroll for zoom in/out functionality
	 * 
	 * @param event scroll event containing delta information
	 */
	private void handleScroll(ScrollEvent event) {
		double deltaZ = event.getDeltaY() * SCROLL_SPEED;
		translateZ.setZ(translateZ.getZ() + deltaZ);
	}

	/**
	 * Handles mouse press - stores initial mouse position and current rotation
	 * angles
	 * 
	 * @param event mouse press event
	 */
	private void handleMousePressed(MouseEvent event) {
		anchorX = event.getSceneX();
		anchorY = event.getSceneY();
		anchorAngleX = rotateX.getAngle();
		anchorAngleY = rotateY.getAngle();
	}

	/**
	 * Handles mouse drag - updates scene rotation based on mouse movement
	 * 
	 * @param event mouse drag event
	 */
	private void handleMouseDragged(MouseEvent event) {
		double deltaX = event.getSceneX() - anchorX;
		double deltaY = event.getSceneY() - anchorY;

		// Update rotations: Y-mouse movement affects X-axis rotation (pitch)
		// X-mouse movement affects Y-axis rotation (yaw)
		rotateX.setAngle(anchorAngleX - deltaY);
		rotateY.setAngle(anchorAngleY + deltaX);
	}
}