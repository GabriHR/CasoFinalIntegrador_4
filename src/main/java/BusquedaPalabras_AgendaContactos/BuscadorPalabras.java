package BusquedaPalabras_AgendaContactos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuscadorPalabras {

    public class ResultadoBusqueda {
        private int cantidad;
        private List<Integer> lineas;

        public ResultadoBusqueda() {
            this.cantidad = 0;
            this.lineas = new ArrayList<>();
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public List<Integer> getLineas() {
            return lineas;
        }

        public void setLineas(List<Integer> lineas) {
            this.lineas = lineas;
        }
    }

    public ResultadoBusqueda buscarPalabraEnArchivo(String archivo, String palabra) throws IOException {
        ResultadoBusqueda resultado = new ResultadoBusqueda();
        int count = 0;
        List<Integer> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            int numLinea = 1;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.equals(palabra)) {
                        count++;
                        if (!lineas.contains(numLinea)) {
                            lineas.add(numLinea);
                        }
                    }
                }
                numLinea++;
            }
        }
        resultado.setCantidad(count);
        resultado.setLineas(lineas);
        return resultado;
    }
}