package jme3D;

import java.util.ArrayList;
import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class MyCubeJMonkey {

	private Vector3f[] corners;

	/**
	 * Indexed Face Set Cube with 6 surface areas, 8 vertices center of gravity is
	 * 0,0,0 !
	 * 
	 * @param length - length of the edges
	 */
	public MyCubeJMonkey(float length) {

		corners = new Vector3f[8];

		float a = length / 2;

		// TODO: Implement the cube!
		corners[0] = new Vector3f(-a, -a, -a);
		corners[1] = new Vector3f(a, -a, -a);
		corners[2] = new Vector3f(a, a, -a);
		corners[3] = new Vector3f(-a, a, -a);

		corners[4] = new Vector3f(-a, -a, a);
		corners[5] = new Vector3f(a, -a, a);
		corners[6] = new Vector3f(a, a, a);
		corners[7] = new Vector3f(-a, a, a);
	}

	/**
	 * Creates your own mesh for the cube
	 * 
	 * @return
	 */
	public Node createMeshes(AssetManager assetManager) {

		// Indexed Face Set: Polygons
		// ccw - counterclockwise
		int[][] polygons = new int[][] { { 3, 2, 1, 0 }, { 1, 2, 6, 5 }, { 0, 1, 5, 4 }, { 0, 4, 7, 3 }, { 2, 3, 7, 6 },
				{ 4, 5, 6, 7 } };

		int i = 0;
		Node box = new Node("Box"); // one mesh per face to make each face clickable

		for (int[] p : polygons) {

			// decomposition in triangles
			ArrayList<Integer> face = new ArrayList<Integer>();
			face.addAll(List.of(p[0], p[1], p[2]));
			face.addAll(List.of(p[2], p[3], p[0]));

			// Array from List
			int[] indexArray = face.stream().mapToInt(Integer::intValue).toArray();

			// Face mesh
			Mesh faceMesh = new Mesh();
			faceMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(corners));
			faceMesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexArray));
			faceMesh.updateBound();

			box.attachChild(new Geometry("Face" + i++, faceMesh));
		}

		return box;
	}

}
