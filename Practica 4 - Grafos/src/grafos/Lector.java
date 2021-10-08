package grafos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import graphsDSESIUCLM.*;

/****************************************************************************************
*
* Class Name: Lector
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: Esta clase se encarga de leer el archivo atp_matches_2020.csv e ir 
* guardando cada dato en un objeto, ya sea el jugador o el partido junto con su elemento
* decorado. 
*
*****************************************************************************************
*/

public class Lector {

	  /*******************************************************************************************
	  *
	  * Method name: rellenar
	  *
	  * Description of the Method: Este método se encarga de leer el fichero e ir guardando cada
	  * dato en un objeto, ya sea el jugador o el partido junto con su elemento decorado. Mientras
	  * que haya una linea en nuestro archivo el while ejecutará, iremos trozeando la linea y 
	  * guardando cada dato en su correspondiente variable. No tendremos en cuenta los partidos
	  * jugados de la copa Davis por lo que al final cuando leamos Davis, esos partidos no los
	  * guardaremos en nuestros objetos. Se comprueba si existe algún vertice con el mismo nombre
	  * de alguno de los dos jugadores que leemos en cada iteración. En el caso de que exista, 
	  * seleccionamos ese vertice, y en el caso de que no, lo creamos. En el caso de que existan
	  * los 3 elementos decorados para crear la arista, esta se crea y aumenta el contador de la 
	  * arista.
	  * 
	  * Calling arguments: String ruta, utilizamos este argumento para pasar nuestro archivo con 
	  * los datos de los partidos y los jugadores.
	  *
	  * Return value: 
	  * 	- Graph<DecoratedElementTenis<Jugador>: elemento decorado de nuestro objeto jugador
	  * 	- DecoratedElementPartido<Partido>>: elemento decorado de nuestro objeto partido
	  *
	  * Required Files: apt_matches_2020.cvs, en este archivo se guardan los datos de los partidos.
	  *
	  * List of Checked Exceptions: 
	  * 			- IOException e: Es una excepción de Java que se produce cuando falla una 
	  * 			  operación de IO. 
	  * 			- FileNotFoundException e: Si no encontramos el fichero con los datos del programa 
	  * 			  lanzaremos la excepción e y mostrara por pantalla el mensaje "No existe este
	  * 			  fichero datos en el directorio indicado".
	  *
	  *********************************************************************************************/
	
	public Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  rellenar(String ruta) {
		Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>> grafo = new TreeMapGraph<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(ruta)); 
						
			String[] datos = null;// Para trozear la linea
			String linea; // Donde almacenamos
			String torneo;// nombre del torneo
			String numeroPartido;// numero de partido
			Jugador x; 
			Jugador y; 
			String nombre, nombre2;// nombre del jugador
			String mano, mano2;
			String pais, pais2;
			int rango = 0, rango2=0;
			int puntos = 0, puntos2=0;
			int lol=0;
			String id="00", id2="00";

			        while((linea = reader.readLine()) != null){
			           if(lol!=0) {
			        	String[] array = linea.split(", ");
			            datos=linea.split(";");						
						torneo = datos[0];	
						numeroPartido = datos[1];	
						nombre = datos[2];
						mano = datos[3];
						pais = datos[4];
						nombre2 = datos[5];
						mano2 = datos[6];
						pais2 = datos[7];
						try {
						rango=Integer.parseInt(datos[8]);
						puntos=Integer.parseInt(datos[9]);
						}catch(Exception e) {
							rango=9999;
							puntos=0;
						}
						
						try {
						rango2=Integer.parseInt(datos[10]);
						puntos2=Integer.parseInt(datos[11]);
						}catch(Exception e) {
							rango2=9999;
							puntos2=0;
						}
						
						String[] parts = torneo.split(" ");
						if(!parts[0].equals("Davis")) {
							int numeroPartido1=Integer.parseInt(datos[1]);
						x = new Jugador(nombre, mano, pais, rango, puntos);
						y = new Jugador(nombre2, mano2, pais2, rango2, puntos2);
						Partido partido = new Partido(torneo,numeroPartido1);
						Iterator<Vertex<DecoratedElementTenis<Jugador>>> comparador=grafo.getVertices();
						DecoratedElementPartido<Partido> arista = new DecoratedElementPartido<Partido>(id, partido);
						DecoratedElementTenis<Jugador> vertice_origen=null;
						Vertex<DecoratedElementTenis<Jugador>> vertice;
						DecoratedElementTenis<Jugador> vertice_destino=null;
						while(comparador.hasNext()) {
							vertice=comparador.next();
							if(vertice.getElement().getElement().getNombre().equals(nombre)) {
								if(vertice.getElement().getElement().getRango()==9999) {
									vertice.getElement().getElement().setRango(rango);
									vertice.getElement().getElement().setPuntos(puntos);
									vertice_origen = new DecoratedElementTenis<Jugador>(vertice.getElement().getID(), x);
								}else {
								vertice_origen = new DecoratedElementTenis<Jugador>(vertice.getElement().getID(), x);
								}
							}
							if(vertice.getElement().getElement().getNombre().equals(nombre2)) {
								if(vertice.getElement().getElement().getRango()==9999) {
									vertice.getElement().getElement().setRango(rango2);
									vertice.getElement().getElement().setPuntos(puntos2);
									vertice_destino = new DecoratedElementTenis<Jugador>(vertice.getElement().getID(), y);
									
								}else {
								vertice_destino = new DecoratedElementTenis<Jugador>(vertice.getElement().getID(), y);
								}
							}
						}
						
						if(vertice_origen==null) {
						vertice_origen = new DecoratedElementTenis<Jugador>(id2, x);
						id2= id2 +"1";}
						
						if(vertice_destino==null) {
							vertice_destino = new DecoratedElementTenis<Jugador>(id2, y);
							id2= id2 +"1";
						}
						
						if (vertice_origen != null && vertice_destino != null && partido != null) {
							grafo.insertEdge(vertice_origen, vertice_destino, arista);
							
							id = id + "1";
						}
						}
						}
						lol++;
				}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("No existe este fichero datos en el directorio indicado");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return grafo;
	}



	
}