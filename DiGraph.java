package edcc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <b>diGraph</B> is a mutable finite number of nodes and their associated edges.
 * <p>
 * Each diGraph contains some nodes {n1, n2, ..., n_k }, and some edges {e1, e2, ..., e_s} that 
 * connects these nodes. Note that node order and edge order may be different and some nodes may not have 
 * any edges connecting them. Nodes and their associated node and edges are stored accordingly using map.
 * <p>
 * Nodes {n1, n2, ..., n_k } are stored in the Map digraph as follows:
 *     digraph.put(n1,ArrayList_of_map for connection between another node and it's associated egde);
 *     .
 *     .
 *     .
 *     digraph.put(n_k,ArrayList_of_map for connection between another node and it's associated egde);
 * 
 *     
 * @author 
 */

public class DiGraph {
	
	/** Holds all the nodes and there associated connecting nodes and edges in this DiGraph */
	private final HashMap<String, Set<String>> digraph;

	
	  // Abstraction Function:
	  // DiGraph, d, represents a graph containing all the nodes and the edges connecting 
	  // these nodes. These nodes and theirs associated nodes and edges are stores in a hashMap.
	  // Nodes {n1, n2, ..., n_k } are put in the map as the key in the same order as they are added 
	  // to the graph, and the key in the map would be the same as {n1, n2, ..., n_k }.
	  // Each key in the map would have an associated value, ArrayList<HashMap <String,String>>> arr to 
	  // store all the tail node and the associated edges connecting them. For example, node n_i and n_k
	  // is connected by the edge e_k, and node n_i and n_j is connected by the edge e_j, then the digraph
	  // will look like HashMap<n_i, ArrayList<HashMap<n_k,e_k>,HashMap<n_j,e_j>>>.

	  // Because ArrayList<HashMap <String,String>> is used to store the tail node and edges, 
	  // DiGraph, d allows multiple edges between the same nodes even with the same edge label.
	  // It also supports an edge connecting a node to itself.

	  // Representation Invariant for every DiGraph d:
	  // d != null && every node and edge cannot be null
	  // Any null node cannot be connected by a node or edge in the graph
	  // In other words, all nodes and edges at the end of each operation must be in this graph

	
	
    /**
     * @effects constructs an empty hashMap
     */
	public DiGraph(){
		digraph=new HashMap<String, Set<String>>();
	}
	
	
    /**
     * Add a node
     * @param vertex node to be added to the graph
     * @requires vertex is not in the graph and !vertex.equals(null)
     * @modifies this
     * @effects add the vertex to the graph
     * @return true if the node is added successfully, otherwise false
     */
	public boolean addNode(String vertex){
		String temp = vertex.toUpperCase();
		boolean result=false;
		if((!digraph.containsKey(vertex))&&(!vertex.equals(null))){			
		    digraph.put(temp, new HashSet<String>());
		    result=true;
		}
		return result;
	}
	
	
    /**
     * Add a connection between two nodes 
     * @param head,tail nodes to be connected by the edge
     * @requires head and tail are not null
     * @modifies this
     * @effects add a connection connecting head and tail
     * @return true if the edge is added successfully, otherwise false
     */
	public boolean addEdge(String head, String tail) {
		boolean result=false;
        // check if the passed parameter is null
		if((!head.equals(null))&&(!tail.equals(null))){
			// add head to the diGraph if head is not in the graph
			if(!hasNode(head)){
				addNode(head);
			}
			// add tail to the diGraph if tail is not in the graph
			if(!hasNode(tail)){
				addNode(tail);
			}
			// add new connection if no connection exists
			if(!digraph.get(head.toUpperCase()).contains(tail.toUpperCase())){
				digraph.get(head.toUpperCase()).add(tail.toUpperCase());
				result=true;
			}
		}
		return result;		
	}
		
		
	
    /**
     * Check if the graph has the node 
     * @param node to be checked 
     * @requires !node.equals(null) 
     * @return true if the node is in the graph, otherwise false
     */
	public boolean hasNode(String node){
		String temp = node.toUpperCase();
		return digraph.containsKey(temp);
	}	

	
    /**
     * sort the graph using topological sorting 
     * 
     * @return an arraylist to store the topological sort
     */	
	public List<String> topoSort(){
		List<String> result = new ArrayList();
		Set<String> visitSet = new HashSet<String>();
		Set<String> nodes = NodeList();
		while(visitSet.size() < digraph.size()){
			for(String node : nodes){
				if(!visitSet.contains(node)){
					dfs(node, visitSet, result);
				}
			}
		}
		for(int i=0;i<result.size()/2;i++){
			String temp = result.get(result.size()-1-i);
			result.set(result.size()-1-i, result.get(i));
			result.set(i, temp);
		}
		return result;		
	}

    /**
     * dfs search algorithm to recursively sort the graph
     * @param head, the starting node to be searched on using dfs
     * @param set, the set to store which nodes have been visited
     * @param result, the list to store the sorted graph
     * @requires head, set and result are not null
     * @modifies set, result
     * @effects add head to the visitedSet set and all the sort to result
     * 
     */
	private void dfs(String head, Set<String> set, List result){
		set.add(head);		
		for(String node : digraph.get(head)){
			if(!set.contains(node)){
				dfs(node, set, result);
			}
		}
		result.add(head);
	}	
	
    /**
     * Get all nodes in this graph
     * @requires the graph is not null
     * @return a set containing all the nodes in this graph
     */
	public Set<String> NodeList(){		
		return new TreeSet<String>(digraph.keySet());
	}										    		
}


