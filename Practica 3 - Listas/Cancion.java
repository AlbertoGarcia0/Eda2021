package listas;

/*********************************************************************************************
*
* Class Name: Cancion
* Author/s name: Alberto Garc�a Aparicio, Laura Morales Caro, Santiago Gonz�lez Mill�n
* Release/Creation date: 10/11/2020
* Class version: 1.0
* Class description: En esta clase crearemos el objeto cancion junto con sus correspondientes
* atributos: titulo, artista, genero, duracion y reproducciones. 
*
**********************************************************************************************/

public class Cancion {
	private String titulo; //variable utilizada para guardar el titulo de la cancion
	private String artista;  //variable utilizada para guardar el artista de la cancion
	private String genero;  //variable utilizada para guardar el genero de la cancion
	private int duracion; //variable utilizada para guardar la duracion de la cancion
	private int reproducciones;  //variable utilizada para guardar el n� de reproducciones de la cancion
	
	/******************************************************************************************
	*
	* Method name: Cancion
	*
	* Description of the Method: Este m�todo lo utilizamos para inicializar las variables con 
	* valores coherentes. 
	*
	* Calling arguments: String titulo, String artista, String genero, int duracion, 
	* int reproducciones
	*
	******************************************************************************************/
	
	public Cancion(String titulo, String artista, String genero, int duracion, int reproducciones){
		this.titulo = titulo;
		this.artista = artista;
		this.genero = genero;
		this.duracion = duracion;
		this.reproducciones = reproducciones;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public int getReproducciones() {
		return reproducciones;
	}
	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}
	
	/******************************************************************************************
	*
	* Method name: toString
	*
	* Description of the Method: En este m�todo mostraremos por pantalla los atributos de la clase
	* cancion 
	*
	* Return value: String datos.
	* 
	******************************************************************************************/
	
	public String toString(){
		
			String datos = "";
			datos += "\nTitulo: " + titulo;
			datos += "\nArtista: " + artista;
			datos += "\nG�nero: " + genero;
			datos += "\nDuraci�n: " + duracion;
			datos += "\nN�mero de reproducciones: " + reproducciones;
			return datos;
	}
}

