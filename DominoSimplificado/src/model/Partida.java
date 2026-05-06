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

            realizarTurno(jugadorActual);

            System.out.println("El monton tiene: " + monton.cantidadFichas() + " fichas");

            System.out.println("Mesa: " + mesa);

            if (jugadorActual.cantidadFichas() == 0) {
                terminarPartida(jugadorActual);
                break;
            }

            if (jugadorActual == jugador1) {
                jugadorActual = jugador2;
            } else {
                jugadorActual = jugador1;
            }
        }
    }

    public void realizarTurno(Jugador jugador) {
        jugador.mostrarMano();

        System.out.println("Escriba numero de ficha, o 'R' para robar: ");

        String entrada = scanner.nextLine();

        if (entrada.equalsIgnoreCase("R")) {
            jugador.robarFicha(monton);
            return;
        }

        int indice = Integer.parseInt(entrada);

        Ficha ficha = jugador.getFicha(indice);

        System.out.println("¿Dónde colocar la ficha? ('I' para inicio, 'F' para final): ");

        String lado = scanner.nextLine();

        if (lado.equalsIgnoreCase("I")) {
            if (mesa.puedePonerseInicio(ficha)) {
                ficha = jugador.jugarFicha(indice);
                mesa.ponerInicio(ficha);
            } else {
                System.out.println("No se puede colocar la ficha en el inicio.");
            }
        } else if (lado.equalsIgnoreCase("F")) {
            if (mesa.puedePonerseFinal(ficha)) {
                ficha = jugador.jugarFicha(indice);
                mesa.ponerFinal(ficha);
            } else {
                System.out.println("No se puede colocar la ficha en el final.");
            }
        } else {
            System.out.println("Entrada no válida.");
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
}
