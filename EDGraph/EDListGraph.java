package grafoFacebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;


/** Implementation of interface Graph using adjacency lists
 * @param <T> The base type of the nodes
 * @param <W> The base type of the weights of the edges
 */
public class EDListGraph<T,W> implements EDGraph<T,W> {
	@SuppressWarnings("hiding")
	private class Node<T> {
		T data;
		List< EDEdge<W> > lEdges;
		
		Node (T data) {
			this.data = data;
			this.lEdges = new LinkedList< EDEdge<W> >();
		}
		public boolean equals (Object other) {
			if (this == other) return true;
			if (!(other instanceof Node)) return false;
			//System.out.println("equals de node");
			Node<T> anotherNode = (Node<T>) other;
			return data.equals(anotherNode.data);
		}
	}
	
	// Private data
	private ArrayList<Node<T>> nodes;  //Vector of nodes
	private int size; //real number of nodes
	private boolean directed;
	
	/** Constructor
	 * @param direct <code>true</code> for directed edges; 
	 * <code>false</code> for non directed edges.
	 */

	public EDListGraph() {
		directed = false; //not directed
		nodes =  new ArrayList<Node<T>>();
		size =0;
	}
	
	public EDListGraph (boolean dir) {
		directed = dir;
		nodes =  new ArrayList<Node<T>>();
		size =0;
	}
	
	public int getSize() {
		return size;
	}

