/*********************************************************************************************
*
* Class Name: B2G07
* Author/s name: Alberto García Aparicio, Laura Morales Caro, Santiago González Millón
* Release/Creation date: 16/10/2020
* Class version: 1.0
* Class description: En esta clase lo que haremos es ejecutar un juego simple que consiste 
* en ir ordenando las piezas en una pila, cada color tiene una puntuación diferente. Además 
* tenemos la pieza estrella que lo que hará es darle la vuelta a la pila, aunque no sumará 
* puntos. Lo primero que haremos será leer por teclado los datos del juego y los iremos 
* colocando pieza por pieza. Si hay dos piezas del mismo color sacaremos las 2 y sumaremos 
* el doble de puntos de la pieza en cuestión. Al final sacaremos por pantalla el total de
* movimientos, los puntos totales y las posiciones finales de las fichas en la pila.
*
**********************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class B2G07 {
	
	/******************************************************************************************
	*
	* Method name: main
	*
	* Description of the Method: Se encarga de hacer un try del método buclePrincipal y mostrar 
	* por pantalla el mensaje "Fin del programa..." cuando este termine. Si no se consigue hacer 
	* el try se lanzará y cogerá la excepción e.
	*
	* Calling arguments: String[] args, son para los argumentos de línea de 
	* comandos en Java
	*
	* Return value: void, no estamos devolviendo ningún valor
	*
	* Required Files: 
	* 		- datos.txt: Este contiene los datos de las piezas del juego
	*
	* List of Checked Exceptions: 
	* 		- FileNotFoundException e: Si no encontramos el fichero con los datos del programa 
	* 		  lanzaremos la excepción e y mostrara por pantalla el mensaje "No existe este fichero 
	* 		  datos en el directorio indicado"
	*
	******************************************************************************************/
	public static void main(String[] args) throws IOException {
		// Bienvenida del programa
		System.out.println("----------------------\n   Contador de pila\n----------------------");
		try {
			buclePrincipal("datos.txt"); /* */
			System.out.println("\nFin del programa...");
		} catch (FileNotFoundException e) {
			System.out.println("No existe este fichero datos en el directorio indicado");
		}
	}

	/******************************************************************************************
	*
	* Method name: rellenarPila
	*
	* Description of the Method: Este método se encarga de guardar los datos en una pila. 
	* StringTokenizer mantiene internamente una posición actual dentro de la cadena que se va a 
	* convertir en token. Se devuelve un token tomando una subcadena de la cadena que se usó para 
	* crear el objeto StringTokenizer.
	*
	* Calling arguments: String cadena, Stack<String> pila
	*
	* Return value: void, no estamos devolviendo ningún valor
	* 
	******************************************************************************************/
	public static void rellenarPila(String cadena, Stack<String> pila) {

		StringTokenizer tokens = new StringTokenizer(cadena); // Dividimos la cadena
		while (tokens.hasMoreTokens()) { // metemos en la pila los trozos de la cadena
			pila.push(tokens.nextToken());
		}
	}
	
	/******************************************************************************************
	*
	* Method name: truncar
	*
	* Description of the Method: Este método se encarga de "dar la vuelta" a la pila del juego 
	* cuando llegue una pieza de la forma estrella
	*
	* Calling arguments: Stack<String> pila
	*
	* Return value: Stack<String>, devolvemos la pila que nos había llegado antes pero ahora 
	* truncada
	*
	******************************************************************************************/
	public static Stack<String> truncar(Stack<String> pila) {
		Stack<String> aux = new Stack<>();
		do {
			rellenarPila(pila.pop(), aux);
		} while (!pila.isEmpty());
		return aux;
	}
	
	/******************************************************************************************
	*
	* Method name: valor color
	*
	* Description of the Method: A través del argumento color, veremos cuantos puntos vale una 
	* pieza dependiendo de su color.
	*
	* Calling arguments: String color, este argumento lleva los datos de los colores de cada pieza
	*
	* Return value: int valor, devolvemos el valor de puntos de la pieza que se nos ha consultado
	* 
	*******************************************************************************************/
	public static int valorColor(String color) {
		int valor = 0; //en esta varaible guardaremos el valor de la pieza según su color
		if (color.equals("red")) {
			valor = 1;
		} else if (color.equals("blue")) {
			valor = 2;
		} else if (color.equals("green")) {
			valor = 3;
		} else if (color.equals("yellow")) {
			valor = 4;
		} else {
			valor = 5;
		}
		valor = valor * 2;
		return valor;
	}
	
	/******************************************************************************************
	*
	* Method name: buclePrincipal
	*
	* Description of the Method: Este método se encarga de ejecutar el programa principal,
	* leeremos el fichero que contiene los datos del juego, si la pieza es un cuadrado primero
	* tendremos que dividir el dato en color y forma y después iremos colocando en la pila las piezas
	* según las vayamos leyendo mediante el método rellenarPila y sumaremos un movimiento en la 
	* variable movimientos. Si la pieza es una estrella llamaremos al método truncar para "dar la
	* vuelta" a la pila. Si la pieza nueva es igual que la anterior retiraremos la pieza anterior 
	* y llamararemos al método valorColor para ver los puntos de la pieza según el color y los 
	* iremos sumando a lo largo de la lectura del fichero. Cuando terminemos de leer el fichero 
	* se imprimirá el total de movimientos, de puntos y las posiciones finales de la pila.
	* 
	* Calling arguments: String cadena, esta cadena serán los datos del juego
	*
	* Return value: void, no estamos devolviendo ningún valor
	*
	* Required Files: 
	* 		- datos, se creará un nuevo File a partir del fichero cadena
	*
	*******************************************************************************************/
	public static void buclePrincipal(String cadena) throws IOException {
		File datos = new File(cadena);
		Scanner fichero = new Scanner(datos);
		Stack<String> pila = new Stack<>();  // en la variable pila del tipo Stack guardaremos las posiciones de las piezas
		int puntos = 0, movimientos = 0;  
		//en la variable puntos guardaremos los puntos finales de la pila y en la variable movimiento iremos guardando
		//los movimientos que vayamos realizando

		while (fichero.hasNext()) {
			String tipoPieza = fichero.next();
			movimientos++;

			if (tipoPieza.equals("star")) {
				// truncar objeto
				if (!pila.isEmpty()) {
					pila = truncar(pila);
				}
			}

			else {
				String[] parts = tipoPieza.split(";"); //utilizamos la variable parts para dividir el color y forma de la piza 
													   // del archivo en dos 
				String forma = parts[0]; //en la variable forma guardaremos la primera parte de la linea del archivo, la forma
				String color = parts[1]; //en la variable forma guardaremos la segunda parte de la linea del archivo, el color

				if (pila.isEmpty()) {
					rellenarPila(color, pila);
				}

				else {

					if (!color.equals(pila.peek())) {
						rellenarPila(color, pila);
					}

					else {
						puntos = puntos + valorColor(color);
						pila.pop();
					}
				}
			}

		}
		fichero.close();
		System.out.println("\nEstado final de la pila: " + pila + "\nTotal de puntos: " + puntos
				+ "\nTotal de movimientos: " + movimientos);
	}
}