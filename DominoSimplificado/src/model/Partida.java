package model;

import model.*;
import utils.Entrada;
import java.util.Scanner;

public class Partida {
    private Mesa mesa;
    private Monton monton;

    private Jugador jugador1;
    private Jugador jugador2;

    
    private Scanner scanner;

    public Partida() {
        mesa = new Mesa();
        monton = new Monton();

        jugador1 = new Jugador("Jugador 1");
        jugador2 = new Jugador("Jugador 2");

        scanner = new Scanner(System.in);
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
        Jugador jugadorActual = jugador1; //El jugador 1 comienza la partida.
        while (true) {
            System.out.println();
            System.out.println("Turno de " + jugadorActual.getNombre());
                        
            //SIRVE COMO BULEANO Y ADEMÁS REALIZA EL TURNO
           while (realizarTurno(jugadorActual)){ //Devuelve un buleano para que se repita si es true
        	   System.out.println("El montón tiene: " + monton.cantidadFichas() + " fichas");

               System.out.println("Mesa: " + mesa);        	   
           }
            	            //Para que lo haga también cuando sale
           System.out.println("El montón tiene: " + monton.cantidadFichas() + " fichas");

           System.out.println("Mesa: " + mesa);
           
            /*if (jugadorActual.cantidadFichas() == 0) {
                terminarPartida(jugadorActual);
                break;
            }*/
           //La partida termina cuando un jugador se queda sin fichas o no se pueden poner más
           //¿Cuándo no se pueden poner más? Cuando el montón está vacío y nada de la mano de los jugadores vale
           //o cuando la ficha que falta es imposible de poner por todas las que hay en la mesa
           //Es decir, que cuando mesa tiene todas las de 4 y las de 5, por ejemplo
           //Es decir, que sin robar, en el montón y en las manos no hay más f de 4 y 5.
             if (jugadorActual.cantidadFichas() == 0) {
           terminarPartida(jugadorActual);
           break;
       		}
            
            jugadorActual = jugadorActual == jugador1 ? jugador2 : jugador1;//lo de antes pero para simplificarlo en una linea   
        }
    }

    public boolean realizarTurno(Jugador jugador) {
        jugador.mostrarMano();

        System.out.println("Escriba numero de ficha, 'R' para robar o 'P' para pasar: ");

        String entrada = scanner.nextLine();

        if (entrada.equalsIgnoreCase("R")) {
            jugador.robarFicha(monton);
            
            return true;
        } else if (entrada.equalsIgnoreCase("P")) {
        	if (monton.puedoPasar()== false) {
        		System.out.println("El montón aún tiene fichas");
        		//volvería a salir el menú de opciones        		
        		return true;
        	} else return false;
        } else {

        	int indice = Integer.parseInt(entrada);

        	Ficha ficha = jugador.getFicha(indice);

        	System.out.println("¿Dónde colocar la ficha? ('I' para inicio, 'F' para final): ");

        	String lado = scanner.nextLine();

        	if (lado.equalsIgnoreCase("I")) {
        		if (mesa.puedePonerseInicio(ficha)) {
        			ficha = jugador.jugarFicha(indice);
        			mesa.ponerInicio(ficha);
        			return false; //Terminar turno
        		} else {
        			System.out.println("No se puede colocar la ficha en el inicio.");
        			return true; //Volver a tocar turno
        		}
        	} else if (lado.equalsIgnoreCase("F")) {
        		if (mesa.puedePonerseFinal(ficha)) {
        			ficha = jugador.jugarFicha(indice);
        			mesa.ponerFinal(ficha);
        			return false; //Terminar turno
        		} else {
        			System.out.println("No se puede colocar la ficha en el final.");
        			return true; //Volver a tocar turno
        		}
        		
        	}
        		//FALTA:
        /*} else {
            System.out.println("Entrada no válida.");
        }*/
        return false;
        }
        
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
    
    public void cierre(){
    	int puntosJ1 = jugador1.calcularPuntos();
    	int puntosJ2 = jugador2.calcularPuntos();
    }
    }
}
