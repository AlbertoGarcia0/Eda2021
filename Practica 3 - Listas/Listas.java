package listas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

/*********************************************************************************************
*
* Class Name: Listas
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago González Millón
* Release/Creation date: 10/11/2020
* Class version: 1.0
* Class description: En esta clase lo que haremos es leer de un fichero canciones con su título,
* artista, género, duración y reproducciones. Después las organizaremos en tres listas de 
* reproducciones: Una para las canciones con más de 100 reproducciones, otra en la que guardaremos 
* canciones para tener 1 hora de música y otra en la cuál ponemos todas las canciones de género 
* chill. 
*
**********************************************************************************************/

public class Listas {
	static Scanner teclado = new Scanner(System.in);  //variable utilizada para leer lo escrito por pantalla
	
	/******************************************************************************************
	*
	* Method name: main
	*
	* Description of the Method: Se encarga de hacer un try en el main y mostrar por pantalla 
	* el mensaje "Fin del programa..." cuando este termine. Si no se consigue hacer el try se 
	* lanzará y cogerá la excepción e.
	*
	* Calling arguments: String[] args, son para los argumentos de línea de comandos en Java.
	*
	* Return value: void, no estamos devolviendo ningún valor.
	*
	* Required Files: 
	* 		- song.txt: Este contiene los datos de las canciones.
	*
	* List of Checked Exceptions: 
	* 		- FileNotFoundException e: Si no encontramos el fichero con los datos del programa 
	* 		  lanzaremos la excepción e y mostrara por pantalla el mensaje "No existe este fichero 
	* 		  datos en el directorio indicado".
	*
	******************************************************************************************/
	
	public static void main(String[] args) throws IOException {
		try {
			
			leerCanciones("C:\\Users\\34627\\git\\ISO_1\\LegacyProject\\iso1_db\\listas\\src\\listas\\songs.txt"); 
			
			System.out.println("\nFin del programa...");
		} catch (FileNotFoundException e) {
			System.out.println("No existe este fichero datos en el directorio indicado");
		}
	}
	

	
	/******************************************************************************************
	*
	* Method name: leerCanciones
	*
	* Description of the Method: Este método se encarga de leer el fichero de las canciones, 
	* crearemos tres playlist con la clase LinkedList de la librería java.util.package. En el 
	* primer if iremos guardando canciones, cuando la duracion de la playlist llege a la hora 
	* terminaremos de añadir canciones. Tambien llamaremos al método add_cancion para añadir 
	* canciones en las otras dos playlist. En un try catch crearemos un menú con el que el 
	* usuario interactuará para elegir que playlist quiere ver mediante el techado. 
	* 
	* Calling arguments: String cadena
	*
	* Return value: void, no estamos devolviendo ningún valor.
	* 
	******************************************************************************************/
	
	public static void leerCanciones(String cadena) throws IOException{
		File canciones = new File(cadena);
		Scanner fichero = new Scanner(canciones);
		int duracionMax=0; //utilizamos esta variable para guardar la duración máxima de la playlist 2
		
		List<Cancion> playlist1 = new LinkedList(); //en playlist 1 guardaremos la lista Our Favourite Songs
		List<Cancion> playlist2 = new LinkedList(); //en playlist 2 guardaremos la lista Costeando la costa
		List<Cancion> playlist3 = new LinkedList(); //en playlist 3 guardaremos la lista Lo-Fi Chill-Hop Beats to Relax to

		fichero.useDelimiter(";|\\r\\n");
		while (fichero.hasNext()){
			String titulo = fichero.next();
			String artista = fichero.next(); 
			String genero = fichero.next(); 
			int duracion = fichero.nextInt(); 
			int reproducciones = fichero.nextInt(); 
			Cancion c = new Cancion(titulo, artista, genero, duracion, reproducciones);

			//metemos canciones hasta llegar a la hora de duración
			if(duracionMax<3600) {
				duracionMax=duracionMax+c.getDuracion();
				playlist2.add(c);
			}
			//meter canciones en las otras dos listas
			add_cancion(c,playlist1,playlist3);
		} fichero.close();
        
		//menu para mostrar las listas con sus canciones
		int opcionMenu = 0; 
        boolean opcionSalir = false;
        String saltoLinea;
        do{
                    opciones();
                    try{
                        opcionMenu = teclado.nextInt();
                        if (opcionMenu < 1 || opcionMenu > 4) {
                            System.out.println("Opción incorrecta\nIntroduzca un valor entre el 1 y 4 incluidos los dos");
                        saltoLinea = teclado.nextLine();
                        }
                    }catch (InputMismatchException a) {
        
                    }
                    switch (opcionMenu){

                    case 1:
                		System.out.print("Our Favourite Songs ");
                    	show_cancion(playlist1);
                        break;

                    case 2:
                		System.out.print("Costeando la costa ");
                		show_cancion(playlist2);

                        break;

                    case 3:
                		System.out.print("Lo-Fi Chill-Hop Beats to Relax to ");
                		show_cancion(playlist3);
                        break;

                    case 4:
                        opcionSalir = true;
                        break;
                    }
                    
                } while (!opcionSalir);
    }
	
