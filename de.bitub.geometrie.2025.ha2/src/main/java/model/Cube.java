package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Box;

public class Cube {

	private Face[] faces;
	private Point[] vertices;
	private HalfEdge[] halfEdges;
	private Box box;
	private List<List<Point>> vertexList = new ArrayList<>();

	public Cube(Point p, double edgeLenght) {

		initVertecies(p, edgeLenght);
		initHalfEdges();
		initFaces();
		this.box = new Box(edgeLenght, edgeLenght, edgeLenght);

	}

	private void initFaces() {
		faces = new Face[6];
		faces[0] = new Face(halfEdges[0]); // Button Face
		faces[1] = new Face(halfEdges[1]); // Top Face
		faces[2] = new Face(halfEdges[2]); // Left Face
		faces[3] = new Face(halfEdges[3]); // Right Face
		faces[4] = new Face(halfEdges[4]); // Front Face
		faces[5] = new Face(halfEdges[5]); // Back Face

	}

	private void initHalfEdges() {

		halfEdges = new HalfEdge[6];

		// HalfEdges für Button Face
		List<Point> buttonFacePoints = new ArrayList<>();
		buttonFacePoints.add(vertices[0]);
		buttonFacePoints.add(vertices[1]);
		buttonFacePoints.add(vertices[2]);
		buttonFacePoints.add(vertices[3]);
		vertexList.add(buttonFacePoints);
		HalfEdge buttonFace = HalfEdgeUtil.buildPolygon(buttonFacePoints);
		halfEdges[0] = buttonFace;

		// HalfEdges für Top Face
		List<Point> topFacePoints = new ArrayList<>();
		topFacePoints.add(vertices[7]);
		topFacePoints.add(vertices[6]);
		topFacePoints.add(vertices[5]);
		topFacePoints.add(vertices[4]);
		vertexList.add(topFacePoints);
		HalfEdge topFace = HalfEdgeUtil.buildPolygon(topFacePoints);
		halfEdges[1] = topFace;

		// HalfEdges für Left Face
		List<Point> leftFacePoints = new ArrayList<>();
		leftFacePoints.add(vertices[3]);
		leftFacePoints.add(vertices[7]);
		leftFacePoints.add(vertices[4]);
		leftFacePoints.add(vertices[0]);
		vertexList.add(leftFacePoints);
		HalfEdge leftFace = HalfEdgeUtil.buildPolygon(leftFacePoints);
		halfEdges[2] = leftFace;

		// HalfEdges für Right Face
		List<Point> rightFacePoints = new ArrayList<>();
		rightFacePoints.add(vertices[1]);
		rightFacePoints.add(vertices[5]);
		rightFacePoints.add(vertices[6]);
		rightFacePoints.add(vertices[2]);
		vertexList.add(rightFacePoints);
		HalfEdge rightFace = HalfEdgeUtil.buildPolygon(rightFacePoints);
		halfEdges[3] = rightFace;

		// HalfEdges für Front Face
		List<Point> frontFacePoints = new ArrayList<>();
		frontFacePoints.add(vertices[2]);
		frontFacePoints.add(vertices[6]);
		frontFacePoints.add(vertices[7]);
		frontFacePoints.add(vertices[3]);
		vertexList.add(frontFacePoints);
		HalfEdge frontFace = HalfEdgeUtil.buildPolygon(frontFacePoints);
		halfEdges[4] = frontFace;

		// HalfEdges für Back Face
		List<Point> backFacePoints = new ArrayList<>();
		backFacePoints.add(vertices[0]);
		backFacePoints.add(vertices[4]);
		backFacePoints.add(vertices[5]);
		backFacePoints.add(vertices[1]);
		vertexList.add(backFacePoints);
		HalfEdge backFace = HalfEdgeUtil.buildPolygon(backFacePoints);
		halfEdges[5] = backFace;

	}

	private void initVertecies(Point p, double edgeLenght) {
		vertices = new Point[8];
		vertices[0] = p;
		vertices[1] = new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2]);
		vertices[2] = new Point(p.xyz[0] + edgeLenght, p.xyz[1], p.xyz[2] + edgeLenght);
		vertices[3] = new Point(p.xyz[0], p.xyz[1], p.xyz[2] + edgeLenght);
		vertices[4] = new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2]);
		vertices[5] = new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2]);
		vertices[6] = new Point(p.xyz[0] + edgeLenght, p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght);
		vertices[7] = new Point(p.xyz[0], p.xyz[1] + edgeLenght, p.xyz[2] + edgeLenght);
	}

	public List<List<Point>> getVertexList() {
		return vertexList;
	}

	public Box getBox() {
		return box;
	}

	public static Cube createExampelCube() {
		Point p = new Point(0, 0, 0);
		double edgeLength = 1;
		return new Cube(p, edgeLength);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cube with vertices:\n");
		int counter = 1;
		for (Point vertex : vertices) {
			sb.append(counter).append(": ").append(vertex.toString()).append("\n");
			counter++;
		}
		sb.append("HalfEdges:\n");
		if (halfEdges != null) {
			counter = 1;
			for (HalfEdge he : halfEdges) {
				if (he != null) {
					sb.append(counter).append(": ").append(he.toString()).append("\n");
				} else {
					sb.append(counter).append(": null\n");
				}
				counter++;
			}
		} else {
			sb.append("No halfEdges initialized.\n");
		}

		sb.append("Faces:\n");
		String[] faceNames = { "Button Face", "Top Face", "Left Face", "Right Face", "Front Face", "Back Face" };
		if (faces != null) {
			for (int i = 0; i < faces.length; i++) {
				sb.append(faceNames[i]).append(": ");
				if (faces[i] != null) {
					sb.append(faces[i].toString());
				} else {
					sb.append("null");
				}
				sb.append("\n");
			}
		} else {
			sb.append("No faces initialized.\n");
		}
		return sb.toString();
	}

}