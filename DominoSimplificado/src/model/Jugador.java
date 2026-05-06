package model;
import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void robarFicha(Monton monton) {
        Ficha fichaRobada = monton.robarFicha();

        if (fichaRobada != null) {
            mano.add(fichaRobada);
            //System.out.println(nombre + " ha robado: " + fichaRobada);
        } else {
            //System.out.println(nombre + " no pudo robar una ficha (monton vacío).");
        }
    }

    public void mostrarMano() {
        System.out.print("Fichas" + nombre + ": ");
        for (Ficha ficha : mano) {
            System.out.print(ficha + " ");
        }
        System.out.println();

        System.out.print("Número de ficha: ");
        for (int i = 0; i < mano.size(); i++) {
            System.out.print("  " + i + "   ");
        }
        System.out.println();
    }

    public Ficha getFicha(int indice) {
            return mano.get(indice); // Devuelve la ficha sin eliminarla de la mano
    }

    public Ficha jugarFicha(int indice) {
        return mano.remove(indice) ; // Elimina y devuelve la ficha jugada
    }

    public int cantidadFichas() {
        return mano.size(); // Devuelve la cantidad de fichas en la mano del jugador
    }

    public int calcularPuntos() {
        int suma = 0;

        for (Ficha ficha : mano) {
            suma += ficha.getIzquierda() + ficha.getDerecha();
        }

        return suma; // Devuelve la suma total de puntos en la mano del jugador
    }
}