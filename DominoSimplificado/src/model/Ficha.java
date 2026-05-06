package model;

public class Ficha {
    private int izquierda;
    private int derecha;

    public Ficha(int izquierda, int derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    public int getIzquierda() {
        return izquierda;
    }

    public int getDerecha() {
        return derecha;
    }

    public void girar() {
        int temp = izquierda;
        izquierda = derecha;
        derecha = temp;
    }

    @Override
    public String toString() {
        return "[" + izquierda + "|" + derecha + "]";
    }
}