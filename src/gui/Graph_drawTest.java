package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Node_metadata;
import dataStructure.graph;
import utils.Point3D;

class Graph_drawTest { // TODO

	@Test
	void testDraw() {
    	Node_metadata n1 = new Node_metadata(0, new Point3D(12,90,3), 3);
    	Node_metadata n2 = new Node_metadata(1, new Point3D(5,50,3), 2);
    	Node_metadata n3 = new Node_metadata(2, new Point3D(48,30,3), 5);
    	Node_metadata n4 = new Node_metadata(3, new Point3D(15,55,3), 1);
    	Node_metadata n5 = new Node_metadata(4, new Point3D(65,64,3), 2);
    	
    	
    	graph myGraph = new DGraph();
    	
    	myGraph.addNode(n1);
    	myGraph.addNode(n2);
    	myGraph.addNode(n3);
    	myGraph.addNode(n4);
    	myGraph.addNode(n5);

    	
    	myGraph.connect(0, 1, 2);
    	myGraph.connect(1, 3, 1);
    	myGraph.connect(0, 2, 10);
    	myGraph.connect(2, 3, 15);
    	myGraph.connect(1, 4, 2);
    	myGraph.connect(2, 1, 2);

    	Graph_draw gd = new Graph_draw();
		gd.draw(myGraph);

	}

}
