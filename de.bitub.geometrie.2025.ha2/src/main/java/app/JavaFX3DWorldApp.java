package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fx3D.JavaFX3DWorldGroup;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Triangulation;
import model.Face;
import model.Point;
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
		Random rand = new Random();
		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

		Map<Face, List<MeshView>> faceToMeshes = new HashMap<>();

		// Durch jedes Face des Polyeders iterieren
		for (Face face : threeFacesPoly.getFaces()) {
			faceToMeshes.put(face, new ArrayList<>());
			// Liste erzeugen, die Listen mit Punkten jeder Fläche enthält
			// Jede innere Liste enthält die Punkte eines Dreiecks
			List<List<Point>> triangles = Triangulation.triangulateFace(face);
			// durch die Liste der Dreiecke iterieren
			for (List<Point> list : triangles) {
				TriangleMesh triangleMesh = new TriangleMesh();
				// durch die Punkte des Dreiecks iterieren
				for (Point p : list) {
					// dem triangleMesh die koordinaten der Punkte hinzufügen
					triangleMesh.getPoints().addAll((float) p.getX(), (float) p.getY(), (float) p.getZ());
				}
				// dem triangleMesh die Texturkoordinaten und die Dreiecksverbindung hinzufügen
				triangleMesh.getTexCoords().addAll(0, 0);
				triangleMesh.getFaces().addAll(0, 0, 1, 0, 2, 0);
				MeshView meshView = new MeshView(triangleMesh);
				meshView.setMaterial(
						new PhongMaterial(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble())));
				faceToMeshes.get(face).add(meshView);
				meshView.setScaleX(1);
				meshView.setScaleY(1);
				meshView.setOnMouseClicked(e -> {
					List<MeshView> meshesOfFace = faceToMeshes.get(face);
					for (MeshView mv : meshesOfFace) {
						mv.setMaterial(new PhongMaterial(Color.RED));
					}
				});
				world.getChildren().add(meshView);
			}
		}

		primaryStage.setScene(world.subScene);
		primaryStage.setOnCloseRequest(this::goodbye);
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
