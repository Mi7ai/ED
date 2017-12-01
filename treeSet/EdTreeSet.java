package treeSet;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;

public class EdTreeSet<E extends Comparable <E>> implements Set<E>{

	protected class BinaryNode {
		protected E data;
		protected BinaryNode left;
		protected BinaryNode right;
		
		BinaryNode(E data){
			this.data = data;
		}
		BinaryNode(E data, BinaryNode lnode, BinaryNode rnode) {
			this.data = data;
			this.left = lnode;
			this.right = rnode;
		}
	}
	
	private BinaryNode root;
	private Comparator<? super E> comparator;
	private int size; //number of elements
	protected boolean insertReturn; //Return value for the public insert method
	protected boolean removeReturn; //Return value for the public remove method

	

	public EdTreeSet() {
		root = null;
		comparator = null;
		size = 0;
	}
	
	public EdTreeSet(Comparator<? super E> comp) {
		root = null;
		comparator = comp;
		size = 0;
	}
	
	public EdTreeSet(Collection<? extends E> c) {
		this();
		addAll(c);
	}
	
	public EdTreeSet(SortedSet<E> s) {
		this(s.comparator());
		addAll(s);
	}
	
	private int compare(E left, E right) {
		if (comparator != null) { //A comparator is defined
			return comparator.compare(left,right);
		}
		else {
			return (((Comparable<E>) left).compareTo(right));
		}
	}
	
	/**
	 * E first()
	 * Returns the first (lowest) element currently in this set.
	Throws:
	NoSuchElementException - if this set is empty
	*/
	public E first() {
		BinaryNode r = root;
		if (root.data == null) {
			throw new NoSuchElementException();
		}		
		while (r.left!=null) {
			r=r.left;
		}
		return r.data;
	}

   
	/**
	 * E last()
	*Returns the last (highest) element currently in this set.
	*@Return:
	* the last (highest) element currently in this set
	* Throws:
	*NoSuchElementException - if this set is empty
	 */
	public E last() {
		BinaryNode r = root;
		if (root.data == null) {
			throw new NoSuchElementException();
		}		
		while (r.right!=null) {
			r=r.right;
		}
		return r.data;
	
	}
	
	
	/** boolean contains(Object o)
	  * Returns true if this set contains the specified element.
	  * Parameters:
		o - element whose presence in this set is to be tested
	  * Returns:
		true if this set contains the specified element
	*/
	@Override
	public boolean contains(Object item) {
		E item2 = (E) item;
		
		if (item != null) {
			return contains(root,item2);
		}		
		return false;
	}
	
	private boolean contains(BinaryNode a, E item){
		
		if (a != null) {
			if (compare(a.data, item)==0) {
				return true;
			}else if (compare(a.data, item)<0) {
				return contains(a.right,item);
			}else if (compare(a.data, item)>0) {
				return contains(a.left,item);
			}
		}		
		return false;
		
	}
	
	
	@Override
	/**
	 * boolean add(E e)
	Adds the specified element to this set if it is not already present
 	Returns:
	true if this set did not already contain the specified element
	 */
	public boolean add(E item) {
//		if (item==(null)) {
//			return false;
//		}
		root = add(root, item); 
		return insertReturn;
	}
	private BinaryNode add(BinaryNode r,E item){		 
		if (r == null) {
			r= new BinaryNode(item);
			insertReturn = true;
			size++;
			return r;
		}else if (compare(item, r.data)<0) {
			r.left=add(r.left, item);
		}else if (compare(item, r.data)>0) {
			r.right=add(r.right, item);
		}else{
			insertReturn = false;
		}
		
		return r;
	}
	
	/** boolean remove(Object o)
	 * Removes the specified element from this set if it is present (optional operation). More formally, removes an element e such that (o==null ? e==null : o.equals(e)), if this set contains such an element. Returns true if this set contained the element (or equivalently, if this set changed as a result of the call). (This set will not contain the element once the call returns.)
	 * Parameters:
		o - object to be removed from this set, if present
	 * Returns:
		true if this set contained the specified element
	*/
	@Override
	public boolean remove(Object item) {
		root = remove(root,(E)item);
		return removeReturn;		
	}
	
	private BinaryNode remove(BinaryNode r,E item){
		if (r == null) {
			removeReturn = false;
			
		}else if (compare(item, r.data)<0) {
			r.left=remove(r.left, item);
		}else if (compare(item, r.data)>0) {
			r.right=remove(r.right, item);
		}else{//encontrado
			removeReturn=true;
			
			if (r.left!=null && r.right!=null) {//hay elementos
				E min = minimo(r.right);
				r.data = min;
				r.right = borrarMinimo(r.right);
			}else if (r.left!=null) {
				r=r.left;
			}else{
				r=r.right;
			}
			size--;
		}
		return r;
		
	}	

