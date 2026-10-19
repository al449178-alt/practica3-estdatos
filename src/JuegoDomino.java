
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class JuegoDomino {
    private final Stack<Ficha> pozo;
    private final Deque<Ficha> cadena;
    private final Jugador[] jugadores;
    private final int NJUGADORES;
    private final int NFICHAS;
    private int turno;

    public JuegoDomino (String nombresJugadores[], int nFichas) {
       if(nombresJugadores.length<2 || nombresJugadores.length>4 || nombresJugadores==null) {
           throw new IllegalArgumentException();
       }
       this.NFICHAS=nFichas;
       this.NJUGADORES= nombresJugadores.length;
       this.jugadores= new Jugador[nombresJugadores.length];
       int i=0;
       for (String n: nombresJugadores){
           Jugador nuevo = new Jugador(n);
           jugadores[i]=nuevo;
           i++;
       }
       this.cadena= new LinkedList<>();
       this.pozo= new Stack<>();
    }


    public JuegoDomino (String nombresJugadores[]) {
        this(nombresJugadores, 7);
    }

    public void prepararPartida () {
        crearYMezclarFichas();
        repartirFichasIniciales();
        turno=0;
    }
    public void crearYMezclarFichas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pozo.add(new Ficha(i, j));
            }
        }
        Collections.shuffle(pozo);
    }

    private void repartirFichasIniciales() {
        for (Jugador j: jugadores){
            for (int p=0; p<NFICHAS; p++){
                j.recibirFicha(pozo.pop());
            }
        }
    }

    public void jugar() {
        System.out.println("--- ESTADO INICIAL ---");
        for (int j=0; j<NJUGADORES; j++)
            System.out.println(jugadores[j]);

        System.out.println("Fichas en pozo (durmientes): " + pozo.size());
        System.out.println("----------------------\n");

        // Jugador 1 empieza colocando su primera ficha boca arriba
        Ficha primeraFicha = jugadores[turno].getFichas().poll();
        cadena.add(primeraFicha);
        System.out.println("\n--- TURNO DE " + jugadores[turno].getNombre().toUpperCase() + " ---");
        System.out.println(jugadores[turno].getNombre() + " inicia la cadena con la ficha " + primeraFicha);
        mostrarCadena();

        this.turno = (this.turno+1)%this.NJUGADORES; //pasa al siguiente jugador
        Jugador actual = this.jugadores[turno];
        int pasesConsecutivos = 0;

        boolean finJuego = false;
        while (!finJuego) {
            System.out.println("\n--- TURNO DE " + actual.getNombre().toUpperCase() + " ---");
            boolean colocoFicha = jugarTurno(actual);

            if (!actual.tieneFichas()) {
                System.out.println("\n¡" + actual.getNombre() + " se ha quedado sin fichas!");
                finJuego = true;
            }
            else {
               if (colocoFicha) {
                  pasesConsecutivos = 0;
               } else {
                   pasesConsecutivos++;
                   System.out.println(actual.getNombre() + " NO ha podido colocar ninguna ficha y pasa.");
                   if (pasesConsecutivos == 2) {
                       System.out.println("\n¡Ambos jugadores han pasado consecutivamente! El juego está cerrado.");
                       finJuego = true;
                   }
               }
            }

            mostrarCadena();

            if (!finJuego) {
                // Cambiar turno
                this.turno= (this.turno+1)%NJUGADORES;
                actual = this.jugadores[turno];
            }
        }
        determinarGanador();
    }

    public Jugador getJugadorActual(){
        return jugadores[turno];
    }

   
    public boolean jugarTurno(Jugador jugador) {
       return false;
    }

    public boolean intentarColocar(Ficha f) {
        if((cadena.getFirst().getLadoA()== f.getLadoB() || cadena.getFirst().getLadoA()==f.getLadoA()) && (cadena.getFirst().getLadoB()== f.getLadoB() || cadena.getFirst().getLadoB()==f.getLadoA())){
            if (cadena.getFirst().getLadoA() < cadena.getFirst().getLadoB()){
                if (f.getLadoB()==cadena.getFirst().getLadoA()){
                    colocarEnIzquierda(f, cadena.getFirst().getLadoA());
                    return true;
                }
                colocarEnDerecha(f, cadena.getFirst().getLadoB());
                return true;
            }

        }
        return false;
    }

    public void colocarEnIzquierda(Ficha f, int valorExtremo) {
        if (f.getLadoB() == valorExtremo) {
            cadena.addFirst(f);
        } else {
            cadena.addFirst(f.invertir());
        }
    }

    public void colocarEnDerecha(Ficha f, int valorExtremo) {
        if (f.getLadoA() == valorExtremo) {
            cadena.addLast(f);
        } else {
            cadena.addLast(f.invertir());
        }
    }

    public void mostrarCadena() {
        StringBuilder sb = new StringBuilder("CADENA ACTUAL: ");
        for (Ficha f : cadena) {
            sb.append(f.toString()).append(" ");
        }
        System.out.println(sb.toString());
    }

    public Jugador determinarGanador() {
       
        return null;
    }

    public Jugador[] getJugadores() {
        return this.jugadores;
    }
    public int fichasEnPozo() {
        return this.pozo.size();
    }
    public Object[] getCadena() {
        return this.cadena.toArray();
    }
}
