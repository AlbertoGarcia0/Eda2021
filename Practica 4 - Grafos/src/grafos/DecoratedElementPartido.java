package grafos;

import graphsDSESIUCLM.*;

/****************************************************************************************
*
* Class Name: Jugador
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: En esta clase crearemos elemento decorado Partido que implementa la
* clase Element con sus correspondientes atributos: ID, element, visited y distance. 
* Tambien tendremos gets y sets de todos nuestros atributos y un método toString para
* mostrar los atributos por pantalla.
* 
*****************************************************************************************
*/

public class DecoratedElementPartido<Partido> implements Element {


	  private String ID;                	 //Vertex ID
	  private Partido element;               //Data Element
	  private boolean visited;       	     //Attribute to label the node as visited
	  private int distance;   				 //Distance (in vertices) from the original node

	  public DecoratedElementPartido(String key, Partido element) {
	    this.element = element;
	    ID = key;
	    visited = false;
	    distance = 0;
	  }

	  public Partido getElement() {
	    return element;
	  }
	  public boolean getVisited() {
	    return visited;
	  }
	  public void setVisited(boolean t) {
	    visited = t;
	  }
	  public int getDistance() {
	    return distance;
	  }
	  public void setDistance(int d) {
	    distance = d;
	  }

	  /* In this case, to check if two Vertices are identical, both the key and the
	   * element must be equal.
	   * Notice the cast to convert n (class Object) to class
	   * DecoratedElementShortestPath<T>
	   * IMPORTANT: element needs to override equals()
	  */
	  public boolean equals (Object n) {
	    return (ID.equals(((DecoratedElementTenis) n).getID())
	       && element.equals(((DecoratedElementTenis<Partido>) n).getElement()));
	  }
	  public String toString() {
	    return element.toString();   //element needs to override toString()
	  }
	  public String getID() {
	    return ID;
	  }
}