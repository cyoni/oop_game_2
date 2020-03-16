package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.line;
import utils.Point3D;

class lineTest {

	
	@Test
	void testGetPointOnLine() {
		Point3D p1 = new Point3D(1,1);
		Point3D p2 = new Point3D(3,3);
		// double[] _vector = line.getPointOnLine(p1, p2, 50);
		// double[] expected = {2,2};
		// assertArrayEquals(expected, _vector);
	}

	@Test
	void testIsIn() {
		Point3D p1 = new Point3D(1,1);
		Point3D p2 = new Point3D(3,3);
		System.out.println(line.isIn(p1, p2, new Point3D(1,2)));
	    assertTrue(line.isIn(p1, p2, new Point3D(2,2)));
	    assertFalse(line.isIn(p1, p2, new Point3D(1,2)));
		
	}
	
	@Test
	void testdistanceBetweenLineAndPoint() {
		Point3D p1 = new Point3D(130,180);
		Point3D p2 = new Point3D(210,450);
		Point3D p = new Point3D(521, 946);
		double[] xx = line.distanceBetweenLineAnd2Points(p2, p1, p);
		System.out.println(xx[0]);
		
	}

}
