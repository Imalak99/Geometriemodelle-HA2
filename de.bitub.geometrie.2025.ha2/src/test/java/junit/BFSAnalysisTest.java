package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import logic.NeighborhoodAnalysis;
import model.Face;
import model.Polyhedron;

public class BFSAnalysisTest {

	@Test
	public void testBFSLevelCountCuboidGenus2() {
		// Erzeuge das Polyeder
		Polyhedron poly = Polyhedron.cuboidGenus2();

		// Wähle eine Startfläche
		Face startFace = poly.getFaces().get(0);

		// Führe BFS durch
		Map<Face, Integer> levels = NeighborhoodAnalysis.bfsFaceLevels(startFace);

		// Erwartung: Alle 14 Flächen sollten erreicht werden
		assertEquals(poly.getFaces().size(), levels.size(), "Alle Faces sollten im BFS erreichbar sein");

		// Überprüfe: Startfläche ist Level 0
		assertEquals(0, levels.get(startFace), "Startfläche muss Level 0 sein");

		// Überprüfe: Es existieren mindestens 3 unterschiedliche Level
		long distinctLevelCount = levels.values().stream().distinct().count();
		assertTrue(distinctLevelCount >= 3, "Topologische Tiefe sollte mindestens 3 betragen");
	}
}
