package grafos;

import graphsDSESIUCLM.*;

/****************************************************************************************
*
* Class Name: Jugador
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago Gónzalez Millón
* Release/Creation date: 22/12/2020
* Class version: 1.0
* Class description: En esta clase crearemos el objeto Jugador junto con sus correspondientes
* atributos: nombre, mano, pais, rango, puntos. De estos tendremos sus correspondientes
* gets y sets y un método to String para mostar por pantalla los atributos.
*****************************************************************************************
*/

public class Jugador{
	private String nombre;
	private String mano;
	private String pais;
	private int rango;
	private int puntos;
	
	public Jugador(String nombre, String mano, String pais, int rango, int puntos) {
		this.nombre = nombre;
		this.mano = mano;
		this.pais = pais;
		this.rango = rango;
		this.puntos = puntos;

	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMano() {
		return mano;
	}

	public void setMano(String mano) {
		this.mano = mano;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getRango() {
		return rango;
	}

	public void setRango(int rango) {
		this.rango = rango;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public String toString() {
		return "Jugador [nombre=" + nombre + ", mano=" + mano + ", pais=" + pais + ", rango=" + rango + ", puntos="
				+ puntos + "]";
	}
	
	
	
	
}