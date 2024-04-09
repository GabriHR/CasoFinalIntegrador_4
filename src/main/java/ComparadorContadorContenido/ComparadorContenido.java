package ComparadorContadorContenido;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ComparadorContenido implements ComparadorInterf, AnalizadorTextoInterf {

    @Override
    public void compararArchivos(String archivo1, String archivo2) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(archivo1));
             BufferedReader reader2 = new BufferedReader(new FileReader(archivo2))) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            boolean areDifferent = false;
            while (line1 != null || line2 != null) {
                if (line1 == null || line2 == null || !line1.equals(line2)) {
                    areDifferent = true;
                    break;
                }
                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }
            if (areDifferent) {
                System.out.println("Los archivos " + archivo1 + " y " + archivo2 + " son diferentes.");
            } else {
                System.out.println("Los archivos " + archivo1 + " y " + archivo2 + " son iguales.");
            }
        }
    }

    @Override
    public void analizarTexto(String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            Map<String, Integer> wordCounts = new HashMap<>();

            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
                line = reader.readLine();
            }

            System.out.println("Número total de palabras: " + wordCounts.values().stream().mapToInt(Integer::intValue).sum());
            System.out.println("Estadísticas de uso de palabras: " + wordCounts);
        }
    }

    public void contarPalabras(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                count += words.length;
            }
            System.out.println("El archivo " + fileName + " contiene " + count + " palabras.");
        }
    }

    public void mostrarPalabrasVariables(String fileName1, String fileName2) throws IOException {
        Map<String, Integer> wordCount1 = getWordCount(fileName1);
        Map<String, Integer> wordCount2 = getWordCount(fileName2);

        System.out.println("Palabras que varían entre " + fileName1 + " y " + fileName2 + ":");
        for (String word : wordCount1.keySet()) {
            if (!wordCount2.containsKey(word)) {
                System.out.println(word + " se encuentra en " + fileName1);
            }
        }
        for (String word : wordCount2.keySet()) {
            if (!wordCount1.containsKey(word)) {
                System.out.println(word + " se encuentra en " + fileName2);
            }
        }
    }

    private Map<String, Integer> getWordCount(String fileName) throws IOException {
        Map<String, Integer> wordCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        return wordCount;
    }
}