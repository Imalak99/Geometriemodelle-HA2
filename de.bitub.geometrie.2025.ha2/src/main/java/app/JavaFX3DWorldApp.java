package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fx3D.JavaFX3DWorldGroup;
import fx3D.JavaFXMeshFactory;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Face;
import model.Polyhedron;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// verschiedene Polyeder erzeugen und testen
//		Polyhedron cube = Polyhedron.cube();
//		Polyhedron facWithHole = TriangulationTest.faceWithHole();
//		Polyhedron triangleFace = TriangulationTest.triangleFace();
//		Polyhedron multiPointsFace = TriangulationTest.multiPointsFace();
//		Polyhedron threeFacesPoly = NeighborhoodAnalysisTest.threeFacesPoly();

//		Polyhedron faceWithHoleAnAdditionalFace = NeighborhoodAnalysisTest.faceWithHoleAnAdditionalFace();

		Polyhedron icosahedron = Polyhedron.icosahedron();
		Polyhedron cubeNonWaterTight = Polyhedron.cubeNonWaterTight();
		Polyhedron torus = Polyhedron.torus(48, 24);
		Polyhedron cuboidGenus2 = Polyhedron.cuboidGenus2();

		List<Polyhedron> polyhedra = List.of(icosahedron, cubeNonWaterTight, torus, cuboidGenus2);

		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

		Map<MeshView, Face> meshToFace = new HashMap<>();
		Map<Face, List<MeshView>> faceToMeshes = new HashMap<>();

		JavaFXMeshFactory.createMeshViews(polyhedra, faceToMeshes, meshToFace, world);

		primaryStage.setScene(world.subScene);
//		primaryStage.setOnCloseRequest(this::goodbye);
		primaryStage.show();
	}

	/**
	 * Good Luck! 祝你順利 !
	 * 
	 * @param event
	 */
	private void goodbye(WindowEvent event) {

		Alert goodbye = new Alert(AlertType.CONFIRMATION);

		goodbye.setTitle("Good Bye!");
		goodbye.getDialogPane().setGraphic(new ImageView(new Image("file:src/main/resources/huhu.png")));
		goodbye.setHeaderText("Thänk You For Using This Template\n祝你順利 ");
		goodbye.showAndWait();

	}

}
