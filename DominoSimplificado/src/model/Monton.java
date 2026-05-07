package model;
//Importar listas dinámicas.
import java.util.LinkedList;

//Importar herramientas útiles como shuffle(), sort(), etc.
import java.util.Collections;

public class Monton {
    private LinkedList<Ficha> fichas; //Creamos la VARIABLE fichas, que no crea la lista

    public Monton() {
        fichas = new LinkedList<>(); //Ahora creamos la lista (vacía)
        for (int i = 0; i <= 5; i++) {//HASTA 5 PARA NO AÑADIR AL MONTÓN EL 6/6 HASTA BARAJAR
            for (int j = i; j <= 6; j++) {
                fichas.add(new Ficha(i, j)); //Agregamos cada ficha al montón sin repetir
            }
        }

        Collections.shuffle(fichas); //Mezclamos las fichas
        //DESPUÉS DE BARAJAR INTRODUCIMOS EL 6/6 EN LA POSICIÓN 0
        //PARA QUE LA ROBE EL JUGADOR 1 EN SU PRIMERA MANO (forzar a salir el 6/6)
        fichas.add(0,new Ficha(6,6));
    }

    public Ficha robarFicha() {
        if (fichas.isEmpty()) {
            return null; //No hay fichas para robar
        
        }
        return fichas.remove(0); //Robamos la primera ficha del montón
    }
    
    public boolean puedoPasar() {//Permite la condición de pasar
    	if (fichas.isEmpty()) {
            return true;        
        }else return false;
    }

    public int cantidadFichas() {
        return fichas.size(); //Devuelve la cantidad de fichas restantes en el montón
    }

    @Override
    public String toString() {
        return fichas.toString();
    }    
}

