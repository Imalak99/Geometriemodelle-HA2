package app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

	// Points: (0,0,0),(1,0,0),(1,1,0),(0,1,0)
	static final float[] P = { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0 };

	// Triangle Faces: [0,1,2,3] -> [0,1,2][2,3,0]
	static final int[] F = { 0, 1, 2, 2, 3, 0 };

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TriangleMesh t = new TriangleMesh();
		t.getPoints().addAll(P);
		t.getFaces().addAll(F[5], 0, F[4], 0, F[3], 0, F[2], 0, F[1], 0, F[0], 0);
		t.getTexCoords().setAll(0, 0);

		MeshView m = new MeshView(t);

		m.setScaleX(500);
		m.setScaleY(500);
		m.setOnMouseClicked(e -> m.setRotate(m.getRotate() + 20));

		primaryStage.setScene(new Scene(new Group(m), 640, 480, Color.DARKGREEN));
		primaryStage.show();
	}
}