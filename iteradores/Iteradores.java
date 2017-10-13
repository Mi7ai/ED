package iteradores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

/**
 * @author morales
 * @author Mihai Manea
 *
 */
public class Iteradores {

	/**
	 * Toma dos conjuntos de enteros y redistribuye los elementos entre ellos para que en 
	 * en primero terminen todos los que tienen valor par, y en el segundo todos los qu tienen
	 * valor impar. 
	 * @param pares		Conjunto en el que terminarÃ¡n todos los elementos pares
	 * @param impares	Conjunto que el que terminarÃ¡n todos los elementos imapres.
	 */
	static public void partir(Set<Integer> pares, Set<Integer> impares) {
		// IMPLEMENTAR AQUÃ�
		Iterator<Integer> iter2 = impares.iterator();//crea iterador al principio del set
		 
		
		while (iter2.hasNext()) {//set impar
			int elem = iter2.next();//elemento 
			if (elem%2 == 0) {//es par				
				pares.add(elem);//
				iter2.remove();
			} 
		}
		
		Iterator<Integer> iter1 = pares.iterator();//crea iterador al principio del set

		while (iter1.hasNext()) {
			int elem = iter1.next();//elemento 
			if (elem%2 != 0) {//es par				
				impares.add(elem);//
				iter1.remove();
			}		
		}	
	}
	
	/**
	 * Intercambio los elementos de una lista de interos. De forma que los que ocupan la 
	 * posiciÃ³n i se intercambiÃ¡n pos los que ocupan i+1, siendo i un nÃºmero par. En el caso de 
	 * que la lista tenga talla impar el Ãºltimo elemento permanece inalterado.
	 * 
	 * <p> Toma como pÃ¡ramatero  un iterador que puede estar en cualquier posiciÃ³n de la lista. 
	 * 
	 * @param iter 	Un iterador de enteros que puede estar en cualquier posiciÃ³n de la lista
	 * @return 		El tamaÃ±o de la lista
	 */
	static public int intercambio(ListIterator<Integer> iter) {
		// IMPLEMENTAR AQUÃ�
		reiniciarIter(iter);
		System.out.println(iter.nextIndex());

		while(iter.hasNext()){

			int actual = iter.next();
			//iter.remove();

			if (iter.hasNext() ) {//si tiene siguiente

				iter.remove();
				iter.next();
				iter.add(actual);	 		    							
			}


		}

		return iter.nextIndex();
	}
	
	
	
	/**
	 * Traslada todos los elmentos que ocupan posiciÃ³n mÃºltiplo de tres en 
	 * una lista de enteros al final de Ã©sta. Los elementos trasladados mantienen el
	 * orden en el que estaban en la lista antes del traslado. El primer elementos de la list ocupa 
	 * la posisicÃ³n 1.
	 * 
	 * <p> Toma como pÃ¡ramatero  un iterador que puede estar en cualquier posiciÃ³n de la lista. 
	 * @param iter	Un iterador de enteros que puede estar en cualquier posiciÃ³n de la lista
	 * @return		El nÃºmero de elementos trasladados. 
	 */
	static public int trespatras(ListIterator<Integer> iter) {
		// IMPLEMENTAR AQUÃ�
		
		LinkedList<Integer> l = new LinkedList<>();
		int c = 0, pos = 0;
 		reiniciarIter(iter);//renicia posicion del iterador a 0
		
			
		 	
		while (iter.hasNext()) {
			int nr = iter.next();
			 pos++;
			System.out.println(nr+" "+pos+" "+esMultiplo(pos, 3));

			if (esMultiplo(pos, 3)) {
				l.add(nr);
				iter.remove();
			}								
		}	
		 

 		if (l.size() >0) {
			for (Integer m : l) {				 
				iter.add(m);
			} 
		}
		c = l.size();
		l.clear();
		
		return c;
	}
	
	public static boolean esMultiplo(int n1,int n2){
		if (n1%n2==0)
			return true;
		else
			return false;
	}
	public static void reiniciarIter(ListIterator<Integer> iter){
		if (iter.hasNext()) {//resetear el puto indice de los cojones y su puta madre que esta aqui para volverme loco...
			iter.next();
			while( iter.nextIndex() > 0) {
				iter.previous();
			}			
		}else if (iter.hasPrevious()) {//resetear el puto indice de los cojones y su puta madre que esta aqui para volverme loco...
			iter.previous();
			while( iter.previousIndex() >= 0) {
				iter.previous();
			}			
		}
	}
 }
