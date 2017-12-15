package grafoFacebook;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import facebook_sol.EDEdge;

public interface EDGraph<T,W> {

	//returns number of nodes of the graph
	int getSize();
	
	//true if the graph has no nodes; false in other case
	boolean isEmpty();
	
	//inserts a new node with the label item
	int insertNode(T item);
	
	//inserts a new edge
	boolean insertEdge(EDEdge<W> edge);
	
	//given the label of a node, returns the index of the position
	//in the array where is stored
	int getNodeIndex(T item);
	
	//given the position of a node, returns the node element
	//returns null if an empty or wrong position
	T getNodeValue(int index);
	
	//given the indices of two nodes, returns the Edge that joins the
	//two nodes, if any. If there is no edge, then returns null
	//The direction source --> dest is important if the graph is directed
	EDEdge<W> getEdge(int source, int dest);
	
	//removes a node
	T removeNode(int index);
	
	//removes an edge
	EDEdge<W> removeEdge(int source, int dest, W weight);

	//returns a Set with the index of the adjacent nodes
	Set<Integer> getAdyacentNodes(int index);
	
	
	//prints the structure
	void printGraphStructure();


	Set<T> getNodes();

	int[] distanceToAll(T item);

	Set<T> common(T item1, T item2);

	Set<T> suggest(T item);

	T mostPopular();

	void saveGraphStructure(RandomAccessFile f);
	
}
