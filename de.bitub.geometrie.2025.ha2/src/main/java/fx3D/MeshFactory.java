package fx3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import logic.Triangulation;
import model.Face;
import model.Point;
import model.Polyhedron;

public class MeshFactory {

	public static void createMeshViews(Polyhedron polyhedron, Map<Face, List<MeshView>> faceToMeshes,
			Map<MeshView, Face> meshToFace, Group world) {

		for (Face face : polyhedron.getFaces()) {
			faceToMeshes.put(face, new ArrayList<>());
			List<List<Point>> triangles = Triangulation.triangulateFace(face);

			for (List<Point> list : triangles) {
				TriangleMesh triangleMesh = new TriangleMesh();
				for (Point p : list) {
					triangleMesh.getPoints().addAll((float) p.getX(), (float) p.getY(), (float) p.getZ());
				}
				triangleMesh.getTexCoords().addAll(0, 0);
				triangleMesh.getFaces().addAll(0, 0, 1, 0, 2, 0);

				MeshView meshView = new MeshView(triangleMesh);
				meshToFace.put(meshView, face);
				faceToMeshes.get(face).add(meshView);

				meshView.setMaterial(new PhongMaterial(Color.LIGHTGRAY)); // Set default color to black );
				meshView.setScaleX(1);
				meshView.setScaleY(1);

				meshView.setOnMouseClicked(e -> {
					Face clickedFace = meshToFace.get(meshView);
					NeighborhoodHandler.colorNeighborhood(clickedFace, faceToMeshes);
				});

				world.getChildren().add(meshView);
			}
		}
	}

	public static void createMeshViews(List<Polyhedron> polyhedra, Map<Face, List<MeshView>> faceToMeshes,
			Map<MeshView, Face> meshToFace, Group world) {

		double offsetX = 0;

		for (Polyhedron polyhedron : polyhedra) {
			Group subGroup = new Group();

			// für jedes Polyhedron: erzeugt MeshViews und fügt sie in subGroup ein
			createMeshViews(polyhedron, faceToMeshes, meshToFace, subGroup);

			// Abstand setzen (verschiebt das gesamte Objekt in X-Richtung)
			subGroup.setTranslateX(offsetX);
			world.getChildren().add(subGroup);

			// nächstes Objekt 5 Einheiten weiter nach rechts (kannst du anpassen)
			offsetX += 7;
		}
	}

}
