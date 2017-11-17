package ficheros;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class visorBinario {

	/** Muestra por pantalla el contenido de un fichero binario.
	 * 
	 * Muestra todos los enteros en el fichero binario que se indique.
	 */
	public static void main(String[] args) {
		RandomAccessFile input = null;
		Scanner consola = new Scanner(System.in);
		
		System.out.print("Escribe el nombre de fichero a procesar (sin extensi�n): ");
		String nombre = consola.nextLine();
		String fichero = nombre + ".dat";
		
		try {
			input = new RandomAccessFile(fichero, "r");
		} catch (IOException e) {
				System.out.println("\n ERROR: " + e.getMessage());
		}
		
		try {
			int linea = 0;
			while(input.getFilePointer() < input.length()) {
				if (linea == 0)
					System.out.printf("%4d: ", input.getFilePointer());
				int dato = input.readInt();
				System.out.printf(" %6d", dato);
				linea++;
				if (linea == 8) {
					System.out.println("");
					linea = 0;
				}
			}
			System.out.println("\nSe han leido " + input.length() + " bytes");
			input.close();
		} catch(IOException e) {
			System.out.println("\n ERROR: " + e.getMessage());
		}
		consola.close();
	}
}
