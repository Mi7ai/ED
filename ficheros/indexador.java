package ficheros;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class indexador {
	
	public static void printIndice(Map<String, List<Integer>> indice) {
		System.out.println("El �ndice contiene " + indice.size() + " palabras distintas");
		for (String palabra:indice.keySet()) 
			System.out.println(" " + palabra + ": " + indice.get(palabra));
	}	
			
	/**
	 * <p>Carga el diccionario desde un fichero. El fichero constar� de un listado de palabras v�lidas. 
	 * <p> Al leer las palbras les asignar� a cada una un �ndice, comenzando desde el cero, de forma consecutiva, de uno en uno. 
	 * 
	 * @param fichero Nombre del fichero que contiene el diccionario.
	 * 
	 * @return Un <code>HashMap</code> donde la clave es la palabra, y el valor el �ndice num�rico de esa palabra. 
	 * 
	 * @throws FileNotFoundException 
	 */
	public static HashMap<String, Integer> leerDiccionario(String fichero) throws FileNotFoundException{
 		HashMap<String, Integer> mapD = new HashMap<>();//crear diccionario vacio
		Scanner outputStream = null;		
		final String fileName = "castellano.dicc";
		
		try		{
			outputStream = new Scanner(new FileInputStream(fileName));//abrir fichero
			/*
			 * recorrer fichero y meter datos en el mapa
			 */
			 Integer index = 0;

			while (outputStream.hasNext()) {
				String word = outputStream.next().toLowerCase();
 				index++;
				
				if (!mapD.containsKey(word)) {
					mapD.put(word, index);
				}
			}		
		}catch(FileNotFoundException e)		{
			System.out.println("Error opening file "+fileName);
			System.exit(0);
		}
		
		System.out.println("File closed...!");
		outputStream.close();
		
		return mapD;
	}
	
	/** Toma un fichero conteniendo un texto e indexa todas las palabras que apercen en �l.
	 * <p> Para realizar est� operaci�n numera las posiciones de todas las palabras que aperecen en el texto, en orden, 
	 * comenzando desde 0 y cont�ndolas de una en una. En esta numeraci�n no se descarta ninguna palabra. A continuaci�n crea un �ndice 
	 * tal que para cada palabra que aparece en el fichero, crea una lista con las posiciones en las que aparece esa palabra.
	 * En este �ndice solo se incluir�n las palabras v�lidas, es decir las que aparecen en el diccionario.
	 * 
	 * @param fichero 	   Nombre del fichero que contiene el texto. 
	 * @param diccionario  Conjunto con todas las palabras del diccionario. 
	 * 
	 * @return Un <code>Map</code> en el que la clave ser� la palabra y el valor, una lista con las posiciones en las 
	 * 		  que aparece la palabra en el fichero de texto. En estas listas las posicioens est�n ordenadas de menor a mayor.
	 * 
	 * @throws FileNotFoundException Si alguno de los ficheros no podido ser abierto.
	 */
	public static Map<String, List<Integer>> indexar(String fichero, Set<String> diccionario) throws FileNotFoundException {
		//TODO: Implmentar para la pr�ctica
 		HashMap<String, List<Integer>> mapIndexes = new HashMap<>();//crear diccionario vacio
		Scanner inputStream = streamRead(fichero);//devuelve el stream del fichero
		int index = 0;
		
		while (inputStream.hasNext()) {
			String word = inputStream.next().toLowerCase();
			if (diccionario.contains(word)) {//la calve esta en el diccionario
				//comprobar si el map tiene la clave
				if (mapIndexes.get(word) == null) {					
					mapIndexes.put(word, new LinkedList<Integer>());
				} 
				//anadir indice a la lista
				List<Integer> indexWord = mapIndexes.get(word);// contiene todos los indices de la palabra
				indexWord.add(index);
			}
			index++;
		}
 			
		return mapIndexes;
	}
	
	/** Escribe el indice en un fichero en formato binario.
	 * 
	 * <p> El fichero binario consistir� en una secuancia de <code>Intger</code> conel siguiente formato:
	 * <ol> 
	 * <li> El primer <code>Intger</code> del fichero ser� el numero de palabras en el �ndice.  
	 * <li> Por cada palabra del �ndice se guardar�n dos <codeIntger</code>: el �ndice de la palabra y el 
	 * n�mero de apariciones de esa palabra en el fichero de texto (longitud de la lista de posiciones). 
	 * Los pares de enteros se ordenar�n por orden del �ndice de las palabras.
	 * <li> Por cada palabra y en el mismo orden que en el bloque anterior, un <code>Integer</code>por cada 
	 * posici�n de esa palabra en el fichero de texto.  Las posiciones se guardar�n en orden 
	 * de menor a mayor. 
	 * </ol>
	 * 
	 * @param fichero 	Nombre del fichero binario que se crear�
	 * @param indice		Un <code>Map</code> en el que la clave ser� la palabra y el valor, una lista con las posiciones en las 
	 * 		  que aparece la palabra.
	 * @param diccionario Un <code>HashMap</code> donde la clave es la palabra, y el valor el �ndice num�rico de esa palabra. 
	 * 
	 * @return El n�mero de bytes escritos en el fichero.
	 * 
	 * @throws IOException Si se produce cualquier fallo de apertura, escritura. 
	 */
	public static long escribirIndice(String fichero,  Map<String,List<Integer>> indice, Map<String, Integer> diccionario) throws IOException {
//		Scanner inputStream = streamRead(fichero);//devuelve el stream del fichero
		RandomAccessFile outputStream = null;//devuelve el stream del fichero
		long tam;
		 
		try {
			outputStream = new RandomAccessFile( fichero,"rw");//stream de escritura en el fichero

			//Bloque 1: numero de palabras en el indice 						 					
			outputStream.writeInt(indice.size());//escribe el indice de cada palabra
				

			//Bloque 2: el numero de la palabra del diccionario y el numero de posiciones que aparecen en la lista del indice de 
			//la palabra en orden alfabetico
			for (Entry<String, List<Integer>> entry : indice.entrySet()) {
				outputStream.writeInt(diccionario.get(entry.getKey()));//escribe el numero de la palabra del diccionario 
				List<Integer> listaPosiciones = entry.getValue();
				outputStream.writeInt(listaPosiciones.size());//escribe cuantas veces esa palabra se ha encontrado en un fichero
			}	
			//Bloque 3: guarda el contenido de las lista del map indice
			for (Entry<String, List<Integer>> entry : indice.entrySet()) {
				for (Integer posicionPalabra: entry.getValue() ) {
					outputStream.writeInt(posicionPalabra);					
				}				 
			}

		} catch (Exception e) {
			System.out.println("Exception in escribirIndice...");
			e.printStackTrace();
		}finally{
			tam = outputStream.getFilePointer();
			outputStream.close();
		}
		 
		return tam;
	}
		
	/** Busca los datos de una plabra en el fichero binario.
	 *
	 * Busca una palabra en un fichero binario con la estructura indicada en el m�todo <code>escribirIndice</code>. Usa 
	 * el diccionario para averiguar el indice de esa palabra. Descarta las palabras que no se encuentran en el diccionario.
	 * 
	 * @param fichero 	Nombre del fichero bianrio
	 * @param palabra	Palabra a buscar en el fichero binario.
	 * @param diccionario  Un <code>HashMap</code> donde la clave es la palabra, y el valor el �ndice num�rico de esa palabra.
	 * 
	 * @return Una <code>List</code> con las posiciones de la palabra, o <code>null</code> si no ha se encuentra en el �ndice.
	 * 
	 * @throws IOException Si se produce alg�n error en la apertura/lectura del fichero binario. 
	 */
	public static List<Integer> buscaPalabra(String fichero, String palabra, Map<String,Integer> diccionario) throws IOException {
		//TODO: de una palabra recupera la info. averiguar el entero del diccionario,abrira el fichero binario en el bloque 2
		
		RandomAccessFile outputStream=null;
		try {
			outputStream = new RandomAccessFile( fichero,"rw");
			
			for (Entry<String, Integer> entry : diccionario.entrySet()) {
				if (entry.getKey() != null) {//comprueba si esta en el diccionario
					int indicePalabraDiccionario = entry.getValue();//devuelve en indice de la palabra del diccionario que se tiene que buscar en el fichero



					//acceder al fichero binario
					int indicePalabraFichero = outputStream.readInt();//devuelve el indice del total de palabras del bloque 2
					int i=0;
					while(indicePalabraFichero <= (indicePalabraFichero*4)+4){
						int indiceBloque = outputStream.readInt();
						int vecesBloque = outputStream.readInt();
						i+=vecesBloque;//desplazamiento en el 3 bloque
						if (indiceBloque == indicePalabraDiccionario) {//esta en el fichero binario

							List<Integer> posicionesPalabra = new LinkedList<>();
							long pos = (i*4)+indicePalabraFichero*4;
							outputStream.seek(pos+4);//pongo el puntero en la lista de posiciones de la palabra
							int j=0;
							while(j<= i){
								posicionesPalabra.add(outputStream.readInt());
							}
							j=0;
							return posicionesPalabra;
							
						}
						i++;
						indicePalabraFichero = outputStream.readInt();//lee el siguiente indice
					}
					return null;
				}
			}
		} catch (IOException e) {
			System.out.println("Exception in buscarPalabra..."+e.getMessage());
		}
		
		return null;
	}
	
	public static Scanner streamRead(String fileName){
		Scanner outputStream = null;
		try		{
			outputStream = new Scanner(new FileInputStream(fileName));//abrir fichero
		 	
		}catch(FileNotFoundException e)		{
			System.out.println("Error opening file "+fileName);
			System.exit(0);
		}		
 		return outputStream;
	}
	
	static public String FICHERO_DICCIONARIO = "castellano.dicc";
	
	
	/** Programa principal.
	 * 
	 * Permite probar los m�todos uno a uno, sobre un fichero de texto concreto
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException {
		System.out.println("Leyendo diccionario...");
		HashMap<String, Integer> diccionario = leerDiccionario(FICHERO_DICCIONARIO);	
			
		System.out.println("Diccionario leido");
		System.out.println(" * Contiene " + diccionario.size() + " palabras distintas");	
		
		Scanner consola = new Scanner(System.in);
		System.out.print("Escribe el nombre de fichero a procesar (sin extensi�n): ");
		String base = consola.nextLine();
		
		Map<String, List<Integer>> indice = indexar(base + ".txt", diccionario.keySet());
		printIndice(indice);
		
		System.out.println("Escribiendo el indice en el fichero "+ base +".dat ...");
		long t = escribirIndice(base + ".dat", indice, diccionario); 
		System.out.println("Se han escrito " + t + " bytes");
		
		
		String siguiente = null;
		do {
			System.out.print("Escribe la palbra cuya informaci�n deseas recuperar  (\"null\" para terminar): ");
			siguiente = consola.nextLine().toLowerCase();
			if (siguiente.equals("null"))
				break;
			
			List<Integer> l = buscaPalabra (base + ".dat", siguiente, diccionario);
			if (l != null) 
				System.out.println(" -> " + siguiente + " (" + diccionario.get(siguiente) + "): " + l);
			else
				System.out.println(" XX " + siguiente + " no ha sido encontrada");
			
		} while (!siguiente.equals("null"));
		
		consola.close();
	}
	
	

}
