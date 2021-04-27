package examen;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ejercicio001 implements Runnable {
	public static void main(String[] args) {
		System.out.println("hola");
	}
    Puerta      puerta;
    Almacen     almacen;
    String      nombre;
    Random      generador;
    final int MAX_INTENTOS  =   10;
    public ejercicio001(Puerta p, Almacen a, String nombre){
            this.puerta     =   p;
            this.almacen    =   a;
            this.nombre     =   nombre;
            generador       =   new Random();
    }

    public void esperar(){
            try {
                    int ms_azar = generador.nextInt(100);
                    Thread.sleep(ms_azar);
            } catch (InterruptedException ex) {
                    System.out.println("Falló la espera");
            }
    }
    @Override
    public void run() {
            for (int i=0; i<MAX_INTENTOS; i++){
                    if (!puerta.estaOcupada()){
                            if (puerta.intentarEntrar()){
                                    esperar();
                                    puerta.liberarPuerta();
                                    if (almacen.cogerProducto()){
                                            System.out.println(
                                                            this.nombre + ": cogí un producto!");
                                            return ;
                                    }
                                    else {
                                            System.out.println(
                                                       this.nombre+
                                                       ": ops, crucé pero no cogí nada");
                                            return ;
                                    } //Fin del else
                            } //Fin del if
                    } else{
                       esperar();
                    }

            } //Fin del for
            //Se superó el máximo de reintentos y abandonamos
            System.out.println(this.nombre+
                            ": lo intenté muchas veces y no pude");
    }
	
	
}

class Almacen{
	   private int numProductos;
       public Almacen(int nProductos){
               this.numProductos=nProductos;
       }
       public boolean cogerProducto(){
               if (this.numProductos>0){
                       this.numProductos--;
                       return true;
               }
               return false;
       }
	
	
}

 class Puerta {
    boolean ocupada;

    Puerta(){
            this.ocupada=false;

    }
    public boolean estaOcupada(){
            return this.ocupada;
    }
    public synchronized void liberarPuerta(){
            this.ocupada=false;
    }
    public synchronized boolean intentarEntrar(){
            if (this.ocupada) return false;
            /* Si llegamos aquí, la puerta estaba libre
            pero la pondremos a ocupada un tiempo
            y luego la volveremos a poner a "libre"*/
            this.ocupada=true;
            return true;
    }
}



 class GrandesAlmacenes {
    public static void main(String[] args) throws InterruptedException {
            final int NUM_CLIENTES  = 300;
            final int NUM_PRODUCTOS = 100;

            ejercicio001[]   cliente =   new ejercicio001[NUM_CLIENTES];
            Almacen     almacen =   new Almacen(NUM_PRODUCTOS);
            Puerta      puerta  =   new Puerta();

            Thread[]    hilosAsociados=new Thread[NUM_CLIENTES];

            for (int i=0; i<NUM_CLIENTES; i++){
                    String nombreHilo   = "Cliente "+i;
                    cliente[i]          = new ejercicio001(puerta, almacen, nombreHilo);
                    hilosAsociados[i]   = new Thread(cliente[i]);
                    //Intentamos arrancar el hilo
                    hilosAsociados[i].start();
            } //Fin del for

            //Una vez arrancados esperamos a que todos terminen
            for (int i=0; i<NUM_CLIENTES; i++){
                    hilosAsociados[i].join();
            } //Fin del for
    }
}




