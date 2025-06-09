package fx3D;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MyCubeFX {

	// Example model with transformation and Listeners
	private Box b;

	public MyCubeFX(double a) {

		// Example model with transformation and Listeners
		b = new Box(a, a, a);

		b.setTranslateX(2);

		setFunnyToolTip(b);
		setBoxMouseListeners(b);

	}

	public Box getBox() {
		return b;
	}

	/**
	 * Create a tool tip that is shown when hovering over the box.
	 * 
	 * @param b
	 */
	private void setFunnyToolTip(Box b) {
		Tooltip tip = new Tooltip("CLICK ME!\n點我！");
		tip.setFont(Font.font(22));
		tip.setTextAlignment(TextAlignment.CENTER);
		tip.setGraphic(new ImageView(new Image("file:src/main/resources/dance-vibing.gif")));
		tip.setShowDelay(Duration.millis(100)); // Show after 100ms instead of default ~1000ms
		Tooltip.install(b, tip);
	}

	/**
	 * Creates three example listeners for mouse interaction
	 * 
	 * @param b
	 */
	private void setBoxMouseListeners(Box b) {
		b.setOnMouseClicked(this::randomOnClick);
		b.setOnMouseEntered(event -> b.setMaterial(new PhongMaterial(Color.RED)));
		b.setOnMouseExited(event -> b.setMaterial(new PhongMaterial(Color.AQUA)));

	}

	/**
	 * Alternative functional event handling (::) by calling a method to process
	 * event. Lambda Notation (event -> method call) also possible.
	 * 
	 * Right-Click: Changes color randomly // Left-Click: Box jumps away randomly
	 * 
	 * @param event
	 */
	private void randomOnClick(MouseEvent event) {
		Shape3D box = (Shape3D) event.getSource();

		if (event.getButton() == MouseButton.PRIMARY) {

			box.setMaterial(new PhongMaterial(Color.color(Math.random(), Math.random(), Math.random())));
		} else {

			box.setTranslateX((Math.random() - 0.5) * 10);
			box.setTranslateY((Math.random() - 0.5) * 10);
			box.setTranslateZ((Math.random() - 0.5) * 10);
		}
	}

}
