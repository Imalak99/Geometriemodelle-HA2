package tests;

import model.Cube;
import model.Point;

public class CubeTest {
	public static void main(String[] args) {

		Point p = new Point(0, 0, 0);
		Cube c = new Cube(p, 100);
		System.out.println(c);

	}

}
