package algorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Node_metadata;
import dataStructure.edge_data;
import dataStructure.edge_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

class FWTest {

	@Test
	void testFW() {
		
		node_data n1 = new Node_metadata(0, new Point3D(1,2,3));
		node_data n2 = new Node_metadata(1, new Point3D(1,2,3));
		node_data n3 = new Node_metadata(2, new Point3D(1,2,3));
		

		
		graph g = new DGraph();
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		
		g.connect(0, 1, 1);
		g.connect(1, 2, 2);
		g.connect(0, 2, 5);
		
		FW fw = new FW(g);
		
		
		
		
	}


}
