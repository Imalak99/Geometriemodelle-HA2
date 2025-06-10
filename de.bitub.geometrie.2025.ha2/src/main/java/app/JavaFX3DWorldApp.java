package app;

import fx3D.JavaFX3DWorldGroup;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Cube;

/** JavaFX Application to run the 3D example */
public class JavaFX3DWorldApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create World and add Model
		JavaFX3DWorldGroup world = new JavaFX3DWorldGroup();
		Cube c = Cube.createExampelCube();

		world.getChildren().add(c.getBox()); // Add the cube to the world
//		world.getChildren().add(new MyCubeFX(2).getBox());

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
