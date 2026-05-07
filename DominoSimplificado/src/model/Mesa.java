package model;
import java.util.LinkedList;

//Necesidades: saber si está vacia, extremos, validar fichas, colocar inicio y final, mostrar mesa.
public class Mesa {
    private LinkedList<Ficha> fichasMesa;

    //Constructor
    public Mesa() {
        fichasMesa = new LinkedList<> ();
    }

    //Métodos
    public boolean estaVacia() {
        return fichasMesa.isEmpty();
    }

    public int getExtremoIzquierdo() {
        return fichasMesa.getFirst().getIzquierda(); //getFirst() devuelve el primer elemento (ficha), y getIzquierda() devuelve el valor del lado izquierdo de esa ficha.
    }

    public int getExtremoDerecho() {
        return fichasMesa.getLast().getDerecha(); //getLast() devuelve el último elemento (ficha), y getDerecha() devuelve el valor del lado derecha de esa ficha.
    }

    public boolean puedePonerseInicio(Ficha ficha) {
        if (estaVacia()) {
            return true; //Si la mesa está vacía, cualquier ficha puede colocarse.
        }
        int extremo = getExtremoIzquierdo();
        return ficha.getIzquierda() == extremo || ficha.getDerecha() == extremo; //La ficha puede colocarse si alguno de sus lados coincide con el extremo izquierdo de la mesa.
    }

    public boolean puedePonerseFinal(Ficha ficha) {
        if (estaVacia()) {
            return true; //Si la mesa está vacía, cualquier ficha puede colocarse.
        }
        int extremo = getExtremoDerecho();
        return ficha.getIzquierda() == extremo || ficha.getDerecha() == extremo; //La ficha puede colocarse si alguno de sus lados coincide con el extremo derecho de la mesa.
    }

    public void ponerInicio(Ficha ficha) {
        if (!estaVacia()) {
            int extremo = getExtremoIzquierdo();
            if (ficha.getIzquierda() == extremo) {
                ficha.girar(); //Gira la ficha para que el lado derecho quede hacia afuera.
            }
        }
        fichasMesa.addFirst(ficha); //Agrega la ficha al inicio de la lista.
    }

    public void ponerFinal(Ficha ficha) {
        if (!estaVacia()) {
            int extremo = getExtremoDerecho();
            if (ficha.getDerecha() == extremo) {
                ficha.girar(); //Gira la ficha para que el lado izquierdo quede hacia afuera.
            }
        }
        fichasMesa.addLast(ficha); //Agrega la ficha al final de la lista.
    }
    
    public int nFichasX(int numero) {//Para comprobación del cierre
        int n = 0;
        for (int i = 0; i < fichasMesa.size(); i++) {
        Ficha f = fichasMesa.get(i);
        //Comparar ficha por ambos lados
         if (f.getIzquierda() == numero || f.getDerecha() == numero) {
                    n++;//aumenta el contador, que si llega a siete significa bloqueo de partida
          }
         }
            
        return n;
    }

    @Override
    public String toString() {
        String resultado = "";
        for (Ficha ficha : fichasMesa) {
            resultado += ficha;
        }
        return resultado;
        
    }
}
