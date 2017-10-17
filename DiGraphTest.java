package edcc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edcc.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * This class contains a set of test cases that can be used 
 * to test the implementation of the DiGraph class.
 * <p>
 */
@SuppressWarnings("nullness")

public final class DiGraphTest {		    
	// create new graphs
	DiGraph d1, d2;
	@Before
	public void setUp(){		
		d1 = new DiGraph();
		d1.addNode("node1");
		d1.addNode("node2");
		d1.addNode("node3");
		d1.addEdge("node1", "node2");
		d1.addEdge("node2", "node3");
		d2 = new DiGraph();
		d2.addEdge("CS104", "CS202");
		d2.addEdge("CS107", "CS202");
		d2.addEdge("CS107", "CS210");
		d2.addEdge("CS210", "CS300");
		d2.addEdge("CS202", "CS300");					
	}
				
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	hasNode() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testHasNode() {
	   assertTrue(d1.hasNode("NODE1"));
	   assertTrue(d1.hasNode("NODE2"));
	   assertTrue(d1.hasNode("NODE3"));
	   assertFalse(d1.hasNode("NODE4"));
	   assertTrue(d2.hasNode("CS104"));
	   assertTrue(d2.hasNode("CS107"));
	   assertTrue(d2.hasNode("CS210"));	   
	   assertFalse(d2.hasNode("CS310"));
	   assertFalse(d2.hasNode("NODE4"));
	   assertFalse(d2.hasNode("CS200"));
	   
	}
			
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	NodeList()Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testNodeList() {
	   assertTrue(d1.NodeList().contains("NODE1"));
	   assertTrue(d1.NodeList().contains("NODE2"));
	   assertTrue(d1.NodeList().contains("NODE3"));
	   assertFalse(d1.NodeList().contains("NODE4"));
	   assertTrue(d2.NodeList().contains("CS104"));
	   assertTrue(d2.NodeList().contains("CS107"));
	   assertTrue(d2.NodeList().contains("CS210"));	   
	   assertFalse(d2.NodeList().contains("CS310"));
	   assertFalse(d2.NodeList().contains("NODE4"));
	   assertFalse(d2.NodeList().contains("CS200"));
	   
	}		
	
							
	///////////////////////////////////////////////////////////////////////////////////////
	////	topoSort Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testTopoSort() {  
		assertEquals("[NODE1, NODE2, NODE3]",d1.topoSort().toString()); 
	    assertEquals("[CS107, CS210, CS104, CS202, CS300]",d2.topoSort().toString());
	}		
}






