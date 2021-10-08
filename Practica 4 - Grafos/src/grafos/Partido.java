package grafos;

/****************************************************************************************
*
* Class Name: Partido
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: En esta clase crearemos el objeto Partido junto con sus correspondientes
* atributos: torneo y numeroPartido. De estos tendremos sus correspondientes gets y sets
* y un método to String para mostar por pantalla los atributos.
*****************************************************************************************
*/

public class Partido {
	private String torneo;	
	private int numeroPartido;
	private int id;
	
	public Partido(String torneo, int numeroPartido) {
		this.torneo = torneo;
		this.numeroPartido = numeroPartido;

	}

	public String getTorneo() {
		return torneo;
	}
	public void setTorneo(String torneo) {
		this.torneo = torneo;
	}
	public int getNumeroPartido() {
		return numeroPartido;
	}
	public void setNumeroPartido(int numeroPartido) {
		this.numeroPartido = numeroPartido;
	}	
	
	public String toString() {
		return "Partido [torneo=" + torneo + ", numeroPartido=" + numeroPartido + "]";
	}
	
}
