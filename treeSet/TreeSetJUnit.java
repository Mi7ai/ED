package treeSet;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

public class TreeSetJUnit<E> {

	private static Random generator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		generator = new Random();
	}



	private EdTreeSet<Integer> createTreeSet() {
		return new EdTreeSet<Integer>();
	}
	
	

	@Test
	public final void testTreeSet() {
		System.out.println("\nValidando constructor por defecto...");

		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);

		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}

	
	@Test
	public final void testAdd() {
		System.out.println("\nValidando el metodo add()...");
		EdTreeSet<Integer> set = createTreeSet();
		
		System.out.println(" -> " + set);

		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
		
        TreeSet<Integer> checkSet= new TreeSet<Integer>(); //tree set for checking values
		
        int count=0;
		for (int i = 0; i< 20; i++) {
			int num = generator.nextInt(99);
			System.out.print("add(" + num + ")");
			boolean added = set.add(num);
			boolean added2 = checkSet.add(num);
			System.out.println(" -> " + set.toString());
			if (added2) {
				assertTrue(added);
				assertFalse(set.isEmpty());
				assertEquals(++count, set.size());
			}
			else {
				assertFalse(added);
				assertEquals(count,set.size());
			}
			Integer [] check = new Integer[checkSet.size()];
			Integer [] other = new Integer[set.size()];
			checkSet.toArray(check);
			set.toArray(other);
			assertEquals(checkSet.size(),set.size());
			for (int j=0; j<check.length; j++)
				assertEquals(check[j],other[j]);
			
		}

		int oldSize=set.size();
		for (Integer n : checkSet) {
			System.out.print("add(" + n + ")");
			boolean added = set.add(n);
			System.out.println(" -> " + set);
			assertFalse(added);
			assertFalse(set.isEmpty());
			assertEquals(oldSize, set.size());
			
		}
		
		set.clear();
		checkSet.clear();
		count=0;
		for (int i = 0; i< 20; i++) {
			
			System.out.print("add(" + i + ")");
			boolean added = set.add(i);
			boolean added2 = checkSet.add(i);
			System.out.println(" -> " + set.toString());
			
			assertTrue(added);
			assertFalse(set.isEmpty());
			assertEquals(++count, set.size());
			
			Integer [] check = new Integer[checkSet.size()];
			Integer [] other = new Integer[set.size()];
			checkSet.toArray(check);
			set.toArray(other);
			assertEquals(checkSet.size(),set.size());
			for (int j=0; j<check.length; j++)
				assertEquals(check[j],other[j]);
			
		}
		
		set.clear();
		checkSet.clear();
		count=0;
		for (int i = 19; i>0; i--) {
			
			System.out.print("add(" + i + ")");
			boolean added = set.add(i);
			boolean added2 = checkSet.add(i);
			System.out.println(" -> " + set.toString());
			
			assertTrue(added);
			assertFalse(set.isEmpty());
			assertEquals(++count, set.size());
			
			Integer [] check = new Integer[checkSet.size()];
			Integer [] other = new Integer[set.size()];
			checkSet.toArray(check);
			set.toArray(other);
			assertEquals(checkSet.size(),set.size());
			for (int j=0; j<check.length; j++)
				assertEquals(check[j],other[j]);
			
		}
		
	}

	@Test
	public final void testContains() {
		System.out.println("\nValidando el metodo contains()...");
		EdTreeSet<Integer> set = createTreeSet();
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		System.out.println(" -> " + set);
		
		int count = 0;
		for (int i = 0; i < 20; i += 2) {
			int num = generator.nextInt(99);
			System.out.print("add(" + num + ")");
			boolean added = set.add(num);
			boolean added2 = checkSet.add(num);
			if (added2) count++;
			System.out.println(" -> " + set);
			assertEquals(added,added2);
			assertFalse(set.isEmpty());
			assertEquals(count, set.size());
			
		}

		for (int i = 0; i < 100; i++) {
			int num = generator.nextInt(99);
			System.out.print("contains(" + num + ")");
			boolean actual = set.contains(num);
			boolean expected = checkSet.contains(num);
			System.out.println(" -> " + actual);
			assertEquals(expected, actual);
		}

	}

	@Test
	public final void testRemove() {
		System.out.println("\nValidando el metodo remove()...");
		EdTreeSet<Integer> set = createTreeSet();
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();
		System.out.println(" -> " + set);
		


		int count = 0;
		
		for (int i = 0; i < 20; i ++) {
			int num = generator.nextInt(99);
			System.out.print("add(" + num + ")");
			set.add(num);
			boolean added=checkSet.add(num);
			
			System.out.println(" -> " + set);
			if (added) {
				l.add(num);
				count++;
			}
		}

		for (int i = 0; i < l.size(); i=i+2) {
			Integer s = l.get(i);
			System.out.print("remove(" + s + ")");
			boolean actual = set.remove(s);
			boolean expected = checkSet.remove(s);
			if (expected)
				count--;
			System.out.println(" -> " + actual + ", set: " + set);
			assertEquals(expected, actual);
			assertEquals(count, set.size());
		}
		
		for (int i=0; i<100; i++) {
			int num = generator.nextInt(99);
			System.out.print("remove(" + num + ")");
			boolean actual = set.remove(num);
			boolean expected = checkSet.remove(num);
			if (expected)
				count--;
			System.out.println(" -> " + actual + ", " + set);
			assertEquals(expected, actual);
			assertEquals(count, set.size());
		}
		
	}

	@Test
	public final void testSize() {
		System.out.println("\nValidando el metodo size()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		assertEquals(0, set.size());
		
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();

		
		int count =0;
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
		
			System.out.print("add(" + num + ")");
			boolean added=set.add(num);
			if (added) {
				l.add(num);
				count++;
			}
			System.out.println(" -> " + set);
			assertEquals(count, set.size());
			
		}

		System.out.println("tama�o conjunto "+set.size()+" .. count .. "+count);
		for (int i = 0; i < l.size(); i++) {
			Integer s = l.get(i);
			System.out.print("remove(" + s + ")");
			set.remove(s);
			count--;
			System.out.println(" -> " + set);
			assertEquals(count, set.size());

		}
	}

	@Test
	public final void testIsEmpty() {
		System.out.println("\nValidando el metodo isEmpty()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		System.out.print("isEmpty() -> ");
		boolean empty = set.isEmpty();
		System.out.println(empty);
		assertTrue(empty);
		

		for (int i=0; i<20; i++) {
			System.out.print("add(" + i + ")");
			set.add(i);
			System.out.println(" -> " + set);
			System.out.print("isEmpty() -> ");
			empty = set.isEmpty();
			System.out.println(empty);
			assertFalse(empty);
			


			System.out.print("remove(" + i + ")");
			set.remove(i);
			System.out.println(" -> " + set);
			System.out.print("isEmpty() -> ");
			empty = set.isEmpty();
			System.out.println(empty);
			assertTrue(empty);
			

		}
	}

	@Test
	public final void testClear() {
		System.out.println("\nValidando el metodo clear()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		

		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			System.out.print("add(" + num + ")");
			set.add(num);
			System.out.println(" -> " + set);

			if ((i + 1) % 5 == 0) {
				System.out.println("clear()");
				set.clear();
				System.out.println(" -> " + set);
				assertEquals(0, set.size());
				assertTrue(set.isEmpty());
				

			} else {
				assertNotEquals(0, set.size());
				
			}
		}
	}


	@Test
	public final void testContainsAll() {
		System.out.println("\nValidando el metodo containsAll()...");

		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();
		EdTreeSet<Integer> set = createTreeSet();
		
		System.out.println(" -> " + set);
		


		System.out.print("containsAll(" + l + ")");
		boolean result = set.containsAll(l);
		System.out.println(" -> " + result);
		assertTrue(result);
		
		for (int i = 0; i < 7; i++) {
			int num = generator.nextInt(99);
			if (!checkSet.contains(num)) {
				set.add(num);
				checkSet.add(num);
				l.add(num);
			}
		}
		
		List<Integer> l2 = new ArrayList<Integer>();

		System.out.print("containsAll(" + l2 + ")");
		result = set.containsAll(l2);
		System.out.println(" -> " + result);
		assertTrue(result);

		System.out.println(" -> " + set);
		System.out.print("containsAll(" + l.subList(0, 6) + ")");
		result = set.containsAll(l.subList(0, 6));
		System.out.println(" -> " + result);
		assertTrue(result);
		
		System.out.println(" -> " + set);
		System.out.print("containsAll(" + l.subList(2, 4) + ")");
		result = set.containsAll(l.subList(0, 6));
		System.out.println(" -> " + result);
		assertTrue(result);

		for (int i=0; i<10; i++)  {
			int num = generator.nextInt(99);
			if (!checkSet.contains(num)) {
				checkSet.add(num);
				l.add(num);
			}
		}
		System.out.println(" -> " + set);
		System.out.print("containsAll(" + l.subList(7, l.size()) + ")");
		result = set.containsAll(l.subList(7, l.size()));
		System.out.println(" -> " + result);
		assertFalse(result);

		System.out.println(" -> " + set);
		System.out.print("containsAll(" + l + ")");
		result = set.containsAll(l);
		System.out.println(" -> " + result);
		assertFalse(result);
		

		l.clear();
		System.out.println(" -> " + set);
		System.out.print("containsAll(" + l + ")");
		result = set.containsAll(l);
		System.out.println(" -> " + result);
		assertTrue(result);
		
	}

	@Test
	public final void testAddAll() {
		System.out.println("\nValidando el metodo addAll()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();

		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			if (!checkSet.contains(num)) {
				checkSet.add(num);
				l.add(num);
			}
		
		}
		
        //addAll sobre una lista vac�a
		System.out.print("addAll(" + l + ")");
		boolean modified = set.addAll(l);
		//checkSet.addAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertTrue(modified);
		assertEquals(checkSet.size(),set.size());
			
		//addAll con los elementos que ya tiene el conjunto
		int oldSize = set.size();
		System.out.print("addAll(" + l + ")");
		modified = set.addAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		assertEquals(oldSize, set.size());
			
		//addAll con un conjunto que ya tiene elementos
		for (int i = 0; i < 10; i++) {
			int num = generator.nextInt(99);
			l.add(num);
		}
		
		int count= 0;
		for (int x: l) {
			if (!checkSet.contains(x)) {
				checkSet.add(x);
				count++;
			}
		}
		
		oldSize=set.size();
		System.out.print("addAll(" + l + ")");
		modified = set.addAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		if (count>0) assertTrue(modified);
		else assertFalse(modified);
		assertEquals(oldSize+count, set.size());
		
	}

	@Test
	public final void testRetainAll() {
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();

		System.out.println("testRetainAll ( "+l);
		boolean modified = set.retainAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			if (!checkSet.contains(num)) {
				checkSet.add(num);
				l.add(num);
			}
		}
		
		System.out.println("testRetainAll ( "+l);
		modified = set.retainAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);

		
		set.addAll(l);
		System.out.print(" addAll(" + l + ")");
		System.out.println(" -> " + set);
		System.out.print("retainAll(" + l.subList(0, 7) + ")");
		modified = set.retainAll(l.subList(0, 7));
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertTrue(modified);
			


		int oldSize = 7;
		System.out.print("retainAll(" + l.subList(0, 7) + ")");
		modified = set.retainAll(l.subList(0, 7));
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		assertEquals(oldSize, set.size());
		
		System.out.print("retainAll(" + l.subList(8, l.size()) + ")");
		modified = set.retainAll(l.subList(8, l.size()));
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertTrue(modified);
		
	}

	@Test
	public final void testRemoveAll() {
		System.out.println("\nValidando el metodo removeAll()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();

		//RemoveAll from an empty set
		System.out.println("RemoveAll ( "+l);
		boolean modified = set.removeAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			if (!checkSet.contains(num)) {
				checkSet.add(num);
				l.add(num);
			}
		}
		
		System.out.println("RemoveAll ( "+l);
		modified = set.removeAll(l);
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		
		
		set.addAll(l);
		System.out.print(" addAll(" + l + ")");
		System.out.println(" -> " + set);
		System.out.print("removeAll(" + l.subList(0, 7) + ")");
		modified = set.removeAll(l.subList(0, 7));
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertTrue(modified);
			

		int oldSize = l.size() - 7;
		System.out.print("removeAll(" + l.subList(0, 7) + ")");
		modified = set.removeAll(l.subList(0, 7));
		System.out.println(" -> " + modified);
		System.out.println(" -> " + set);
		assertFalse(modified);
		assertEquals(oldSize, set.size());
			
		
	}

	@Test
	public final void testToArray() {
		System.out.println("\nValidando el metodo toArray()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();

		//toArray for an empty set
		Object[] v =  set.toArray();
		assertEquals(0,v.length);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
				checkSet.add(num);
				set.add(num);
		}
		
		System.out.println("toString ( "+set);
		v =  set.toArray();
		Object [] vaux = checkSet.toArray();
		assertEquals(vaux.length, v.length);
		
		for (int i = 0; i < v.length; i++) {
			assertEquals(vaux[i],v[i]);
		}
	}
	
	@Test
	public final void testCeiling() {
		System.out.println("\nValidando el metodo ceiling(e)...");
		EdTreeSet<Integer> set = createTreeSet();
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();
		
		System.out.println(" -> " + set);
		System.out.print("   ceiling(5) -> ");
		Integer n = set.ceiling(5);
		assertEquals(n,null);
		if (n==null) System.out.println("null");
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			checkSet.add(num);
			set.add(num);
		}
		
		System.out.println(" -> " + set);
		for (int i=0; i<10; i++) {
			int num = generator.nextInt(99);
			System.out.println("   ceiling("+ num +") -> "+set.ceiling(num));
			assertEquals(checkSet.ceiling(num),set.ceiling(num));
		}
		
		assertEquals(checkSet.ceiling(101), set.ceiling(101));
		
	}
	
	//@Test(expected = NoSuchElementException.class)
		public final void testFirstExcepcion() throws NoSuchElementException {
			EdTreeSet<Integer> set = createTreeSet();
			System.out.println(" -> " + set);
			Integer n = set.first();
		}

	
	@Test
	public final void testFirst() {
		System.out.println("\nValidando el metodo first()...");
		EdTreeSet<Integer> set = createTreeSet();
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();
		
		System.out.println(" -> " + set);
		//Integer n = set.first();
		//System.out.println("  first()--> "+n);
		//assertEquals(n,null);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			checkSet.add(num);
			set.add(num);
		}
		System.out.println(" -> " + set);
		Integer n = set.first();
		System.out.println("  first()--> "+n);
		assertEquals(checkSet.first(), n);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			checkSet.remove(num);
			set.remove(num);
			System.out.println(" -> " + set);
			n = set.first();
			System.out.println("  first()--> "+n);
			assertEquals(checkSet.first(), n);
		}
		
		set.add(0);
		checkSet.add(0);
		System.out.println(" -> " + set);
		n = set.first();
		System.out.println("  first()--> "+n);
		assertEquals(checkSet.first(), n);
	}
	

	//@Test(expected = NoSuchElementException.class)
	public final void testLastExcepcion() throws NoSuchElementException {
				EdTreeSet<Integer> set = createTreeSet();
				System.out.println(" -> " + set);
				Integer n = set.last();
	}
	
	@Test
	public final void testLast() {
		System.out.println("\nValidando el metodo last()...");
		EdTreeSet<Integer> set = createTreeSet();
		TreeSet<Integer> checkSet = new TreeSet<Integer>();
		List<Integer> l = new ArrayList<Integer>();
		
		System.out.println(" -> " + set);
		//Integer n = set.last();
		//System.out.println("  last()--> "+n);
		//assertEquals(n,null);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			checkSet.add(num);
			set.add(num);
		}
		System.out.println(" -> " + set);
		Integer n = set.last();
		System.out.println("  last()--> "+n);
		assertEquals(checkSet.last(), n);
		
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			checkSet.remove(num);
			set.remove(num);
			System.out.println(" -> " + set);
			n = set.last();
			System.out.println("  last()--> "+n);
			assertEquals(checkSet.last(), n);
		}
		
		checkSet.add(110);
		set.add(110);
		n=set.last();
		System.out.println(" -> " + set);
		System.out.println("  last()--> "+n);
		assertEquals(checkSet.last(), n);
	}
	
		
	@Test
	public final void testIterator() {
		System.out.println("\nValidando el metodo iterator()...");

		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		

		System.out.print("iterator()");
		Iterator<Integer> iter = set.iterator();
		System.out.println(" -> iterador contruido con exito");
	}

	@Test
	public final void testIteratorHasNext() {
		System.out.println("\nValidando el metodo Iterator.hasNext()...");

		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);
		System.out.println("iterator()");

		// iterador de un conjunto vacio
		Iterator<Integer> iter = set.iterator();
		System.out.print("hasNext()");
		boolean result = iter.hasNext();
		System.out.println(" -> " + result);
		assertFalse(result);

		// Iterador con un sólo elemento
		set.add(5);
		System.out.println(" -> " + set);
		System.out.println("iterator()");
		iter = set.iterator();
		System.out.print("hasNext()");
		result = iter.hasNext();
		System.out.println(" -> " + result);
		assertTrue(result);

		// Iterador con varios elementos
		List<Integer> l=new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			//set.add(num);
			l.add(num);
		}
		set.clear();
		for (Integer s : l)
			set.add(s);

		System.out.println(" -> " + set);
		iter = set.iterator();
		int tam = set.size();
		for (int i = 0; i < tam; i++) {
			System.out.print("hasNext()");
			result = iter.hasNext();
			System.out.println(" -> " + result);
			assertTrue(result);
			System.out.print("next()");
			System.out.println(" -> " + iter.next());
		}

		System.out.print("hasNext()");
		result = iter.hasNext();
		System.out.println(" -> " + result);
		assertFalse(result);
	}

	@Test
	public final void testIteratorNext() {
		System.out.println("\nValidando el metodo Iterator.next()...");
		EdTreeSet<Integer> set = createTreeSet();
		System.out.println(" -> " + set);

		// Iterador con varios elementos
		TreeSet<Integer> saux = new TreeSet<Integer>();
		for (int i = 0; i < 20; i++) {
			int num = generator.nextInt(99);
			//set.add(num);
			saux.add(num);
		}

		for (Integer s : saux)
			set.add(s);

		System.out.println(" -> " + set);
		Iterator<Integer> iter = set.iterator();
		System.out.println("iterator()");

		int tam = set.size();
		System.out.println("tam -->"+tam);
		for (int i = 0; i < tam; i++) {
			System.out.println("next()");
			Integer str = iter.next();
			System.out.println(" -> " + str);

			if (!saux.contains(str))
				System.out.println("Elemento incorrecto");

			assertTrue(saux.contains(str));
			saux.remove(str);

		}

		System.out.print("hasNext()");
		boolean result = iter.hasNext();
		System.out.println(" -> " + result);
		assertFalse(result);
	}
	
	@SuppressWarnings("rawtypes")
	static class Tobjeto implements Comparable{
		String  clave;
		String dato;
		
		public Tobjeto(String c, String d){
			this.clave= c;
			this.dato = d;
		}
		
		
		public String toString() {
			String sal = "("+this.clave+", "+this.dato+")";
			return sal;
		}


		@Override
		public int compareTo(Object o) {
			Tobjeto ob= (Tobjeto) o;
			return this.clave.compareTo((ob.clave));
		}

	}
	
	static class comparadorTobjeto implements Comparator<Tobjeto> {
		public int compare(Tobjeto ob1, Tobjeto ob2) {
			return ob1.clave.compareTo(ob2.clave);
		}
	}
	
	private static List<Tobjeto> generarObjetos() {
		List<Tobjeto> lista = new ArrayList<Tobjeto>();
		int num1 = 97;
		int num2 = 122;

		//establezco una secuencia de diez n�meros aleatorios
		//podemos establecer la secuencia que queramos, siempre y cuando recordemos que el alfabeto en acssi tiene 26 letras, 
		//>a� que ese es el m�ximo de la secuencia
		for (int i=0; i<10; i++){
			String cl=new String();
			String v = new String();
			for (int j=0; j<5; j++) {
				int numAleatorio = (int)Math.floor(Math.random()*(num2 -num1)+num1);
				//para transformar los n�meros en letras seg�n ACSII
				char c = (char) numAleatorio;
				cl+=c;
				v=c+v;
			}
	
			//Tobjeto ob= new Tobjeto(cl, v);
			lista.add(new Tobjeto(cl, v));
		}
		return lista;
	}
	
	@Test
	public final void testCompare() {
		System.out.println("Validando el uso de compare(..,..)");
		EdTreeSet set = new EdTreeSet(new comparadorTobjeto());
		TreeSet<Tobjeto> ref = new TreeSet<Tobjeto>(new comparadorTobjeto());
		
		System.out.println(" -> " + set);
		List<Tobjeto> l = generarObjetos();
		for (Tobjeto ob:l) {
			set.add(ob);
			ref.add(ob);
			System.out.println("--> "+ set);
			assertEquals(set.first(), ref.first());
			Tobjeto [] check = new Tobjeto[ref.size()];
			Tobjeto [] other = new Tobjeto[set.size()];
			ref.toArray(check);
			set.toArray(other);
			assertEquals(ref.size(),set.size());
			for (int j=0; j<check.length; j++)
				assertEquals(check[j],other[j]);
			
		}
	}
	/*
	@Test
	public final void testIteratorRemove() {
		System.out.println("\nValidando el metodo Iterator.remove()...");
		TreeSet<String> set = createTreeSet();
		System.out.println(" -> " + set);

		// Iterador con varios elementos
		Set<String> saux = new HashSet<String>(generateStrings(10, "dispersion"));

		for (String s : saux)
			set.add(s);

		System.out.println(" -> " + set);
		Iterator<String> iter = set.iterator();
		System.out.println("iterator()");

		for (int i = 0; i < 11; i++) {
			System.out.print("next()");
			String str = iter.next();
			System.out.println(" -> " + str);
			saux.remove(str);

			if (i % 2 == 0) {
				System.out.println("remove()");
				iter.remove();
				System.out.println(" -> " + set);
				System.out.print("cointains(" + str + ")");
				boolean erased = set.contains(str);
				System.out.println("-> " + set.contains(str));
				assertFalse(erased);
			}
		}

		System.out.println("iterator()");
		iter = set.iterator();
		for (int i = 0; i < 5; i++) {
			System.out.print("next()");
			String str = iter.next();
			System.out.println(" -> " + str);
			saux.remove(str);

			System.out.println("remove()");
			iter.remove();
			System.out.println(" -> " + set);
			System.out.print("cointains(" + str + ")");
			boolean erased = set.contains(str);
			System.out.println("-> " + set.contains(str));
			assertFalse(erased);
		}

		System.out.print("hasNext()");
		boolean result = iter.hasNext();
		System.out.println(" -> " + result);
		assertFalse(result);
	}
	*/
}
