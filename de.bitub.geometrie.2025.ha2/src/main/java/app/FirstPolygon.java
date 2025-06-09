package app;

import java.util.ArrayList;
import java.util.List;

import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * Example of showing a triangulated polygon in JavaFX in 2D.
 * 
 */
public class FirstPolygon extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public Polygon examplePolygone() {

		Polygon p = new Polygon(List.of(new PolygonPoint(0, 0), new PolygonPoint(800, 0), new PolygonPoint(800, 400),
				new PolygonPoint(1200, 400), new PolygonPoint(1200, 800), new PolygonPoint(0, 800)));

		p.addHole(new Polygon(List.of(new PolygonPoint(200, 200), new PolygonPoint(200, 600),
				new PolygonPoint(600, 600), new PolygonPoint(600, 200))));

		return p;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Polygon p = examplePolygone();

		Poly2Tri.triangulate(p);

		List<Shape> shapes = createShapesFromTriangles(p);

		primaryStage.setScene(new Scene(new Pane(shapes.toArray(Shape[]::new))));
		primaryStage.show();
	}

	private List<Shape> createShapesFromTriangles(Polygon p) {
		List<Shape> shapes = new ArrayList<>();

		for (DelaunayTriangle t : p.getTriangles()) {
			shapes.add(new javafx.scene.shape.Polygon( // each polygon is a triangle
					t.points[0].getX(), t.points[0].getY(), // point 0
					t.points[1].getX(), t.points[1].getY(), // point 1
					t.points[2].getX(), t.points[2].getY())); // point 2
			shapes.getLast().setFill(Color.color(Math.random(), Math.random(), Math.random()));

		}
		return shapes;
	}

}
