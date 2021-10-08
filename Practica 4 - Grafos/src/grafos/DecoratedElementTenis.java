package grafos;

import graphsDSESIUCLM.*;

/****************************************************************************************
*
* Class Name: Jugador
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: En esta clase crearemos elemento decorado Jugador que implementa la
* clase Element con sus correspondientes atributos: ID, element, visited, parent y distance. 
* Tambien tendremos gets y sets de todos nuestros atributos y un método toString para
* mostrar los atributos por pantalla.
* 
*****************************************************************************************
*/

public class DecoratedElementTenis<Jugador> implements Element {


	  private String ID;                 				//Vertex ID
	  private Jugador element;                		    //Data Element
	  private boolean visited;        				    //Attribute to label the node as visited
	  private DecoratedElementTenis<Jugador> parent;   // Vertex from which
	  										       	   // the current node is accessed
	  private int distance;   						   // Distance (in vertices) from the original node

	  public DecoratedElementTenis(String key, Jugador element) {
	    this.element = element;
	    ID = key;
	    visited = false;
	    parent = null;
	    distance = 0;
	  }

	  public Jugador getElement() {
	    return element;
	  }
	  public boolean getVisited() {
	    return visited;
	  }
	  public void setVisited(boolean t) {
	    visited = t;
	  }
	  public DecoratedElementTenis<Jugador> getParent() {
	    return parent;
	  }
	  public void setParent(DecoratedElementTenis<Jugador> u) {
	    parent = u;
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
	       && element.equals(((DecoratedElementTenis<Jugador>) n).getElement()));
	  }
	  public String toString() {
	    return element.toString();   //element needs to override toString()
	  }
	  public String getID() {
	    return ID;
	  }
}