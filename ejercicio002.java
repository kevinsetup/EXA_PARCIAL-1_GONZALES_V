package examen;

public class ejercicio002 {
	 private int saldo;
	    private int saldoInicial;

	    public ejercicio002(int saldo){
	        this.saldoInicial=saldo;
	        this.saldo=saldo;
	    }
	    public synchronized void hacerMovimiento(int cantidad){
	        this.saldo = this.saldo+cantidad;
	    }
	    public boolean esSimulacionCorrecta(){
	        if (this.saldo==this.saldoInicial) return true;
	        return false;
	    }
	    public int getSaldo(){
	        return this.saldo;
	    }
	    public static void main(String[] args) {
			System.out.println("hola");
		}
	
	
	
}

 class HiloCliente implements Runnable{
    ejercicio002 cuenta;
    int cantidad;

    public HiloCliente(ejercicio002 cuenta, int cantidad) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        /* Forzamos la maquinaria: repetimos
        la operación muchísimas veces para
        intentar verificar si la simulación es
        correcta
        */
        for (int i=0; i<100; i++){
            cuenta.hacerMovimiento(cantidad);
        }
    }
}
 
  class Lanzador {
	    public static void main(String[] args) throws InterruptedException{
	        ejercicio002 cuenta = new ejercicio002(100);

	        final int NUM_OPS_CON_100 = 40;
	        final int NUM_OPS_CON_50  = 20;
	        final int NUM_OPS_CON_20  = 60;

	        Thread[] hilosIngresan100 = new Thread[NUM_OPS_CON_100];
	        Thread[] hilosRetiran100  = new Thread[NUM_OPS_CON_100];
	        Thread[] hilosIngresan50  = new Thread[NUM_OPS_CON_50];
	        Thread[] hilosRetiran50   = new Thread[NUM_OPS_CON_50];
	        Thread[] hilosIngresan20  = new Thread[NUM_OPS_CON_20];
	        Thread[] hilosRetiran20   = new Thread[NUM_OPS_CON_20];

	        /* Arrancamos todos los hilos*/
	        for (int i=0; i<NUM_OPS_CON_100;i++){
	            HiloCliente ingresa = new HiloCliente(cuenta, 100);
	            HiloCliente retira  = new HiloCliente(cuenta, -100);

	            hilosIngresan100[i]= new Thread(ingresa);
	            hilosRetiran100[i] = new Thread(retira);

	            hilosIngresan100[i].start();
	            hilosRetiran100[i].start();
	        }

	        for (int i=0; i<NUM_OPS_CON_50;i++){
	            HiloCliente ingresa = new HiloCliente(cuenta, 50);
	            HiloCliente retira  = new HiloCliente(cuenta, -50);

	            hilosIngresan50[i]= new Thread(ingresa);
	            hilosRetiran50[i] = new Thread(retira);

	            hilosIngresan50[i].start();
	            hilosRetiran50[i].start();
	        }

	        for (int i=0; i<NUM_OPS_CON_20;i++){
	            HiloCliente ingresa = new HiloCliente(cuenta, 20);
	            HiloCliente retira  = new HiloCliente(cuenta, -20);

	            hilosIngresan20[i]= new Thread(ingresa);
	            hilosRetiran20[i] = new Thread(retira);

	            hilosIngresan20[i].start();
	            hilosRetiran20[i].start();
	        }

	        /* En este punto todos los hilos están arrancados,
	        ahora toca esperarlos */

	        for (int i=0; i<NUM_OPS_CON_100;i++){
	            hilosIngresan100[i].join();
	            hilosRetiran100[i].join();
	        }

	        for (int i=0; i<NUM_OPS_CON_50;i++){
	            hilosIngresan50[i].join();
	            hilosRetiran50[i].join();
	        }

	        for (int i=0; i<NUM_OPS_CON_20;i++){
	            hilosIngresan20[i].join();
	            hilosRetiran20[i].join();
	        }
	        if (cuenta.esSimulacionCorrecta()){
	            System.out.println("La simulación fue correcta");
	        } else {
	            System.out.println("La simulación falló ");
	            System.out.println("La cuenta tiene:"+
	                    cuenta.getSaldo());
	            System.out.println("Revise sus synchronized");
	        }

	    }
	}
 
 
 
 
