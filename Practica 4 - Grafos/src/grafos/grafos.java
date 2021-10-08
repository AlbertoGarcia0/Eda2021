package grafos;
import graphsDSESIUCLM.*;


/****************************************************************************************
*
* Class Name: grafos
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: Esta clase se encarga de mostar el número de jugadores, calcular el 
* jugador más competitivo, calcular el jugador de menor ranking y recorrer el grafo mediante 
* BFS y DFS para calcular las secuencias de la ronda de inauguración y la ronda exprés. 
* Para todo esto tendremos un menú para elegir que calcular o mostar y métodos para la 
* impresión de los calculos.
*
*****************************************************************************************
*/
import java.util.concurrent.LinkedBlockingQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class grafos {
	  static Scanner leer = new Scanner(System.in);
	  
	  /*******************************************************************************************
	  *
	  * Method name: main
	  *
	  * Description of the Method: En este método nos encargaremos de crear el grafo con los 
	  * elementos decorados de jugador y partido. Después, rellenamos nuestro grafo con los datos
	  * del cvs y creamos un menu para poder mostrar varios casos, estos pueden ser mostar todos
	  * los jugadores, mostrar la ronda exprés...
	  *
	  * Calling arguments: String[] args, son para los argumentos de línea de comandos en Java.
	  *
	  * Return value: void, no estamos devolviendo ningún valor
	  *
	  * Required Files: apt_matches_2020.cvs, en este archivo se guardan los datos de los partidos.
	  *
	  * List of Checked Exceptions: 
	  * 			- IOException: Es una excepción de Java que se produce cuando falla una 
	  * 			  operación de IO. 
	  *
	  *********************************************************************************************/
	  
	  public static void main (String[] args) throws IOException{
	  String csvFile = "atp_matches_2020.csv";
		  Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  grafo = new TreeMapGraph<>();
		  Lector l1 = new Lector();
		  grafo = l1.rellenar(csvFile);		  
		  boolean seguir = true;

	            while (seguir) {
	                mostrarmenu();
	                limpiarGrafo(grafo);
	                switch (eligirOpcion()) {
	                case 1:
	                    System.out.println("EL NUMERO DE JUGADORES ES: " + grafo.getN() + "\n");
	                    break;
	                case 2:
	                    System.out.println("EL NUMERO DE RELACIONES TOTALES ENTRE JUGADORES ES: " + grafo.getM() + "\n");
	                    break;
	                case 3:
	    				masCompetitivos(grafo);
	                    break;
	                case 4:
	                    menorRanking(grafo);
	                    break;
	                case 5:
	                	BFS(grafo);
	                    break;
	                case 6:
	                	DFS(grafo);
	                    break;
	                case 7: 
	                    seguir = false;
	                    System.out.println("Juego, set y partido.");
	                }
	            }
		  }

	  /*******************************************************************************************
	  *
	  * Method name: limpiarGrafo
	  *
	  * Description of the Method: Este método se encarga de establecer a falso los vertices que han
	  * podido ser visitados, y a null los parientes de los vertices, al realizar alguna de las operaciones de nuestro menu
	  *
	  * Calling arguments: 
	  * 	- Graph<DecoratedElementTenis<Jugador>:  este argumento pasa el Jugador de nuestro grafo
	  * 	- DecoratedElementPartido<Partido>> grafo: este argumento pasa los datos de nuestro partido
	  *
	  * Return value: void, no estamos devolviendo ningún valor
	  *
	  *********************************************************************************************/
	  
		public static void limpiarGrafo(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  grafo) {
			Vertex<DecoratedElementTenis<Jugador>> p;
			Iterator<Vertex<DecoratedElementTenis<Jugador>>> it= grafo.getVertices();
			while(it.hasNext()) {
				p=it.next();
				p.getElement().setVisited(false);
				p.getElement().setParent(null);
			}
		}
	  
	  public static void mostrarmenu() {
          System.out.println("Escriba un número para elegir una opcion:");
          System.out.println("1.- Mostrar número de jugadores");
          System.out.println("2.- Mostrar número total de relaciones entre jugadores");
          System.out.println("3.- Mostrar el jugador más competitivo");
          System.out.println("4.- Mostrar el jugador de menor ranking");
          System.out.println("5.- Ronda de inauguración");
          System.out.println("6.- Ronda exprés");
          System.out.println("7.- Salir");
      }
	  
	  /********************************************************************************************
	  *
	  * Method name: elegirOpcion
	  *
	  * Description of the Method: Este método se encarga de ver que la opción elegida en el método
	  * main están entre los parámetros 1 y 7. Si no está se mostrar la excepción InputMismatchException.
	  *
	  * Return value: int opcion, se devuelve la opción elegida por teclado
	  *
	  * List of Checked Exceptions: 
	  * 	- InputMismatchException: Lanzado por el éscaner para indicar que la opcion introducida
	  * 	  esta fuera del rango esperado.
	  *
	  *********************************************************************************************/
	  
	  public static int eligirOpcion() throws InputMismatchException {
          int opcion = 0;
          
          do {
              System.out.println("Introduce la opcion entre 1 y 7");
              opcion = leer.nextInt();
              leer.nextLine();
          } while (opcion < 1 || opcion > 7);
          return opcion;
          
      }
	  
	  /*******************************************************************************************
	  *
	  * Method name: masCompetitivos
	  *
	  * Description of the Method: Con este método veremos cual es el jugador que ha jugado más 
	  * partidos con más jugaodres distintios. Para ello tendremos la variable aristas que es un 
	  * iterator con los datos del jugador, mientras que haya aristas haremos el while. Iremos sumando
	  * en la variable relaciones las adyacencias de cada jugador. Si se encuentra un jugador que ha 
	  * jugado más partidos con jugadores diferentes cambiaremos el valor de mayor y guardaremos
	  * todos los jugadores contra los que ha competido en una lista. Al final de todo llamamos al 
	  * método Mostrar_Lista para imprimir todos los jugadores que han competido con el jugador 
	  * más competitivo.
	  * 
	  * Calling arguments: 
	  * 	- Graph<DecoratedElementTenis<Jugador>:  este argumento pasa el Jugador de nuestro grafo
	  * 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
	  *
	  * Return value: void, no estamos devolviendo ningún valor.
	  *
	  ********************************************************************************************/
	  
		public static void masCompetitivos(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g) {		
			Iterator<Vertex<DecoratedElementTenis<Jugador>>> vertices = g.getVertices();
			Iterator<Edge<DecoratedElementPartido<Partido>>> aristas;
			Vertex<DecoratedElementTenis<Jugador>> v1;
			Edge<DecoratedElementPartido<Partido>> a1;
			List<Vertex<DecoratedElementTenis<Jugador>>> lista_v = new LinkedList<Vertex<DecoratedElementTenis<Jugador>>>();
			int relaciones = 0; // va sumando una unidad cada vez que un vertice tiene otro vertice adyacente
			int mayor = 0; // va guardando el vertice con mayor numero de adyacencias
			int contador=0,vector=0;
		
			while (vertices.hasNext()) {
				relaciones = 0; // las relaciones de cada vertice deben empezar siendo 0
				v1 = vertices.next(); // vertice en el que se cuentan adyacencias
				aristas = g.incidentEdges(v1);
				vector=0;
				String nom[]=new String[20];
				
				while (aristas.hasNext()) {
					a1 = aristas.next();
					if (g.opposite(v1, a1) != v1) {
						contador=0;
						for(int i=0; i<nom.length; i++) {
							if(!g.opposite(v1, a1).getID().equals(nom[i]) || nom[i].isEmpty()) {
								contador++;
								if(contador==nom.length) {
									nom[vector]=g.opposite(v1, a1).getID();
									relaciones++;
									vector++;
								}
							}
						}
					}
				}
				if (relaciones > mayor) {
					mayor = relaciones;
					lista_v.clear();
					lista_v.add(v1);
				} else if (relaciones == mayor) {
					lista_v.add(v1);
				}
			}
			Mostrar_Lista(lista_v, mayor);
		}
		
		/*****************************************************************************************
		*
		* Method name: menorRanking
		*
		* Description of the Method: Con este método lo que haremos será mostrar por pantalla el 
		* jugador de menor ranking y averiguaremos quien es con el if, si ve que hay un jugador de
		* menor ranking lo cambiará por el nuevo. 
		* 
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>:  este argumento pasa el Jugador de nuestro grafo
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido	
		*
		* Return value: void, no estamos devolviendo ningún valor
		*
		*******************************************************************************************/
		
		public static void menorRanking(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g) {		
			Iterator<Vertex<DecoratedElementTenis<Jugador>>> vertices = g.getVertices();
			Vertex<DecoratedElementTenis<Jugador>> v1;
			Vertex<DecoratedElementTenis<Jugador>> vMenor=null;
			int menor=0; // va guardando el vertice con mayor numero de adyacencias
		
			while (vertices.hasNext()) {
				v1 = vertices.next(); // vertice en el que se cuentan adyacencias
				if(menor< v1.getElement().getElement().getRango() && 9999!=v1.getElement().getElement().getRango()) {
					menor=v1.getElement().getElement().getRango();
					vMenor=v1;
				}
					
			}
			System.out.println(vMenor.getElement().getElement().toString()+"\n");
		}
	  
		/******************************************************************************************
		*
		* Method name: mostrar_Lista
		*
		* Description of the Method: Este método lo utilizamos para mostrar los jugadores contra los
		* que ha jugado el jugador más competitivo. Mientras que la lista no este vacía imprimirá el 
		* jugador y el tipo de relación.
		*
		* Calling arguments: 
		* 	- List<Vertex<DecoratedElementTenis<Jugador>>> lista_v
		* 	- int tipo_relacion
		*
		* Return value: void, no estamos devolviendo ningún valor
		*
		*******************************************************************************************/
		
		public static void Mostrar_Lista(List<Vertex<DecoratedElementTenis<Jugador>>> lista_v, int tipo_relacion) {
			int cont = 0;
			Vertex<DecoratedElementTenis<Jugador>> vertice;
			
			while (!lista_v.isEmpty()) {
				vertice = lista_v.remove(cont);
				System.out.println(vertice.getElement().toString() + "\t" + tipo_relacion + "\n");
				cont++;
			}
		}
		
		/******************************************************************************************
		*
		* Method name: BFS
		*
		* Description of the Method: Para la ronda de inauguración utilizamos un BFS  para conformar 
		* el conjunto de jugadores que formará el equipo. Con el BFS encontramos la ruta con el 
		* número mínimo de bordes entre el origen y el destino, siendo estos los capitanes de los 
		* equipos. Si se dan las condiciones lo pasaremos al método imprimirPila. En caso de que no
		*  se pueda se mostrara por teclado el mensaje "Imposible establecer relación".
		*
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>:  este argumento pasa el Jugador de nuestro grafo
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
		*
		* Return value: void, no devuelve ningún valor.
		*
		*******************************************************************************************/
		
		public static void BFS(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g) {
			Vertex<DecoratedElementTenis<Jugador>> origen = devuelveVertices(g,"origen");
			Vertex<DecoratedElementTenis<Jugador>> destino = devuelveVertices(g,"destino");
			boolean hecho=hacerBFS(g,origen,destino);
			if(hecho==true) {
				imprimirPila(destino.getElement(), origen.getElement());	
			}else {
				System.out.println("No existe una conexión entre los jugadores");
			}
		}
		
		
		/******************************************************************************************
		*
		* Method name: hacerBFS
		*
		* Description of the Method: Este método se encarga de hacer un BFS para la ronda de 
		* inauguración, se elegirá la secuencia más corta de jugadores que conecta a ambos 
		* capitanes con un ranking ATP superior a 50. Para ello cada vez que pasemos por un jugador
		* estableceremos a true el setVisited, mientras que q, vertice de un jugador, no este 
		* vacio y no se haya encontrado haremos un remove y lo guardaremos en u y lo recorreremos 
		* con el iterator. Si el jugador no ha sido visitado ni encontrado y el rango es menor o
		* igual a 50 lo añadiremos a la lista q. Una vez hecho el BFS, llamaremos al método 
		* imprimirPila pasando el argumento q.
		*
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>: este argumento pasa el Jugador de nuestro grafo
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
		* 	- Vertex<DecoratedElementTenis<Jugador>> origen: jugador, capitán, de origen
		* 	- Vertex<DecoratedElementTenis<Jugador>> destino: d: jugador, capitán, de destino
		*
		* Return value: boolean encontrado, nos devuelve true o false si encuentra un camino BFS
		*
		* List of Checked Exceptions:
		* 	- NullPointerException: Se lanza esta excepción cuando una aplicación intenta utilizar
		* 	  una referencia de objeto que tiene valor nulo.
		*
		*******************************************************************************************/
		
		public static boolean hacerBFS(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g,Vertex<DecoratedElementTenis<Jugador>> origen
				,Vertex<DecoratedElementTenis<Jugador>> destino)throws NullPointerException {

			Vertex<DecoratedElementTenis<Jugador>> u, z;
			boolean encontrado = false;
			Queue <Vertex<DecoratedElementTenis<Jugador>>> q = new LinkedBlockingQueue<Vertex<DecoratedElementTenis<Jugador>>>();
			z = null;
			Edge<DecoratedElementPartido<Partido>> e;
			Iterator<Edge<DecoratedElementPartido<Partido>>> it;
			origen.getElement().setVisited(true);
			q.add(origen);

			while (!q.isEmpty() && !encontrado) {
				u = q.remove();
				it = g.incidentEdges(u);
				while (it.hasNext() && !encontrado) {
					e = it.next();
						z = g.opposite(u, e);
						if (!z.getElement().getVisited() && !encontrado && z.getElement().getElement().getRango()<=50) {
							z.getElement().setVisited(true);
							q.add(z);
							z.getElement().setParent(u.getElement());
						if (z.getElement().equals(destino.getElement())) {
								encontrado = true;
								z.getElement().setParent(u.getElement());
						}
					}
				}
			}
			q.add(origen);
			return encontrado;
		}
		
		/******************************************************************************************
		*
		* Method name: DFS
		*
		* Description of the Method:  Para la ronda exprés utilizamos un DFS  para calcular una 
		* secuencia cualquiera de jugadores que conecte a esos dos. Con el if buscamos que los 
		* jugadores sean diestros o del mismo país que algunos de los dos capitanes y si se dan 
		* las condiciones lo pasaremos al método imprimirPila. En caso de que no se pueda se mostrara
		* por teclado el mensaje "Imposible establecer relación".
		*
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>: este argumento pasa los datos de nuestro partido
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
		*
		* Return value: void, no devuelve ningún valor.
		*
		*****************************************************************************************/
		
		public static void DFS(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g) {
			Stack<Vertex<DecoratedElementTenis<Jugador>>> pila= new Stack<Vertex<DecoratedElementTenis<Jugador>>>();
			Vertex<DecoratedElementTenis<Jugador>> origen = devuelveVertices(g,"origen");
			Vertex<DecoratedElementTenis<Jugador>> destino = devuelveVertices(g,"destino");
			boolean hecho=hacerDFS(g,origen,destino,pila,origen.getElement().getElement().getMano(),origen.getElement().getElement().getPais());
			if(hecho==false) {
				imprimirPila(destino.getElement(), origen.getElement());
			}else {
				System.out.println("No existe una conexión entre los jugadores");
			}
		}
		
		/****************************************************************************************
		*
		* Method name: hacerDFS
		*
		* Description of the Method: para recorrer el grafo para la ronda exprés cada vez que pasamos
		* por un jugador, damos el valor de true a setVisited para saber que hemos pasado por ese 
		* jugaodr y lo cogemos. Después vemos que si el jugador no esta visitado y es diestro o de 
		* la misma nacionalidad que el capitan lo pasamos a noEnd y este llama al método hacerDFS 
		* pasando los argumentos g, v, d, pila, capDiestro y nacionalidad.
		*
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>: este argumento pasa el Jugador de nuestro grafo
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
		* 	- Vertex<DecoratedElementTenis<Jugador>> o: jugador, capitán, de origen
		* 	- Vertex<DecoratedElementTenis<Jugador>> d: jugador, capitán, de destino
		* 	- Stack<Vertex<DecoratedElementTenis<Jugador>>> pila: pila con los jugadores de la 
		* 	  ronda exprés. 
		* 	- String capDiestro: argumento que guarda el capitán que es diestro
		* 	- String nacionalidad: argumento que guarda la nacionalidad del jugador
		*
		* Return value: boolean noEnd, se devuelve true o false
		* 
		* List of Checked Exceptions:
		* 	- NullPointerException: Se lanza esta excepción cuando una aplicación intenta utilizar
		* 	  una referencia de objeto que tiene valor nulo.
		*
		******************************************************************************************/
		
		public static boolean hacerDFS(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g, Vertex<DecoratedElementTenis<Jugador>> o,
				Vertex<DecoratedElementTenis<Jugador>> d, Stack<Vertex<DecoratedElementTenis<Jugador>>> pila, String capDiestro, String nacionalidad) throws NullPointerException {

			boolean noEnd = !o.equals(d);
			Edge<DecoratedElementPartido<Partido>> e;
			Iterator<Edge<DecoratedElementPartido<Partido>>> it;
			Vertex<DecoratedElementTenis<Jugador>> v;
			
			o.getElement().setVisited(true);
			pila.push(o);
			it = g.incidentEdges(o);
			while (it.hasNext() && noEnd) {
				e = it.next();

				v = g.opposite(o, e);
				if (!v.getElement().getVisited() && (v.getElement().getElement().getMano().equals(capDiestro) || v.getElement().getElement().getMano().equals(d.getElement().getElement().getMano())
					|| v.getElement().getElement().getPais().equals(nacionalidad) || v.getElement().getElement().getPais().equals(d.getElement().getElement().getPais())	)) {
					v.getElement().setParent(o.getElement());
					noEnd = hacerDFS(g, v, d, pila, capDiestro,nacionalidad);
				}
			}
			return noEnd;
		}
		
		/*****************************************************************************************
		*
		* Method name: devuelveVertices
		*
		* Description of the Method: utilizamos este método para el BFS y DFS para leer los jugadores
		* capitanes de las ronda de inauguración y exprés que se escribe por teclado. Se compara el 
		* jugador escrito por teclado con nuestro grafo para ver que exite. Si exite se devuelve el 
		* jugador por el argumento vertice.
		*
		* Calling arguments: 
		* 	- Graph<DecoratedElementTenis<Jugador>: este argumento pasa los datos de nuestro partido
		* 	- DecoratedElementPartido<Partido>> g: este argumento pasa los datos de nuestro partido
		* 	- String c: este argumento guarda los valores "origen" o "destino" y lo imprime por pantalla.
		*
		* Return value: Vertex<DecoratedElementTenis<Jugador>>: devuelve el jugador leido por teclado
		*
		* List of Checked Exceptions:
		* 	- NullPointerException: Se lanza esta excepción cuando una aplicación intenta utilizar
		* 	  una referencia de objeto que tiene valor nulo.
		*
		******************************************************************************************/
		
		public static Vertex<DecoratedElementTenis<Jugador>> devuelveVertices(Graph<DecoratedElementTenis<Jugador>,DecoratedElementPartido<Partido>>  g, String c) throws NullPointerException {
			Vertex<DecoratedElementTenis<Jugador>> vertice = null;
			Vertex<DecoratedElementTenis<Jugador>> comparador;
			Iterator<Vertex<DecoratedElementTenis<Jugador>>> vertices = g.getVertices();
			String o;
			System.out.println("Escribe el jugador " + c);
			o = leer.nextLine();
			
			while (vertices.hasNext() && vertice==null) {
				comparador= vertices.next();
				if(comparador.getElement().getElement().getNombre().equals(o)) {
				vertice=comparador;
				}
			}
			return vertice;
		}
		
		/*****************************************************************************************
		*
		* Method name: imprimirPila
		*
		* Description of the Method: Este método lo utilizamos para mostar por pantalla los dos 
		* equipos crados para la ronda exprés. Le pasamos una pila con los jugadores que cumplen 
		* el ser disetros o ser del mismo país que alguno de los capitanes, vamos sacando de esta
		* pila los jugadores y guardandolos en aux para despues obtener sus datos e imprimirlos.
		* Haremos lo mismo con los dos equipos.
		*
		* Calling arguments: 
		* 	- Stack<Vertex<DecoratedElementTenis<Jugador>>> pila: pila con los jugadores de la 
		* 	  ronda exprés. 
		*
		* Return value: void, no devuelve ningún valor.
		*
		****************************************************************************************/
		
		public static void imprimirPila(DecoratedElementTenis<Jugador> destino, DecoratedElementTenis<Jugador> origen) {
			DecoratedElementTenis<Jugador> aux=destino;
			Stack<DecoratedElementTenis<Jugador>> pilaAux = new Stack<DecoratedElementTenis<Jugador>>();
			int contador = 0, contador2=0, tamaño;
			boolean fin=false;
			Stack<DecoratedElementTenis<Jugador>> pila= new Stack<DecoratedElementTenis<Jugador>>();
			
		
			while(fin==false) {
				pila.add(aux);
				if(origen.equals(aux)) {
					System.out.println();
					fin=true;
				}else {
					aux=aux.getParent();
				}
			}
			
			
			tamaño=pila.size()/2;
			while (!pila.isEmpty()) {
				if(contador==0) {
				System.out.print("Equipo 1, capitaneado por: ");
				}
				contador++;
				if(contador<=tamaño) {
					aux=pila.pop();
					System.out.println(aux.getElement().getNombre());
				}else {
					pilaAux.push(pila.pop());
				}
			}
			while(!pilaAux.empty()) {
				if(contador2==0) {
				System.out.print("Equipo 2, capitaneado por: ");
				}
				aux= pilaAux.pop();
				System.out.println(aux.getElement().getNombre());
				contador2++;
				
			}
		}
		
}