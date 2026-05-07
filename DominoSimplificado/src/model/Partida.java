package model;

import utils.Entrada;
import java.util.Scanner;

public class Partida {
    private Mesa mesa;
    private Monton monton;

    private Jugador jugador1;
    private Jugador jugador2;

    private Jugador ultimoEnJugar; //así se quien cierra
   private boolean primerTurno;
    private Scanner scanner;

    public Partida() {
        mesa = new Mesa();
        monton = new Monton();

        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");

        scanner = new Scanner(System.in);
        
        ultimoEnJugar = null;
        
        primerTurno = true;
    }

    
    public void repartirFichas() { 
        for (int i = 0; i < 7; i++) {
            jugador1.robarFicha(monton);
            jugador2.robarFicha(monton);
        }
    }

    public void iniciarPartida() {
        repartirFichas();
        jugarPartida();
    }

    //Bucle principal del juego
    public void jugarPartida() {
        Jugador jugadorActual = jugador1;
        while (true) {
            System.out.println("\nTurno de " + jugadorActual.getNombre());
            boolean turnoTerminado = false;
            
            // *** FORZAR PRIMER TURNO: Solo se puede jugar el [6|6] ***
            if (primerTurno && jugadorActual == jugador1) {
                // Comprobar que Jugador1 tiene el doble 6
                if (jugadorActual.tieneDobleSeis()) {
                    int indice = jugadorActual.getIndiceDobleSeis();
                    Ficha ficha = jugadorActual.getFicha(indice);
                    // La mesa está vacía, ponemos la ficha (da igual inicio o final)
                    mesa.ponerInicio(ficha);  
                    jugadorActual.jugarFicha(indice);  
                    ultimoEnJugar = jugadorActual;
                    System.out.println(jugadorActual.getNombre() + " ha puesto automáticamente el [6|6] para comenzar la partida.");
                    primerTurno = false;   
                    turnoTerminado = true;
                } else {
                    // Esto no debería ocurrir porque fuerzo a que el montón dé el 6/6 al jugador1
                    System.out.println("Error: Jugador 1 no tiene el doble 6. No se puede iniciar la partida.");
                    return;
                }
            } else {
                // Resto de turnos normales
                while (!turnoTerminado) {
                    turnoTerminado = realizarTurno(jugadorActual);
                    if (!turnoTerminado) {
                        System.out.println("El montón tiene: " + monton.cantidadFichas() + " fichas");
                        System.out.println("Mesa: " + mesa);
                    }
                }
            }

            System.out.println("El montón tiene: " + monton.cantidadFichas() + " fichas");
            System.out.println("Mesa: " + mesa);

            // Comprobar fin de la partida
            if (jugadorActual.cantidadFichas() == 0) {
                terminarPartida(jugadorActual);
                break;
            }
            if (noSePuedePoner()) {
                cierre(jugadorActual);
                break;
            }

            jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        }
    }
    
    
    public boolean realizarTurno(Jugador jugador) {
        jugador.mostrarMano();
        System.out.print("Escriba número de ficha, 'R' para robar o 'P' para pasar: ");
        String entrada = scanner.nextLine();

        if (entrada.equalsIgnoreCase("R")) {
            // Robo automático múltiple hasta poder jugar o hasta que se acabe el montón
            boolean roboExitoso = false;
            while (monton.cantidadFichas() > 0) {
                Ficha robada = monton.robarFicha();
                if (robada != null) {
                    jugador.robarFichaDirecta(robada); // nuevo método en Jugador
                    System.out.println(jugador.getNombre() + " ha robado: " + robada);
                
                    if (tieneMovimiento(jugador)) {
                        roboExitoso = true;
                        break; 
                    }
                } else {
                    break;
                }
            }
            if (!roboExitoso && monton.cantidadFichas() == 0 && !tieneMovimiento(jugador)) {
                System.out.println("No se puede jugar ni robar. Turno pasado.");
                return true; 
            }
            return false; // repetir turno para que juegue la ficha robada (o si no pudo robar)
        }
        else if (entrada.equalsIgnoreCase("P")) {
            // Solo puede pasar si no tiene movimiento y el montón está vacío
            if (!tieneMovimiento(jugador) && monton.cantidadFichas() == 0) {
                System.out.println(jugador.getNombre() + " pasa el turno.");
                return true;
            } else {
                System.out.println("No puedes pasar: tienes movimientos posibles o aún quedan fichas en el montón.");
                return false;
            }
        }
        else {
            try {
                int indice = Integer.parseInt(entrada);
                if (indice < 0 || indice >= jugador.cantidadFichas()) {
                    System.out.println("Índice no válido.");
                    return false;
                }
                Ficha ficha = jugador.getFicha(indice);
                System.out.print("¿Dónde colocar? ('I' inicio, 'F' final): ");
                String lado = scanner.nextLine();

                if (lado.equalsIgnoreCase("I")) {
                    if (mesa.puedePonerseInicio(ficha)) {
                        ficha = jugador.jugarFicha(indice);
                        mesa.ponerInicio(ficha);
                        ultimoEnJugar = jugador;
                        return true; // turno terminado correctamente
                    } else {
                        System.out.println("No se puede colocar esa ficha en el inicio.");
                        return false;
                    }
                } else if (lado.equalsIgnoreCase("F")) {
                    if (mesa.puedePonerseFinal(ficha)) {
                        ficha = jugador.jugarFicha(indice);
                        mesa.ponerFinal(ficha);
                        ultimoEnJugar = jugador;
                        return true;
                    } else {
                        System.out.println("No se puede colocar esa ficha en el final.");
                        return false;
                    }
        		
		        	} else {
		                    System.out.println("Opción no válida. Use 'I' o 'F'.");
		                    return false;
		            }
		       } catch (NumberFormatException e) {
		            System.out.println("Entrada no válida. Debe ser un número, 'R' o 'P'.");
		                
		            return false;
		       }
		  }
    }

    
    
