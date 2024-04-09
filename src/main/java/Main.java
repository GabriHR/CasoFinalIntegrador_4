import java.io.IOException;
import ComparadorContadorContenido.ComparadorContenido;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ComparadorContenido cc = new ComparadorContenido();
        cc.compararArchivos("archivo1.txt", "archivo2.txt");
        cc.analizarTexto("archivo1.txt");
    }
}