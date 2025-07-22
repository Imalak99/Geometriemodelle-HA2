package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fx3D.JavaFX3DWorldGroup;
import fx3D.JavaFXMeshFactory;
import javafx.application.Application;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import model.Face;
import model.Polyhedron;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Polyhedron icosahedron = Polyhedron.icosahedron();
		Polyhedron cubeNonWaterTight = Polyhedron.cubeNonWaterTight();
		Polyhedron torus = Polyhedron.torus(48, 24);
		Polyhedron cuboidGenus2 = Polyhedron.cuboidGenus2();

		List<Polyhedron> polyhedra = List.of(icosahedron, cubeNonWaterTight, torus, cuboidGenus2);

		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

		Map<MeshView, Face> meshToFace = new HashMap<>();
		Map<Face, List<MeshView>> faceToMeshes = new HashMap<>();

		JavaFXMeshFactory.createMeshViews(polyhedra, faceToMeshes, meshToFace, world);

		primaryStage.setScene(world.subScene);
		primaryStage.show();
	}

}