	public int nodesSize() {
		return nodes.size();
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public int insertNode(T item) {
			
	    int i = 0;
	    while (i<nodes.size() && nodes.get(i).data != null) i++;
				
	    Node<T> newNode = new Node<T>(item);
	    if (i<nodes.size()) nodes.set(i,newNode);
	    else nodes.add(newNode);
	    size++;
	    //System.out.println("Insertado en posicion "+i);
	    return i;
	}
	
	@Override
	public int getNodeIndex(T item) {
		Node<T> aux = new Node<T>(item);
		return nodes.indexOf(aux);
	}

	@Override
	public T getNodeValue(int index) throws IndexOutOfBoundsException{
		
		return nodes.get(index).data;
		
	}
	
	@Override
	public boolean insertEdge(EDEdge<W> edge) {
		int sourceIndex = edge.getSource();
		int targetIndex = edge.getTarget();
		if (sourceIndex >=0 && sourceIndex<nodes.size() && targetIndex >=0 && targetIndex<nodes.size()) {
			Node<T> nodeSr = nodes.get(sourceIndex);
			Node<T> nodeTa = nodes.get(targetIndex);
			if (nodeSr.data!=null && nodeTa.data != null) {
			   if (!nodeSr.lEdges.contains(edge)) {
				   nodeSr.lEdges.add(edge);
				   nodes.set(sourceIndex,nodeSr); 
				   if (!directed) {//no dirigido
					  EDEdge<W> reverse = new EDEdge<W>(targetIndex,sourceIndex,edge.getWeight());
					  nodeTa.lEdges.add(reverse);
					  nodes.set(targetIndex, nodeTa);
				   }
				   return true;
			    }
			   else System.out.println("The graph has already this edge: "+edge.toString());
			}
		}
		return false;
	}
	
	@Override
	public EDEdge<W> getEdge (int source, int dest) {
		if (source <0 || source >= nodes.size()) return null;
		
			Node<T> node = nodes.get(source);
			if (node.data == null ) return null;
			for (EDEdge<W> edge: node.lEdges)
				if (edge.getTarget() == dest) return edge;
			
			return null;
	}
	
	
	
	@Override
	public EDEdge<W> removeEdge(int source, int target, W weight) {
		if (source <0 || source >= nodes.size() || target<0 || target >= nodes.size()) return null;
		if (nodes.get(source).data!=null && nodes.get(target).data!=null) {
			EDEdge<W> edge = new EDEdge<W>(source, target, weight);
			Node<T> node = nodes.get(source);
			int i = node.lEdges.indexOf(edge);
			if (i != -1) {
				edge = node.lEdges.remove(i);
				if (!directed) {
					EDEdge<W> reverse = new EDEdge<W>(target,source,weight);
					nodes.get(target).lEdges.remove(reverse);
				}
				return edge;
			}	
		}
		return null;	
	}

	@Override
	public T removeNode(int index) {
		if (index >=0 && index < nodes.size()){
			if (!directed) {
				Node<T> node = nodes.get(index);
				for (EDEdge<W> edge: node.lEdges ) {
					int target = edge.getTarget();
					W label = edge.getWeight();
					EDEdge<W> other = new EDEdge<W>(target,index,label);
					nodes.get(target).lEdges.remove(other);
				}
			}
			else { //directed
				for (int i=0; i<nodes.size(); i++) {
					if (i!=index && nodes.get(i).data !=null) {
						Node<T> node = nodes.get(i);
						for (EDEdge<W> edge: node.lEdges) {
							if (index == edge.getTarget()) //any weight/label
								node.lEdges.remove(edge);
						}
					}
				}
			}
			
			Node<T> node = nodes.get(index);
			node.lEdges.clear();
			T ret = node.data;
			node.data = null; //It is not remove, data is set to null
			nodes.set(index, node);
			size--;
			System.out.println("Borrada posicion: "+index);
			return ret;
		}
		return null;
	}
	
	/**  Set<Integer> getAdyacentNodes(index)
	 *  Devuelve el conjunto de nodos adyacentes al nodo de �ndice index
	 */
	@Override
	public Set<Integer> getAdyacentNodes(int index) {
		if (index < 0 || index >= nodes.size()) return new HashSet<Integer>();
		
		Set<Integer> ret = new HashSet<Integer>();
		for (EDEdge<W> ed: nodes.get(index).lEdges) {
			ret.add(ed.getTarget());
		}
		
		return ret;
	}
	
	
	/** int[] distanceToAll (T item)
	 * Devuelve la distancia (grados de separaci�n) del nodo con etiqueta item al resto de nodos.
	 * Devuelve el resultado en un vector con una posici�n por cada nodo.
	 * Si item no pertece al grafo, devuelve null
	 * Nota: Usar el recorrido en anchura
	 */
	@Override
	public int[] distanceToAll (T item) {
		
	}
	
	
	/** Set<T> common(T item1, T item2)
	 * Devuelve un conjunto con las etiquetas de los nodos comunes entre item1 e item2
	 * Si item1 o item2 no pertenecen al grafo, devuelve null
	 */
	public Set<T> common(T item1, T item2) {
		
	}
	
	
	/** Set<T> suggest(T item)
	 * Devuelve un conjunto con las etiquetas de los nodos que esten a distancia 2 del nodo
	 * con etiqueta item (es decir, sugiere amigos al nodo item, en funcion de los amigos de este).
	 * Si item no esta en el grafo, devuelve null
	 */
	public Set<T> suggest(T item) {
		Set<T> commomFriends = new HashSet<>();
		
		if (getNodeIndex(item)<0) {
			return null;
		}

		//recorrer todos los nodos
		for (Node<T> node : nodes) {
			//en node tendremos cada nodo del vector
 			//buscar nodo con etiqueta item
			if (node.data.equals(item)) {//he encontrado el nodo que buscaba
				int actualIndex = getNodeIndex(node.data);//indice de item
				//busco en la lista del nodo que he encontrado aquellos nodos target que tienen la resta de indices <=2
				for (EDEdge<W> edge : node.lEdges) {
					int searchedIndex =edge.getTarget();//el nodo al  que apunta el arco del Nodo que esta en actualIndex
//					System.out.println("1 "+nodes.get(searchedIndex).data);
					if (!commomFriends.contains(getNodeValue(searchedIndex))) {
						commomFriends.add(getNodeValue(searchedIndex));
					}
					for (EDEdge<W> edgeFriends :nodes.get(searchedIndex).lEdges ) {//lista de arcos de los amigos de amigos
						int searchedIndexFriend =edgeFriends.getTarget();//indice de los amigos de amigos de item
						
						if (!commomFriends.contains(getNodeValue(searchedIndexFriend)) && (getNodeIndex(item) != searchedIndexFriend) ) {
							commomFriends.add(getNodeValue(searchedIndexFriend));
							commomFriends.add(getNodeValue(searchedIndex));
 
						}
					}//end for Friends of Friends
				}//end for Friends
				
			}
			
		}//end for nodes
		for (T t : commomFriends) {
			System.out.println(t);
		}
		return commomFriends;
		 
	}
	
	/** T mostPopular()
	 * Devuelve la etiqueta del nodo con m�s arcos (m�s amigos). Si el grafo est� vac�o,
	 * devuelve null.
	 */
	public T mostPopular() {
		if (getNodes().isEmpty()) {
			return null;
		}
		int maxDist=0;
		T res = null;
		for (int i = 0; i < nodes.size(); i++) {//vector de los nodos
			 int ed = nodes.get(i).lEdges.size(); //lista de arcos de cada nodo
				if (ed > maxDist) {
					maxDist = ed;//el valor del nodo con mas amigos
					res = getNodeValue(i);
				}			
		}
		return res;
		
	}
	
	
	public void printGraphStructure() {
		//System.out.println("Vector size= " + nodes.length);
		System.out.println("Vector size " + nodes.size());
		System.out.println("Nodes: "+ this.getSize());
		for (int i=0; i<nodes.size(); i++) {
			System.out.print("pos "+i+": ");
	        Node<T> node = nodes.get(i);
			System.out.print(node.data+" -- ");
			Iterator<EDEdge<W>> it = node.lEdges.listIterator();
			while (it.hasNext()) {
					EDEdge<W> e = it.next();
					System.out.print("("+e.getSource()+","+e.getTarget()+", "+e.getWeight()+")->" );
			}
			System.out.println();
		}
	}
	
	
	@Override
	public void saveGraphStructure(RandomAccessFile f) {
		
		
			try {
				f.writeInt(this.size);
			 //numero de nodos
				//System.out.println("tama�o grafo "+this.size);
				//f.seek(0);
				//System.out.println("leido: "+f.readInt());
				for (int i=0; i<nodes.size();i++) {
				if (nodes.get(i)!=null) {
					f.writeUTF((String) nodes.get(i).data);
					f.writeInt(nodes.get(i).lEdges.size());
					for (EDEdge<W> edge: nodes.get(i).lEdges)
						f.writeInt(edge.getTarget());
				}
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public Set<T> getNodes() {
		Set<T> s = new HashSet<T>();
		for (int i=0; i<nodes.size(); i++) {
			if (nodes.get(i).data!=null) 
				s.add(nodes.get(i).data);
		}
		return s;
	}


}
