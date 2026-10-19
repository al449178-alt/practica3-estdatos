import java.util.LinkedList;
import java.util.Queue;

public class Jugador {
    //parte privada
    private String nombre;
    private Queue<Ficha> fichas;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.fichas = new LinkedList<>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public Queue<Ficha> getFichas() {
        return fichas;
    }

    public void recibirFicha(Ficha f) {
        fichas.add(f);
    }

    public Ficha buscarFicha(int valor1, int valor2) {
        if (!tieneFichas()) {
            return null;
        }
        int total = fichas.size();
        for (int i = 0; i < total; i++) {
            Ficha p = fichas.poll();
            if (p.getLadoA() == valor1 || p.getLadoA() == valor2 ||
                    p.getLadoB() == valor1 || p.getLadoB() == valor2) {
                return p;
            }
            fichas.add(p);
        }
        return null;
    }

    public boolean tieneFichas() {
        return !fichas.isEmpty();
    }

    public int calcularPuntosRestantes() {
        int suma = 0;
        for (Ficha f : fichas) {
            suma += f.getPuntosTotales();
        }
        return suma;
    }

    @Override
    public String toString() {
        String resultado = this.nombre;
        if (!tieneFichas()) {
            return resultado + " no tiene fichas";
        }
        resultado += "[";
        int total = fichas.size();
        int contador = 0;
        for (Ficha f : fichas) {
            resultado += f;
            contador++;
            if (contador < total) {
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }
}