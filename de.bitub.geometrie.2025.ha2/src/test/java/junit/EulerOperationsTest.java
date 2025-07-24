package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import logic.EulerOperations;
import model.Polyhedron;

public class EulerOperationsTest {

	@Test
	public void testIcosahedronIsWatertight() {
		Polyhedron icosahedron = Polyhedron.icosahedron();
		int chi = EulerOperations.calcEulerPoincareCharacteristic(icosahedron);
		assertEquals(0, chi, "Watertight icosahedron should have χ_adjusted = 0");
		assertTrue(EulerOperations.isWatertight(icosahedron), "Icosahedron should be watertight");
	}

	@Test
	public void testTorusGenusOne() {
		Polyhedron torus = Polyhedron.torus(48, 24);
		EulerOperations.calcEulerPoincareCharacteristic(torus);
		String summary = torus.getEulerPoinCareString();
		assertTrue(summary.contains("G (Genus)       = 1"), "Torus should have Genus = 1");
	}

	@Test
	public void testNonWatertightCubeFails() {
		Polyhedron nonWatertight = Polyhedron.cubeNonWaterTight();
		boolean watertight = EulerOperations.isWatertight(nonWatertight);
		assertFalse(watertight, "Deliberately broken cube should not be watertight");
	}
}
