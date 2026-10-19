
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String nombres [] = new String[2];
        nombres[0] ="John";
        nombres[1] = "Jane";
        JuegoDomino juego = new JuegoDomino(nombres, 7);
        boolean seguir = true;
        while (seguir) {
            juego.prepararPartida();
            juego.jugar();

            System.out.println();
            System.out.println("Otra partida? (s/n)");
            char letra = scanner.next().charAt(0);
            seguir = (letra == 's' || letra == 'S');
        }
    }
}