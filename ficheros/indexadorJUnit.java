package ficheros;


import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import ficheros.indexador;

public class indexadorJUnit {
	
	static public String diccionarioTest = "castellano.dicc";
	static public int diccionarioSizeTest = 80383;
	
	private static void  testCompararFicheros(String fichero, String referencia, long longitud) throws IOException {
		RandomAccessFile fich = null, ref = null;
		System.out.println(" Comprobando el fichero " + fichero);
		try {
			fich = new RandomAccessFile(fichero, "r");
			ref = new RandomAccessFile(referencia, "r");
		} catch(FileNotFoundException e) {
			System.err.println("  No se pudieron abrir los ficheros");
			assertTrue(false);
			return;
		}
		
		System.out.println("  Longitud del fichero " + fichero + ": " + fich.length());
		if (longitud != fich.length())
			System.out.println("indexar() no ha calculado bien el numero de bytes escritor. Ha devuelto :" + longitud);
		assertEquals(fich.length(), longitud);
		System.out.println("  Longitud de la referencia: " + ref.length());
		
		assertEquals(ref.length(), fich.length());
		
		for(int i= 0; i < ref.length(); i++) {
			byte f = fich.readByte();
			byte r = ref.readByte();
			
			if (f != r) 
				System.out.println("EL fichero es diferente en el byte " + i + " [" + r +"] <>[" + f + "]");
			assertEquals(r, f);
		}
		
		ref.close();
		fich.close();
	}	
		
	@Test
	public final void testCargarDiccionario() {
		System.out.println("\nCargando el diccionario...");

		HashMap<String, Integer> diccionario;
		try {
			diccionario = indexador.leerDiccionario(diccionarioTest);
		} catch (FileNotFoundException e) {
			System.out.println("El fichero del diccionario no se pudo abrir");
			assertTrue(false);
			return;
		}	
		
		System.out.println("  El diccionario contiene " + diccionario.size() + " palabras, y deberia contener " + diccionarioSizeTest);
		assertEquals(diccionarioSizeTest, diccionario.size());
	}

	
	//@Test(expected = FileNotFoundException.class)
	public final void testCargarDiccionarioExcepciones() throws FileNotFoundException {
		System.out.println("\nComprobando excepciones al cargar el diccionario...");

		HashMap<String, Integer> diccionario;
		
		diccionario = indexador.leerDiccionario("noexiste");
		
	}
	
	private static String[] vFicherosTest = { "corto", "algoritmo", "figuras", "adolescentes" };
	
	@Test
	public void testIndexador() throws IOException {
		System.out.println("\nProbando la indexacion");
		HashMap<String, Integer> diccionario;
		try {
			diccionario = indexador.leerDiccionario(diccionarioTest);
		} catch (FileNotFoundException e) {
			System.out.println("El fichero del diccionario no se pudo abrir");
			assertTrue(false);
			return;
		}	
		
		try { 
			for (String base: vFicherosTest) {
				Map<String, List<Integer>> indice = indexador.indexar(base + ".txt", diccionario.keySet());
				long t = indexador.escribirIndice(base + ".dat", indice, diccionario); 
				testCompararFicheros(base + ".dat", base + ".ref", t);
			}
		} catch (FileNotFoundException e) {
			assertTrue(false);
			throw e;
		}
		
	}
	
	private static void testBuscarPalabraFichero(String base, HashMap<String, Integer> diccionario) throws IOException {
		System.out.println("\nBuscando palabras en el fichero " + base + ".dat");
		Scanner input = null;
		try { 
			input = new Scanner(new FileInputStream(base + ".casos")); 
		} catch (FileNotFoundException e) {
			System.err.println("No se pudo abrir el fichero " + base + ".casos");
			throw e;
		}
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		while (input.hasNextLine()) {
			l.clear();
			String caso = input.next();
			if (input.hasNextInt()) {
				input.nextInt();
				while (input.hasNextInt()) 
					l.add(input.nextInt());
				}
			else
				input.nextLine();
			
			try {
				List<Integer> ret = indexador.buscaPalabra(base + ".dat", caso, diccionario);
				if (l.size() != 0) {
					System.out.println(" Palabra: " + caso + " deber�a ser " + l + " y  devuelve " + ret);
					assertEquals(l, ret);
				} else {
					System.out.println(" Palabra: " + caso + " deber�a ser \"null\"  y devuelve " + ret);
					assertNull(ret);
				}
				
			} catch (IOException e) {
				System.err.println("ERRROR: leyendo " + base + ".dat");
				assertTrue(false);
			}
		}
		
		input.close();
	}
	
	@Test
	public void testBuscarPalabra() throws IOException {
		System.out.println("\nProbando la busqueda de palabras");
		HashMap<String, Integer> diccionario;
		try {
			diccionario = indexador.leerDiccionario(diccionarioTest);
		} catch (FileNotFoundException e) {
			System.out.println("El fichero del diccionario no se pudo abrir");
			assertTrue(false);
			return;
		}	
		
		try {
			for (String base: vFicherosTest) {
				testBuscarPalabraFichero(base, diccionario);
			}
			
		} catch(IOException e) {
			assertTrue(false);
			throw e;
		}
	}
	
	
}
