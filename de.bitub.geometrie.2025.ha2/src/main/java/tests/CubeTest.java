package tests;

import javafx.scene.shape.TriangleMesh;
import model.Polyhedron;
import triangulation.Triangulation;

public class CubeTest {
	public static void main(String[] args) {

		Polyhedron polyhedron = new Polyhedron(); // leeres Polyhedron-Objekt
		polyhedron.exampleCube(); // diese Methode noch schreiben
		System.out.println(polyhedron.toString());
		TriangleMesh t = Triangulation.createTriangleMesh(polyhedron); // diese methode noch schreibe

	}

}
