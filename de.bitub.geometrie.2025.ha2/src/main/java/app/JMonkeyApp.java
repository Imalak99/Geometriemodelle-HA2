package app;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class JMonkeyApp extends SimpleApplication {

	// Points: (0,0,0),(1,0,0),(1,1,0),(0,1,0)
	static final float[] P = { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0 };

	// Triangle Faces: [0,1,2,3] -> [0,1,2][2,3,0]
//	static final int[] F = { 0, 1, 2, 2, 3, 0 };
	static final int[] F = { 0, 1, 2 };

	public static void main(String[] args) {
		new JMonkeyApp().start();
	}

	@Override
	public void simpleInitApp() {
		Mesh m = new Mesh();

		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(P));
		m.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(F));
		m.updateBound();

		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.White);
		Geometry g = new Geometry("Mesh", m, mat);
		rootNode.attachChild(g);

		this.setShowSettings(false);
		this.setDisplayStatView(false);
		this.inputManager.setCursorVisible(true);
		this.flyCam.setDragToRotate(true);
	}
}