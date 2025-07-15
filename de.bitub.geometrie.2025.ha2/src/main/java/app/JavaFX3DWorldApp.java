package app;

import fx3D.JavaFX3DWorldGroup;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Polyhedron;
import triangulation.Triangulation;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	// Points: (0,0,0),(1,0,0),(1,1,0),(0,1,0)
	static final float[] P = { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f

	};

	// Triangle Faces: [0,1,2,3] -> [0,1,2][2,3,0]
	static final int[] F = { 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 0, 1, 5, 5, 4, 0, 1, 2, 6, 6, 5, 1, 2, 3, 7, 7, 6, 2,
			3, 0, 4, 4, 7, 3 };

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Polyhedron polyhedron = new Polyhedron(); // leeres Polyhedron-Objekt
		polyhedron.exampleCube(); // diese Methode noch schreiben

		TriangleMesh t = Triangulation.createTriangleMesh(polyhedron); // diese methode noch schreiben

//		Dass hier passiert dann alles in der createTriangleMesh Methode
//		TriangleMesh t = new TriangleMesh();
//		t.getPoints().addAll(P);
//		System.out.println(F.length);
//		t.getFaces().addAll(F[35], 0, F[34], 0, F[33], 0, F[32], 0, F[31], 0, F[30], 0, F[29], 0, F[28], 0, F[27], 0,
//				F[26], 0, F[25], 0, F[24], 0, F[23], 0, F[22], 0, F[21], 0, F[20], 0, F[19], 0, F[18], 0, F[17], 0,
//				F[16], 0, F[15], 0, F[14], 0, F[13], 0, F[12], 0, F[11], 0, F[10], 0, F[9], 0, F[8], 0, F[7], 0, F[6],
//				0, F[5], 0, F[4], 0, F[3], 0, F[2], 0, F[1], 0, F[0], 0);
//		t.getFaces().addAll(0, 0, 3, 0, 2, 0, 2, 0, 1, 0, 0,  );
//		t.getTexCoords().setAll(0, 0);

		MeshView m = new MeshView(t);

		m.setScaleX(1);
		m.setScaleY(1);
		m.setOnMouseClicked(e -> m.setRotate(m.getRotate() + 20));

		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();

//		world.getChildren().add(new MyCubeFX(2).getBox());
		world.getChildren().add(m);

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
