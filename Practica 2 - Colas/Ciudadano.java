package eda2;

/*********************************************************************************************
*
* Class Name: Ciudadano
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago González Millón
* Release/Creation date: 03/11/2020
* Class version: 1.0
* Class description: En esta clase crearemos el objeto Ciudadano junto con sus correspondientes
* atributos: edad y tiempo.
*
**********************************************************************************************/
public class Ciudadano {
 private int edad; //variable utilizada para guardar la edad del ciudadano
 private int tiempo;  //variable utilizada para guardar el tiempo promedio de la realización de la prueba del ciudadano


public Ciudadano(int tiempo, int edad) {
	this.edad = edad;
	this.tiempo = tiempo;
}


public int getEdad() {
	return edad;
}

public void setEdad(int edad) {
	this.edad = edad;
}

public int getTiempo() {
	return tiempo;
}

public void setTiempo(int tiempo) {
	this.tiempo = tiempo;
}

/******************************************************************************************
*
* Method name: toString
*
* Description of the Method: En este método mostraremos por pantalla los atributos de la clase
* Ciudadano
*
* Return value: String datos.
* 
******************************************************************************************/

public String toString() {
	return "Ciudadano [edad=" + edad + ", tiempo=" + tiempo + "]";
}

}