package EditorTexto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NavegacionListado extends JFrame {
    private JTextArea textArea;
    private JList<String> fileList;
    private File directory;

    public NavegacionListado() {
        // Configuración de la ventana
        setTitle("Navegación y Listado");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación de componentes
        textArea = new JTextArea();
        fileList = new JList<>();
        directory = new File(".");

        // Configuración del layout
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(new JScrollPane(fileList), BorderLayout.WEST);

        // Cargar la lista de archivos
        loadFileList();

        // Agregar listener a la lista
        fileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadTextFromFile(fileList.getSelectedValue());
                }
            }
        });
    }

    private void loadFileList() {
        File[] files = directory.listFiles();
        if (files != null) {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (File file : files) {
                if (file.isFile()) {
                    model.addElement(file.getName());
                }
            }
            fileList.setModel(model);
        }
    }

    private void loadTextFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            textArea.read(reader, null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el texto.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NavegacionListado().setVisible(true);
            }
        });
    }
}