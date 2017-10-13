package polinomio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Polinomio {

	// La lista de monomios
	private List<Monomio> datos = new LinkedList<Monomio>();

	/**
	 * Constructor por defecto. La lista de monomios está vacía
	 */
	public Polinomio() {
	};

	/**
	 * Constructor a partir de un vector. Toma los coeficientes de los monomios
	 * de los valores almacenados en el vector, y los exponentes son las
	 * posiciones dentro del vector. Si <code>v[i]</code> contiene
	 * <code>a</code> el monomio contruido será aX^i. <br>
	 * 
	 * Por ejemplo: <br>
	 * 
	 * v = [-1, 0, 2] -> 2X^2 -1X^0
	 * 
	 * @param v
	 */
	public Polinomio(double v[]) {
		// IMPLEMENTAR
		for (int i = 0; i < v.length; i++) {
			if (!Cero.esCero(v[i])) {//comprobar si el coeficiente no es cero
				Monomio m = new Monomio(v[i], i);//creo monomio
				datos.add(m);
			}
		}
	}

	/**
	 * Constructor copia
	 * 
	 * @param otro
	 * @throws <code>NullPointerException</code>
	 *             si el parámetro es nulo
	 */
	public Polinomio(Polinomio otro) {
		if (otro == null)
			throw new NullPointerException();

		for (Monomio item : otro.datos)
			datos.add(new Monomio(item));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		

		boolean primero = true;

		for (int i = 0; i <datos.size(); i++) {
			Monomio item = datos.get(i);

			if (item.coeficiente < 0) {
				str.append('-');
				if (!primero)
					str.append(' ');
			} else if (!primero)
				str.append("+ ");

			str.append(Math.abs(item.coeficiente));
			if (item.exponente > 0)
				str.append('X');
			if (item.exponente > 1)
				str.append("^" + item.exponente);

			if (i < datos.size()-1)
				str.append(' ');

			primero = false;
		}
		if (primero)
			str.append("0.0");

		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Polinomio other = (Polinomio) obj;

		if (this.datos.size() != other.datos.size())
			return false;

		Iterator<Monomio> iter1 = this.datos.iterator();
		Iterator<Monomio> iter2 = other.datos.iterator();

		while (iter1.hasNext())
			if (!(iter1.next().equals(iter2.next())))
				return false;

		return true;
	}

	/**
	 * Devuelve la lista de monomios
	 *
	 */
	public List<Monomio> monomios() {
		return datos;
	}

	/**
	 * Suma un polinomio sobre <code>this</code>, es decir, modificando el
	 * polinomio local. Debe permitir la auto autosuma, es decir,
	 * <code>polinomio.sumar(polinomio)</code> debe dar un resultado correcto.
	 *
	 * @param otro
	 * @return <code>this<\code>
	 * @throws <code>NullPointeExcepction</code> en caso de que el parámetro sea <code>null</code>.
	 */
	public void sumar(Polinomio otro){
		// IMPLEMENTAR
		ListIterator<Monomio> iter1= datos.listIterator();
		ListIterator<Monomio> iter2= otro.datos.listIterator();
 
		while (iter1.hasNext() && iter2.hasNext()) {
			Monomio mono1 = iter1.next();
			Monomio mono2 = iter2.next();
			
			if ( mono1.exponente == mono2.exponente) {
				if (!Cero.esCero(mono1.coeficiente+mono2.coeficiente)) {
					Monomio a = new Monomio(mono1.coeficiente+mono2.coeficiente,mono1.exponente);
					iter1.set(a);;
				}else{
					iter1.remove();
				}
				  
				
			}else if (mono1.exponente > mono2.exponente) {
				iter1.previous();
				iter1.add(new Monomio(mono2.coeficiente,mono2.exponente));
				
			}else if(mono1.exponente < mono2.exponente){
				iter2.previous();
			}	
		}
		 while(iter2.hasNext())
			 iter1.add(iter2.next());
	}
	

	/** Multiplica el polinomio <code>this</code> por un monomio. 
	 * @param mono
	 */
	public void multiplicarMonomio(Monomio mono) {
		// IMPLEMENTAR
		if (Cero.esCero(mono.coeficiente)) {
			datos.clear();
		}
		
		if (mono != null) {//si el monomio no es nulo
			for (Monomio actual : datos) {				 
				actual.coeficiente *= mono.coeficiente;
				actual.exponente += mono.exponente;
					
 			}
		}
	}
}
