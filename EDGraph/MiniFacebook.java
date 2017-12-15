package grafoFacebook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;

import javax.xml.soap.Node;
/*
 * @author Mihai Manea
 */
//import ejer1.EDListGraph.Node;

public class MiniFacebook {


	/** EDGraph<String, Object> leerGrafo (String nomfich)
	 * 
	 * @param nomfich Nombre del fichero de texto
	 * @return grafo implementado con listas de adyacencia a partir de la informaci�n del fichero
	 * 
	 * Si no puede abrir el fichero, devolver� null. Si el fichero est� vac�o, devolver� un grafo vac�o.
	 * 
	 */
	public static EDGraph<String, Object> leerGrafo (String nomfich) {
		Scanner inputStream = null;
		EDListGraph<String, Object> g = null;

		try		{
			g = new EDListGraph<>();
			inputStream = new Scanner(new FileInputStream(nomfich));//abrir fichero

			while (inputStream.hasNext()) {
				String nodo1 = inputStream.next();
				String nodo2 = inputStream.next();

				if (g.getNodeIndex(nodo1)<0 ) {//si los nodos no estan, los añado 
					g.insertNode(nodo1);		 			
				}
				if (g.getNodeIndex(nodo2)<0 ) {//si los nodos no estan, los añado 
					g.insertNode(nodo2);		 			
				} 

				EDEdge<Object> arco = new EDEdge<>(g.getNodeIndex(nodo1), g.getNodeIndex(nodo2));		 				 	 
				g.insertEdge(arco);		 		 		 		
			}

		}catch(FileNotFoundException e)		{
			System.out.println("Error opening file "+nomfich);
			return null;
		}				 
		return g;		  
	}
}
