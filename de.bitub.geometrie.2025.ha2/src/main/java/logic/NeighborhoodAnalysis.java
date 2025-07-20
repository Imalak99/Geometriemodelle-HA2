package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import model.Face;
import model.HalfEdge;

public class NeighborhoodAnalysis {

	public static Map<Face, Integer> bfsFaceLevels(Face start) {
		Map<Face, Integer> faceLevel = new HashMap<>();
		Queue<Face> queue = new LinkedList<>();

		faceLevel.put(start, 0);
		queue.add(start);

		while (!queue.isEmpty()) {
			Face current = queue.poll();
			int currentLevel = faceLevel.get(current);

			for (Face neighbor : getNeighborFaces(current)) {
				if (!faceLevel.containsKey(neighbor)) {
					faceLevel.put(neighbor, currentLevel + 1);
					queue.add(neighbor);
				}
			}
		}

		return faceLevel;
	}

	public static List<Face> getNeighborFaces(Face face) {
		List<Face> neighbors = new ArrayList<>();
		HalfEdge start = face.getOuterHalfEdge();
		HalfEdge current = start;
		do {
			HalfEdge twin = current.getTwin();
			if (twin != null && twin.getBelongsToFace() != null && twin.getBelongsToFace() != face) {
				neighbors.add(twin.getBelongsToFace());
			}
			current = current.getNext();
		} while (current != start);
		return neighbors;
	}

}