	/******************************************************************************************
	*
	* Method name: add_cancion
	*
	* Description of the Method: En este método comprobamos si la canción cumple con los requisitos
	* para introducirla en una o ambas listas. Para la primera lista veremos que si se ha reproducido
	* más de 100 veces y para la segunda veremos si es del género chill.
	*
	* Calling arguments: Cancion c, List<Cancion> p1, List<Cancion> p3
	*
	* Return value: void, no estamos devolviendo ningún valor.
	* 
	******************************************************************************************/
	
	public static void add_cancion(Cancion c, List<Cancion> p1, List<Cancion> p3){
		if(c.getReproducciones()>100) {
			p1.add(c);
		}
		if(c.getGenero().equals("chill")) {
			p3.add(c);
		}	
	}
	
	/******************************************************************************************
	*
	* Method name: show_cancion
	*
	* Description of the Method: Este método se encarga de mostrar las canciones que hay en una
	* lista de reproduccion en concreto. Mediante un for iremos recorriendo el objeto canción 
	* para obtener la duración, el título y el artista de cada canción y lo iremos guardando 
	* en el String lista que, tras acabar el for, se imprimirá por pantalla.
	*
	* Calling arguments: List<Cancion> p
	*
	* Return value: void, no estamos devolviendo ningún valor.
	* 
	******************************************************************************************/
	
	public static void show_cancion(List<Cancion> p){
		int tiempo=0; //en esta variable guardaremos la duración de la cancion
		Cancion c; //variable del tipo cancion que utilizamos para llamar a una cancion de la clase cancion
		String lista="";
		
		for(int i = 0 ; i < p.size(); i++) {
			c=p.get(i);
			tiempo=tiempo+c.getDuracion();
			lista= lista + c.getTitulo()+"-"+c.getArtista()+"\n";
		}
		calcularTiempo(tiempo);
		System.out.println(lista);		
	}
	
	/******************************************************************************************
	*
	* Method name: calcularTiempo
	*
	* Description of the Method: Este método se encarga de calcular y mostrar la duración de la
	* lista de reproduccion en horas y minutos. Pasaremos el tiempo de segundos a horas y minutos.
	*
	* Calling arguments: int t
	*
	* Return value: void, no estamos devolviendo ningún valor.
	* 
	******************************************************************************************/
	
	public static void calcularTiempo(int t){
		int horas=0;
		int minutos=0;
		horas=t/3600;
		minutos=t/60-horas*60;
		System.out.println(horas+" hours and "+minutos+ " minutes");
	}
	
	public static void opciones(){
	    System.out.println();
	    System.out.println("Elija una opción:");
	    System.out.println("1- Playlist 1: Our Favourite Songs");
	    System.out.println("2- Playlsit 2: Costeando la costa");
	    System.out.println("3- Playlist 3: Lo-Fi Chill-Hop Beats to Relax to");
	    System.out.println("4- Salir");
	    System.out.println();
	}

}
