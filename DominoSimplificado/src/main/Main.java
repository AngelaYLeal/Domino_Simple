public class Main {
    public static void main(String[] args) {
        //Mensaje de bienvenida
        System.out.println("Iniciando el juego de dominó...");
        /* PRUEBA 1 (Clase Ficha)
        //Crear fichas
        Ficha ficha = new Ficha(3,6);

        //Mostrar ficha
        System.out.println("Ficha creada: " + ficha);

        //Girar ficha
        ficha.girar();
        System.out.println("Ficha después de girar: " + ficha);
        */

        /* PRUEBA 2 (Clase Monton y Ficha)
        Monton monton = new Monton();

        System.out.println("Cantidad inicial: " + monton.cantidadFichas());

        Ficha robada = monton.robarFicha();

        System.out.println("Ficha robada: " + robada);

        System.out.println("Cantidad restante: " + monton.cantidadFichas());
        */

        /*PRUEBA 3 (Clase Jugador, Monton y Ficha)
        Monton monton = new Monton();

        Jugador jugador = new Jugador("Jugador 1");

        for (int i = 0; i < 7; i++) {
            jugador.robarFicha(monton);
        }
        jugador.mostrarMano();

        System.out.println("Puntos: " + jugador.calcularPuntos());
        */

         /*PRUEBA 4 (Clase Mesa, Jugador, Ficha)
        Mesa mesa = new Mesa();

        Ficha f1 = new Ficha(6,6);
        Ficha f2 = new Ficha(3,6);
        Ficha f3 = new Ficha(5,3);

        mesa.ponerInicio(f1);
        mesa.ponerInicio(f2);
        mesa.ponerInicio(f3);
        System.out.println("Mesa: " + mesa); */

        Partida partida = new Partida();
    
        partida.iniciarPartida();
    }
}