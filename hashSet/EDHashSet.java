package hashSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * <p>Clase genérica que implementa la interface <code>Set<T></code> usando una tabla 
 * de dispersión con resolución de colisiones mediante direccionamiento abierto. 
 * 
 * <p>No admite elementos repetidos.
 * 
 * <p>Permite incluir elementos con valor <code>null</code>.
 * 
 * <p>Implementa iteradores sín ningún tipo control de las modificaciones concurrente.
 *
 * @param <T> Clase de los elementos contenidos en el conjunto.
 */
public class EDHashSet<T> implements Set<T> {
	private T[] table;				// Tabla de dispersión
	private boolean[] used;			// Elementos usados de la tabla
	private int size;				// Número elementos contenidos en el conjunto
	private int rehashThreshold;		// Umbral para realizar una redispersión.
	private int dirty;				// Cantidad de casillas de la tabla usadas
	private boolean containsNull;	// Indicador de que null está en el conjunto. 

	// Capacidades y umbrales por defecto
	private static int DEFAULT_CAPACITY = 10;
	private static int DEFAULT_THRESHOLD = 7;

	
	/** Calcula el código de dispersión ajusntado al tamaño de la tablas
	 * 
	 * <p> El valor devuelto estará entre <code>0</code> y <code>table.length</code>.
	 * 
	 * @param item 	Valor a dispersar.
	 * @return		Código de dispersión
	 */
	private int hash(T item) {
		return (item.hashCode() & Integer.MAX_VALUE) % table.length;
	}

	/**
	 * Realiza la redispersión de todos los elementos de table.
	 * 
	 * <p>Para ello doblará el tamaño de <code>table, used</code> y del umbral 
	 * de dispersión y resispersará todos los elementos contenidos en el conjnuto.
	 *  La condición para redispersión es <code>dirty >= rehashThreshold</code> 
	 */
	private void rehash() {
		// TODO Implementar 
	}

	/**
	 * Constructor por defecto del conjunto. 
	 * 
	 * <p> Construye un conjunto vacío. 
	 */
	public EDHashSet() {
		table = (T[]) new Object[DEFAULT_CAPACITY];
		used = new boolean[DEFAULT_CAPACITY];
		size = dirty = 0;
		rehashThreshold = DEFAULT_THRESHOLD;
		containsNull = false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public int dirty() {
		return dirty;
	}

	public T[] getTable() {
		return table;
	}

	public boolean[] getUsed() {
		return used;
	}

	public int getThrshold() {
		return rehashThreshold;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Implementar 
		int i = 0;
		if ( o == null) {
			return containsNull;
		}
		int pos = hash((T)o);
		
		while(i < table.length){
			 
		}
				
		return false;
	}

	@Override
	public boolean add(T e) {
		// TODO Implementar 
		int pos = hash(e);	
		
		if (dirty >= rehashThreshold) {//si la tabla esta llena
			rehash();//se hace el rehash y se reinsertan todos los elementos en la tabla.
		}
		
		while(table[pos]!=null && !contains(e)){//mientras no sea null y no lo contenga
			pos = (pos +1) % table.length;
		}
		if (!contains(e)) {//si no contiene el e lo añado
			table[pos] = e;
			used[pos] = true;
			dirty++;
			size++;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return containsNull;
		// TODO Implementar 
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object item : c)
			if (!contains(item))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		int oldS = size;
		for (T item : c)
			add(item);

		return size != oldS;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int oldS = size;

		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T d = iter.next();
			if (!c.contains(d))
				remove(d);
		}

		return size != oldS;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int oldS = size;

		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T d = iter.next();
			if (c.contains(d))
				remove(d);
		}

		return size != oldS;
	}

	@Override
	public void clear() {
		containsNull = false;
		size = 0;
		dirty = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
			used[i] = false;
		}
	}

	@Override
	public Object[] toArray() {
		Object v[] = new Object[size];
		int i = 0;
		for (T item : this)
			v[i++] = item;

		return v;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			return (T[]) toArray();

		Iterator<T> iter = (Iterator<T>) iterator();
		int i = 0;
		while (iter.hasNext())
			a[i++] = iter.next();

		return a;
	}

	public String toString() {
		StringBuilder str = new StringBuilder("[ ");
		if (containsNull)
			str.append("null, ");
		for (int row = 0; row < table.length; row++) {
			if (table[row] != null) {
				str.append("{" + row + ": ");
				str.append(table[row]);
				str.append("} ");
			}
		}

		str.append("] (size: " + size + ", capacity: " + table.length + ")");
		return str.toString();
	}

	// IMPLEMENTACION DE ITERADORES
	private class LocalIterator implements Iterator<T> {
		int next;
		int last;

		LocalIterator() {
			last = -2;
			if (containsNull)
				next = -1;
			else {
				next = 0;
				while (next < table.length && table[next] == null)
					next++;
			}
		}

		@Override
		public boolean hasNext() {
			return next != table.length;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();

			last = next;
			do {
				next++;
			} while (next < table.length && table[next] == null);
			return last == -1 ? null : table[last];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new LocalIterator();
	}

}
