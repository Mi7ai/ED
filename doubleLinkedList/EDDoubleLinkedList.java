package doubleLinkedList;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class EDDoubleLinkedList<T> implements List<T> {
	/** Implementation of the circular Double linked List
	 *
	 * @param <T> Base Type 
	 */

	// Node declaration
	protected class Node {
		public T data;
		public Node next;
		public Node prev;

		public Node(T element) {
			data = element;
			next = null;
			prev = null;
		}

		Node(T element, Node prevRef, Node nextRef) {
			data = element;
			next = nextRef;
			prev = prevRef;
		}
	}

	// private data
	protected Node head = null;
	protected int size = 0;

	protected Node indexNode(int index) {
		// Search a node by index. Does not check index range
		Node ref = head;

		if (head != null) {
			int i; 
			if (index < (size/2))
				for (i=0; i < index; i++)
					ref = ref.next;
			else
				for (i=size; i > index; i--)
					ref = ref.prev;
		}
		return ref;
	}


	public EDDoubleLinkedList() {
		head = null;
		size = 0;
	}

	//Constructor copía
	public EDDoubleLinkedList(List<T> otherList) {
		// IMPLEMENTAR

		head = new Node(otherList.get(0));

		this.size = otherList.size();

	}

	public boolean add(T element) {
		// IMPLEMENTAR
		boolean retValue = false;

		Node first = head.next;

		Node last = first.prev;
		Node nuevo = new Node(element);
		if (size() == 0) {
			head.next = nuevo;
		}
		if(first != null){
			nuevo.prev = last;
			nuevo.next = last.next;
			last.next = nuevo;

			first.prev = nuevo;
			if (size == 1) {
				first.next = nuevo;
			}
			retValue = true;
			size++;
		}
		return retValue;
	}

	public void add(int index, T element) {
		// IMPLEMENTAR
		Node nuevo = new Node(element);
		Node first = head.next;
		Node last = first.prev;



		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {//al principio
			head.next = nuevo;
			first.prev = nuevo;
			nuevo.next = first;
			nuevo.prev = last;
			last.next = nuevo;
		}else if (index == size()-1) {//ultimo
			nuevo.prev = last;
			nuevo.next = last.next;

			first.next = nuevo;
			last.next = nuevo;
		}else{//al medio
			for (int i = 0; i < index; i++) {
				first = first.next;//llego al siguiente del nuevo
			}
			nuevo.next = first;
			nuevo.prev = first.prev;
			first.prev.next = nuevo;
			first.prev = nuevo;
		}

	}

	public void clear() {
		head = null;
		size = 0;
	}

	public boolean contains(Object element) {
		return indexOf(element) != -1;
	}

	public T get(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException(Integer.toString(index));

		Node ref = indexNode(index);

		return ref.data;
	}

	public int indexOf(Object element) {
		// IMPLEMENTAR
		int i = 0;
		if (size() == 0) {
			return -1;
		}
		Node first = head.next;

		while (first != null) {
			if (!first.equals(element)) {
				i++;
			}else{
				return i;
			}
			first = first.next;
		}

		return -1;
	}

	public boolean isEmpty() {
		// IMPLEMENTAR
		if (head.next != null) {
			return false;
		}
		return true;

	}

	public boolean remove(Object element) {
		// IMPLEMENTAR
		Node first = head.next;
		Node last = head.prev;

		if (size() == 0) {
			return false;
		}
		if (first.equals(element)) {//primero
			head.next = first.next;
			first.next.prev = last;
			last.next = first.next;	
		}else if (last.equals(element)) {//ultimo
			last.prev.next = first;
			first.prev = last.prev;
		}else{//al medio
			while (!first.equals(element)) {
				first = first.next;
			}
			first.prev.next = first.next;
			first.next.prev = first.prev;
			
			
		}
		
		return false;


	}

	public T remove(int index) {
		return null;
		// IMPLEMENTAR
	}

	public T set(int index, T element) {
		return element;
		// IMPLEMENTAR
	}

	public int size() {
		return size; 
	}

	public String toString() {

		String retVal = "[ ";

		Node current = head;

		if (current != null)
			do {
				retVal += current.data.toString();
				current = current.next;
				if (current != head)
					retVal += ", ";

			} while(current != head);

		retVal += " ]: " + size;

		return retVal;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T item: c)
			add(item);

		return (!c.isEmpty());
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		ListIterator<T> iter2 = listIterator(index);
		for (T item: c) {
			iter2.add(item);
		}

		return (!c.isEmpty());
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
		// IMPLEMENTAR
	}


	@Override
	public int lastIndexOf(Object o) {
		Node ref = head;

		if (head == null)
			return -1;

		ref = ref.prev;
		for (int i = size-1; i >= 0; i--)
			if (ref.data.equals(o))
				return i;
		return -1;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
		// IMPLEMENTAR

	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
		// IMPLEMENTAR
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || fromIndex >= size || toIndex <= 0 || toIndex >= size || toIndex < fromIndex)
			throw new IndexOutOfBoundsException();

		EDDoubleLinkedList<T> newList = new EDDoubleLinkedList<T>();

		Node ref = indexNode(fromIndex);
		for (int i = fromIndex; i < toIndex; i++) {
			newList.add(ref.data);
			ref=ref.next;
		}

		return newList;
	}

	@Override
	public Object[] toArray() {
		Object[] newV = new Object[size];

		Node ref = head;
		for (int i =0; i < size; i++, ref = ref.next)
			newV[i] = ref.data;

		return newV;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		T[] newV = (T []) new Object[size];

		Node ref = (Node) head;
		for (int i =0; i < size; i++, ref = ref.next)
			newV[i] = (T) ref.data;

		return newV;
	}

	// Implementation of the iterators interface
	private class EDDoubleLinkedListIterator implements ListIterator<T>{
		/*
		 * Internal class that stores the related info of an iterator. This class does 
		 * not implement a fail safe mechanism. 
		 * 
		 * nextItem: The next item to be returned by next()
		 * lastItem: The last item read. Null if not next() or remove() has not been call
		 */
		private Node nextItem = head;
		private Node lastItem = null;
		private int index = 0;

		public EDDoubleLinkedListIterator(int id) {
			if (id < 0 || id > size) 
				throw new IndexOutOfBoundsException(Integer.toString(id));

			lastItem = null;

			nextItem = head;
			for (int i=0; i < id; i++)
				nextItem = nextItem.next;
			index = id;
		}


		public void add(T element) {

			Node n = new Node(element);
			if (nextItem == null) {
				head = n;
				nextItem = n.next = n.prev = n;
			} else {
				n.next = nextItem;
				n.prev = nextItem.prev;
				nextItem.prev= n;
				n.prev.next = n;
			}

			size++;
			lastItem = null;
			index++;

		}

		public boolean hasNext() {
			return index < size;
		}

		public boolean hasPrevious() {
			return index > 0;
		}

		public T next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException();

			lastItem = nextItem;
			nextItem = nextItem.next;
			index++;
			return lastItem.data;
		}

		public int nextIndex() {
			return index;
		}

		public T previous() throws NoSuchElementException {
			if (!hasPrevious())
				throw new NoSuchElementException();

			lastItem = nextItem = nextItem.prev;	
			index--;
			return lastItem.data;
		}

		public int previousIndex() {
			return index-1;
		}
		public void remove() throws IllegalStateException{
			if (lastItem == null)
				throw new IllegalStateException();

			if (lastItem == nextItem) {
				nextItem = nextItem.next;
				index++;
			}

			lastItem.next.prev = lastItem.prev;
			lastItem.prev.next = lastItem.next;

			if (size == 1)
				nextItem = null;
			if (head == lastItem) 
				head = nextItem;

			lastItem = null;
			index--;
			size--;

		}

		public void set(T element) throws IllegalStateException {
			if (lastItem == null)
				throw new IllegalStateException();

			lastItem.data = element;
			lastItem = null;
		}

	}

	public ListIterator<T> listIterator() {
		return new EDDoubleLinkedListIterator(0);
	}

	public ListIterator<T> listIterator(int index) {
		return new EDDoubleLinkedListIterator(index);
	}

	public Iterator<T> iterator() {
		return (Iterator<T>) listIterator();
	}
}
