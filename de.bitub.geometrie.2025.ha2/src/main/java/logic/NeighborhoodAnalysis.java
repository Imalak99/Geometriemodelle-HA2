package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import model.Face;
import model.HalfEdge;

public class NeighborhoodAnalysis {
	/**
	 * Führt eine Breitensuche (BFS) auf den Flächen eines Polyeders durch,
	 * beginnend bei der übergebenen Startfläche. Dabei wird für jede erreichbare
	 * Fläche die minimale topologische Entfernung (in Anzahl von Flächenübergängen)
	 * zur Startfläche berechnet.
	 *
	 * @param start die Startfläche für die BFS
	 * @return eine Map, die jeder Fläche ihren topologischen Level (Abstand) zur
	 *         Startfläche zuordnet
	 */
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

	/**
	 * Gibt alle benachbarten Flächen einer gegebenen Fläche zurück, basierend auf
	 * den {@code twin}-Beziehungen der Half-Edge-Struktur. Dabei werden sowohl die
	 * Außenkante als auch alle Lochkonturen berücksichtigt.
	 *
	 * @param face die Fläche, deren Nachbarn bestimmt werden sollen
	 * @return Liste aller angrenzenden (topologisch benachbarten) Flächen
	 */
	public static List<Face> getNeighborFaces(Face face) {
		List<Face> neighbors = new ArrayList<>();
		Set<Face> uniqueNeighbors = new HashSet<>();
		// Hilfsmethode für eine beliebige geschlossene HalfEdge-Schleife
		Consumer<HalfEdge> checkNeighbors = (HalfEdge start) -> {
			HalfEdge current = start;
			do {
				HalfEdge twin = current.getTwin();
				if (twin != null && twin.getBelongsToFace() != null && twin.getBelongsToFace() != face) {
					uniqueNeighbors.add(twin.getBelongsToFace());
				}
				current = current.getNext();
			} while (current != start);
		};
		// outer
		checkNeighbors.accept(face.getOuterHalfEdge());
		// holes
		for (HalfEdge hole : face.getHoles()) {
			checkNeighbors.accept(hole);
		}
		neighbors.addAll(uniqueNeighbors);
		return neighbors;
	}

}
