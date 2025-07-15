package triangulation;

import java.util.List;

import javafx.scene.shape.TriangleMesh;
import model.Point;
import model.Polyhedron;

public abstract class Triangulation {

	public static TriangleMesh createTriangleMesh(Polyhedron polyhedron) {
		TriangleMesh t = new TriangleMesh();
		List<Point> points = polyhedron.getVertices();
		float[] pointArray = pointsToFloatArray(points);
		for (int i = 0; i < pointArray.length; i++) {
			System.out.println(pointArray[i]);
		}

//		hier muss alles passieren um die nächsten schritte einzuleiten
		t.getPoints().addAll(pointArray);

		int[] faces = pointsToFaceArray();
		for (int i = 0; i < faces.length; i++) {
			System.out.println(faces[i]);
		}

//		Hier muss eine liste von ints rein
		t.getFaces().addAll(faces[35], 0, faces[34], 0, faces[33], 0, faces[32], 0, faces[31], 0, faces[30], 0,
				faces[29], 0, faces[28], 0, faces[27], 0, faces[26], 0, faces[25], 0, faces[24], 0, faces[23], 0,
				faces[22], 0, faces[21], 0, faces[20], 0, faces[19], 0, faces[18], 0, faces[17], 0, faces[16], 0,
				faces[15], 0, faces[14], 0, faces[13], 0, faces[12], 0, faces[11], 0, faces[10], 0, faces[9], 0,
				faces[8], 0, faces[7], 0, faces[6], 0, faces[5], 0, faces[4], 0, faces[3], 0, faces[2], 0, faces[1], 0,
				faces[0], 0);
		t.getTexCoords().setAll(0, 0);
		return t;
	}

	private static int[] pointsToFaceArray() {
		int[] faceArray = new int[] { 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 0, 1, 5, 5, 4, 0, 1, 2, 6, 6, 5, 1, 2, 3, 7,
				7, 6, 2, 3, 0, 4, 4, 7, 3 };

		return faceArray;
	}

	private static float[] pointsToFloatArray(List<Point> points) {
		float[] pointArray = new float[points.size() * 3];
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			pointArray[i * 3] = (float) p.getX();
			pointArray[i * 3 + 1] = (float) p.getY();
			pointArray[i * 3 + 2] = (float) p.getZ();
		}
		return pointArray;
	}

}
