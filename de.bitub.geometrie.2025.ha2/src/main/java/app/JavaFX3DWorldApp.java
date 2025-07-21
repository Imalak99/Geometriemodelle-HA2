package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fx3D.JavaFX3DWorldGroup;
import fx3D.MeshFactory;
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
import tests.NeighborhoodAnalysisTest;
import tests.TriangulationTest;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// verschiedene Polyeder erzeugen und testen
		Polyhedron cube = Polyhedron.cube();
		Polyhedron facWithHole = TriangulationTest.faceWithHole();
		Polyhedron triangleFace = TriangulationTest.triangleFace();
		Polyhedron multiPointsFace = TriangulationTest.multiPointsFace();
		Polyhedron threeFacesPoly = NeighborhoodAnalysisTest.threeFacesPoly();
		Polyhedron cuboidGenus2 = Polyhedron.cuboidGenus2();
//		Polyhedron faceWithHoleAnAdditionalFace = NeighborhoodAnalysisTest.faceWithHoleAnAdditionalFace();

//		System.out.println("Anzahl Faces: " + cuboidGenus2.getFaces().size());
//		System.out.println("Anzahl Points: " + cuboidGenus2.getPoints().size());
//		System.out.println("anzahl HalfEdges: " + cuboidGenus2.getEdges().size());

		// Zum debuggen hier weiter machen!!
//		List<Face> facelist = cuboidGenus2.getFaces();
//		for (Face f : facelist) {
//			System.out.println("outer he " + f.getId() + "\n" + f.getOuterHalfEdge());
//			System.out.println("inner he " + f.getId() + "\n" + f.getHoles());
//		}

		Polyhedron icosahedron1 = Polyhedron.icosahedron();
		Polyhedron torus = Polyhedron.torus();
		Polyhedron torus2 = Polyhedron.torus(48, 24);
		Polyhedron cubeNonWaterTight = Polyhedron.cubeNonWaterTight();

		// Liste mit allen Polyedern
		List<Polyhedron> polyhedra = List.of(icosahedron1, torus, torus2, cube, facWithHole, triangleFace,
				multiPointsFace, threeFacesPoly, cuboidGenus2, cubeNonWaterTight);

		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

		Map<MeshView, Face> meshToFace = new HashMap<>();
		Map<Face, List<MeshView>> faceToMeshes = new HashMap<>();

		MeshFactory.createMeshViews(polyhedra, faceToMeshes, meshToFace, world);

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
