package model;
//Importar listas dinámicas.
import java.util.ArrayList;

//Importar herramientas útiles como shuffle(), sort(), etc.
import java.util.Collections;

public class Monton {
    private ArrayList<Ficha> fichas; //Creamos la VARIABLE fichas, que no crea la lista

    public Monton() {
        fichas = new ArrayList<>(); //Ahora creamos la lista (vacía)
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                fichas.add(new Ficha(i, j)); //Agregamos cada ficha al montón sin repetir
            }
        }

        Collections.shuffle(fichas); //Mezclamos las fichas
    }

    public Ficha robarFicha() {
        if (fichas.isEmpty()) {
            return null; //No hay fichas para robar
        
        }
        return fichas.remove(0); //Robamos la primera ficha del montón
    }

    public int cantidadFichas() {
        return fichas.size(); //Devuelve la cantidad de fichas restantes en el montón
    }

    @Override
    public String toString() {
        return fichas.toString();
    }    
}

