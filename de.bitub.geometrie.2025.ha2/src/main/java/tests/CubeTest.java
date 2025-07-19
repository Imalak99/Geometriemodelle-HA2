package tests;

import model.Face;
import model.Polyhedron;

public class CubeTest {

	public static void main(String[] args) {
		Polyhedron cube = Polyhedron.cube();

		Face c = cube.getFaces().get(0);
		System.out.println(c.getOuterHalfEdge());
//		System.out.println(cube.getEdges().size() + " Kanten");

//		for (Face f : cube.getFaces()) {
//			System.out.println(f.getHalfEdge());
//			System.out.println(" twin:" + f.getHalfEdge().getTwin());
//
//		}
//		System.out.println(cube);
	}

}
