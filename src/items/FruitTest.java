package items;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.line;
import utils.Point3D;

class FruitTest {

	@Test
	void testGetEdge() {
		
		Point3D p1 = new Point3D(190, 865);
		Point3D p2 = new Point3D(420, 84);
		Point3D p3 = new Point3D(204, 595);
		
		if (line.isIn(p1, p2, p3)) {
			System.out.println("yes");
			
			}
		else 
			System.out.println("no");

		
	}

}