	private E minimo(BinaryNode right) {
		BinaryNode aux=right;
		while (aux.left!=null) {
			aux=aux.left;
		}	
		return aux.data;
	}
	private BinaryNode borrarMinimo(BinaryNode root) {
		BinaryNode padre=root;
		BinaryNode hijo=padre.left;

		while (hijo.left!=null) {
			hijo=hijo.left;
			padre=padre.left;
		}
		if (hijo.right!=null) {
			padre.left=hijo.right;
		}
 		return hijo.right;
	}
	/** 
	 * E ceiling(E e)
	 * Returns the least element in this set greater than or equal to the given element, 
	 * or null if there is no such element.
	 */
	public E ceiling(E item) {
		//Implement
		return null;
	}
	

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		if (arg0==null) throw new NullPointerException();
		boolean changed = false;
		for (E e: arg0) {
			boolean res=add(e);
			if (!changed && res) changed = true;
		}
		return (changed);
	}

	@Override
	public void clear() {
		root = null; 
		size = 0;
	}

	
	

	@Override
	public boolean containsAll(Collection<?> arg0) {
		if (arg0 == null) throw new NullPointerException();
		boolean cont=true;
		Iterator<?> it=arg0.iterator();
		while (it.hasNext() && cont) {
			cont = contains(it.next());
		}
		return cont;
	}

	@Override
	public boolean isEmpty() {
		return (size==0);
	}



	@Override
	public boolean removeAll(Collection<?> arg0) {
		if (arg0==null) throw new NullPointerException();
		int originalSize=size();
		int newSize = originalSize;
		Object [] v = this.toArray();
		for (int i=0; i<v.length; i++) {
			if (arg0.contains(v[i])) {
				remove(v[i]);
				newSize--;
			}
		}
		return (originalSize!=newSize);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		if (arg0==null) throw new NullPointerException();
		int originalS = size();
		int newS = originalS;
		Object[] v = this.toArray();
		for (int i=0; i<v.length; i++) {
			if (!arg0.contains(v[i])) {
				remove(v[i]);
				newS--;
			}
		}
		return (originalS!=newS);
		
	}


	@Override
	public int size() {
		return this.size;
	}

	@Override
	public Object[] toArray() {
		
		Object[] v = new Object[size()];
		toArray(0,root,v);
		
		return v;
	}

	private int toArray (int pos, BinaryNode r, Object[] v) {
		if (r!=null) {
			if (r.left!=null) pos = toArray(pos,r.left, v);
			//System.out.println("toArray pos-> "+pos +" data--> "+r.data);
			v[pos] = r.data;
			pos++;
			if (r.right!=null) pos =toArray(pos,r.right,v);
		}
		return pos;
	}
	
	@Override
	public <T> T[] toArray(T[] arg0) {
		if (arg0 == null) throw new NullPointerException();
		int n=size();
		if (n > arg0.length) 
			arg0=(T[]) new Object[n];
		toArray(0,root,arg0);
		
		return arg0;
	}

	
	/**Returns an String with the data in the nodes
	 * in inorder
	 */
	 public String toString() {
		 return toString(root);
	 }
	 
	 private String toString(BinaryNode r) {
		 String s="";
		 if (r != null) {
			 String sl=toString(r.left);
			 String sd=toString(r.right);
			 if (sl.length() >0) 
				 s = sl + ", ";
			 s = s + r.data;
			 if (sd.length() > 0)
				 s = s + ", " + sd;
		 }
	     return s;
	 }
	
	 //Implementaci�n de Iteradores
	 private class EdTreeSetIterator implements Iterator<E>{
			/*
			 * Internal class that stores the related info of an iterator. This class does 
			 * not implement a fail safe mechanism. 
			 * 
			 * nextItem: The next item to be returned by next()
			 * lastItem: The last item read. Null if not next() or remove() has not been call
			 */
			private E nextItem;
			private E lastItem;
			private int index;
			
			 
			public EdTreeSetIterator() {
				if (root!=null) nextItem = root.data;
				lastItem=null;
				index=0;
			}
				

			public boolean hasNext() {
				
				Object v[] = toArray();
				if (index <v.length) return true;
				return false;
			}

			public E next() {
				
				Object v[] = toArray();
				if (!hasNext()) throw new NoSuchElementException();
				lastItem = nextItem;
				index++;
				if (index < v.length) nextItem = (E) v[index];
				return lastItem;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
	    }

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new EdTreeSetIterator();
	}

}
