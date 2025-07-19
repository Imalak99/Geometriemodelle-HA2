package app;

import java.util.List;
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
import model.Face;
import model.Point;
import model.Polyhedron;
import triangulation.Triangulation;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Cube erzeugen
		Polyhedron cube = Polyhedron.cube();
		Random rand = new Random();
		System.out.println("Das ist der Cube\n" + cube);
		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

		for (Face face : cube.getFaces()) {

			List<List<Point>> triangles = Triangulation.triangulateFace(face);
			for (List<Point> list : triangles) {
				TriangleMesh triangleMesh = new TriangleMesh();
				System.out.println(list);
				for (Point p : list) {
					System.out.println(p);
					triangleMesh.getPoints().addAll((float) p.getX(), (float) p.getY(), (float) p.getZ());

				}
				triangleMesh.getTexCoords().addAll(0, 0);
				triangleMesh.getFaces().addAll(0, 0, 1, 0, 2, 0);
				MeshView meshView = new MeshView(triangleMesh);
				meshView.setMaterial(
						new PhongMaterial(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble())));

				meshView.setScaleX(1);
				meshView.setScaleY(1);
				meshView.setOnMouseClicked(e -> meshView.setRotate(meshView.getRotate() + 20));
				world.getChildren().add(meshView);
			}
		}

		// Triangulation der einzelnen Flächen des Polyeders

//		List<Point> trianlge = triangles.get(0);

		// Dummy texture coords (required)

		// One triangle

//		TriangleMesh mesh = Triangulation.createTriangleMesh(cube);

//		System.out.println(mesh.getPoints());

		// Create MeshView for the TriangleMesh

		// Set random colors for the triangles

		// Set Scene and start application
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