    // Verifica si el jugador tiene al menos una ficha jugable en algún extremo
    private boolean tieneMovimiento(Jugador jugador) {
        for (int i = 0; i < jugador.cantidadFichas(); i++) {
            Ficha f = jugador.getFicha(i);
            if (mesa.puedePonerseInicio(f) || mesa.puedePonerseFinal(f)) {
                return true;
            }
        }
        return false;
    }
    
    
    public void terminarPartida(Jugador ganador) {
         Jugador perdedor;
        if (ganador == jugador1) {
            perdedor = jugador2;
        } else {
            perdedor = jugador1;
        }
        
        int puntos = perdedor.calcularPuntos();

        System.out.println();
        System.out.println("¡" + ganador.getNombre() + " ha ganado la partida!");
        System.out.println("Puntos totales: " + puntos);
    }
    
    
    public boolean noSePuedePoner() {
    	//Excepción al comenzar para ahorrarnos una iteración
    	if (mesa.estaVacia()) return false;
    	//Mesa: Contamos cuantas fichas hay en la mesa de cada extremo   
    	//Si son 7, se termina la partida
    	int extIzq = mesa.getExtremoIzquierdo();
    	int extDer = mesa.getExtremoDerecho();
    	
    	int cantIzq = mesa.nFichasX(extIzq);
	    int cantDer = mesa.nFichasX(extDer);
	    
	    if (extIzq == extDer) {
	        return cantIzq == 7; //Si los dos extremos son iguales y hay 7 en la mesa, bloqueada
	    } else {
	        return (cantIzq == 7 && cantDer == 7);//Depende de si los dos son 7 o del otro se pueden poner
	    }
    }
    	
    
    
    public void cierre(Jugador queCierra) {
        int puntosJ1 = jugador1.calcularPuntos();
        int puntosJ2 = jugador2.calcularPuntos();
        System.out.println("\n--- CIERRE DE LA PARTIDA ---");
        System.out.println(jugador1.getNombre() + " tiene " + puntosJ1 + " puntos");
        System.out.println(jugador2.getNombre() + " tiene " + puntosJ2 + " puntos");

        Jugador ganador;
        int diferencia = Math.abs(puntosJ1 - puntosJ2);
        if (puntosJ1 < puntosJ2) {
            ganador = jugador1;
        } else if (puntosJ2 < puntosJ1) {
            ganador = jugador2;
        } else {
            // empate: gana quien realizó el cierre
            ganador = queCierra;
        }
        System.out.println("¡Ganador: " + ganador.getNombre() + " con " + diferencia + " puntos de diferencia!");
    }
}