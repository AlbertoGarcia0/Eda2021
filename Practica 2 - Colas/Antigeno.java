package eda2;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/*********************************************************************************************
*
* Class Name: Antigeno
* Author/s name: Alberto Garc�a Aparicio, Laura Morales Caro, Santiago Gonz�lez Mill�n
* Release/Creation date: 03/11/2020
* Class version: 1.0
* Class description: En esta clase lo que haremos ser� ver el coste y el tiempo total necesario
* para realizar las pruebas mediante una simulaci�n y rearemos las colas seg�n los puntos de 
* de atenci�n correspondientes.
*
**********************************************************************************************/

public class Antigeno {
	static final int numCiudadanos=10000; //en esta variable guardamos el n� de habitantes de Ciudad Real
	static final int tLlegada=8; //tiempo promedio de llegada de un nuevo ciudadano a la cola
	static final int costePunto=1200; //coste total por cada punto de atenci�n

	/***********************************************************************************************
	*
	* Method name: main
	*
	* Description of the Method: En este m�todo lo primero que haremos ser� leer lo escrito por teclado
	* para ver cuantos puntos de atenci�n hay. Crearemos una cola de ciudadanos, qPruebas, con 
	* Deque<Ciudadano>, este representa una cola en el que puedes a�adir y elimiar elementos tanto
	* delante como detr�s. Despu�s crearemos dos vectores, tPruebas y fPruebas con el tama�o de los 
	* puntos de atenci�n. En qPruebas iremos guardando los elementos de Ciudadanos con que iremos sacando
	* de la cola con LinkedBlockingDeque. Tambi�n crearemos un bucle for para ir a�andiendo personas a la
	* cola. Si no hay nadie en la cola guardaremos t+tramoedad, si existe alguien, miraremos el tiempo
	* de atencion del �ltimo en la cola y a ese tiempo le sumaremos tramoedad. Cuando el tiempo de atenci�n
	* sea iguala t se borrar� top. Cada vez que gaurdemos un ciudadanos veremos si la cola es mayor que la �ltima
	* vez y guardaremos la cola m�s larga en tPruebas. Cuando terminemos un punto de atenci�n sumaremos a 
	* la varible completo 1. Si completo es igual a puntos entenderemos que hemos terminado la simulaci�n y 
	* pondremos fin a true.
	*
	* Calling arguments: String[] args, son para los argumentos de l�nea de comandos en Java.
	*
	* Return value: void, no estamos devolviendo ning�n valor.
 	*
	*************************************************************************************************/
	
public static void main(String args[]) {
//variables
int t=0, tAten = 0; // utilizamos la variable t para guardar el tiempo de la cola y tAten para guardar
					// el tiempo de atenci�n de cada ciudadano
int puntos, ciuAtendidos=0, completo=0; // en puntos guardaremos los puntos de atenci�n totales, el ciuAtendidos iremos guardando los 
										// ciudadanos que ya han sido atendidos y completos para guardar los puntos que ya han sido completados
int i; //variable que utilizamos para ir recorriendo un vector
int [] menor= new int [2]; 
boolean fin=false; //cuando fin sea true el programa terminar�
Ciudadano c; //variable que tiene una referencia a la clase a la que pertenece y con el que podemos llamar
             // a los m�todos de la clase Ciudadano
Scanner lectura=new Scanner(System.in); //variable del tipo Scanner para leer lo escrito por teclado

//Lectura numero de puntos
System.out.println("Introduce el n�mero de puntos de atenci�n:");
puntos=lectura.nextInt();

//creacion de vectores principales
Deque<Ciudadano> qPruebas[]=new Deque[puntos];
int [] tPruebas = new int[puntos];
int [] fPruebas = new int[puntos];

//crear las listas en el vector
for (i=0; i<puntos; i++) {
	qPruebas[i]=new LinkedBlockingDeque<Ciudadano>();
}

//Bucle principal
do{
	for (i=0; i<puntos; i++) {
		
//Comprobar si el tiempo coincide con la finalizaci�n		
		if(!qPruebas[i].isEmpty()) {
			c=qPruebas[i].getFirst();
			if(t==c.getTiempo()) {
				qPruebas[i].remove();
			}
		}		

//Introducir personas en cola
		if(ciuAtendidos<numCiudadanos){
		if(t % tLlegada==0) {
			int edad=generarTiempoEdad();
			
			if(qPruebas[i].isEmpty()) {
				tAten=t+tramoEdad(edad);
				qPruebas[i].add(new Ciudadano(tAten,edad));
			}
			else {
				c=qPruebas[i].getLast();
				tAten=c.getTiempo()+tramoEdad(edad);
				qPruebas[i].add(new Ciudadano(tAten,edad));
			}
			ciuAtendidos++;
			//Se guarda la cantidad de personas si es mayor de la que ha habido en algun momento
			if(tPruebas[i] < qPruebas[i].size()) {
				tPruebas[i]=qPruebas[i].size();
			}
		}
	}
		
		if(ciuAtendidos==numCiudadanos && qPruebas[i].isEmpty()) {
			completo=0;
			fPruebas[i]=1;
			for(int j=0; j<fPruebas.length; j++) {
				completo=completo+fPruebas[j];
				}
			}
		
		
	}
	
	
	if(completo==puntos) {
		fin=true;
	}
	else {
	t=t+1;
	}
	
}while(fin==false);


menor=devoverMenor(tPruebas);

System.out.println("Gasto en puntos: "+gastoPuntos(puntos)+"�");
System.out.println("Tiempo de realizaci�n en minutos: "+t);
System.out.println("Cola mas larga: "+menor[1]+"; longitud: "+menor[0]);
menosDeUnDia(t);

lectura.close();
}

/***********************************************************************************************
*
* Method name: devolverMenor
*
* Description of the Method: En el vector menor guardaremos la longitud de la cola m�s larga en 
* la primera posici�n y cual ha sido la cola m�s larga en la segunda posici�n del vector.
*
* Calling arguments: int[] tPruebas
*
* Return value: int menor, devolveremos el valor de menor.
*
*************************************************************************************************/

public static int [] devoverMenor(int [] tPruebas) {
int [] menor= new int [2];
for(int i=0; i<tPruebas.length; i++) {
	if(tPruebas[i]>menor[0]) {
		menor[0]=tPruebas[i];
		menor[1]=i+1;
	}
}
return menor;
}

/***********************************************************************************************
*
* Method name: tramoEdad
*
* Description of the Method: En este m�todo veremos el tiempo promedio que tarda una persona en 
* ralizarse la prueba seg�n su edad. Las personas menores a 14 tardar�n 10 min, si estan entre 14
* y 59 a�os tardar�n 7 minutos y si son mayores a 59 a�os tardar�n 12 minutos.
*
* Calling arguments: int edad
*
* Return value: int, devolvemos el tiempo prmedio de la persona seg�n la edad
*
*************************************************************************************************/

public static int tramoEdad (int edad){
    int tiempoEdad=0;
    if (edad<14){
    	tiempoEdad=10;
    }else if (edad >59){
    	tiempoEdad=12;
    }else{
    	tiempoEdad=7;
    }
    return tiempoEdad;
}

/***********************************************************************************************
*
* Method name: generarTiempoEdad
*
* Description of the Method: Para la simulaci�n necesitaremos calcular aleatoriamente la edad de
* una persona por lo que utilizaremos Random() para hacerlo, este nos dar� un n�mero aleatorio entre
* 1 y 80 y lo guardaremos en la variable tiempo
*
* Return value: int tiempo, devolvemos la edad de un ciudadano 
*
*************************************************************************************************/

public static int generarTiempoEdad() {
int tiempo=0;
tiempo = new  Random().nextInt(80);
return tiempo;
}

/***********************************************************************************************
*
* Method name: gastoPuntos
*
* Description of the Method: Calculamos el coste total de la simulaci�n seg�n los puntos de atenci�n
*
* Calling arguments: int puntos
*
* Return value: int total, devolvemos el gasto total por los puntos de atenci�n puestos
*
*************************************************************************************************/

public static int gastoPuntos(int puntos) {
int total;	
	total=puntos*costePunto;
	return total;
}

/***********************************************************************************************
*
* Method name: menosDeUnDia
*
* Description of the Method: Si la simulaci�n se puede hacer en menos de 24 horas se mostrar�
* por pantalla "Configuraci�n de puestos v�lida".
*
* Calling arguments: int tiempo
*
* Return value: void, no estamos devolviendo ning�n valor.
*
*************************************************************************************************/

public static void menosDeUnDia(int tiempo) {
int dia= 1440;

	if(tiempo<dia) {
		System.out.println("Configuraci�n de puestos v�lida");
}

}
}