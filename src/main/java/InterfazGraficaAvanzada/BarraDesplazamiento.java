package InterfazGraficaAvanzada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


public class BarraDesplazamiento extends JFrame {
    private JTextArea areaDeTexto;
    private JScrollBar barraDeDesplazamiento;
    private JLabel etiquetaProgreso;

    public BarraDesplazamiento() {
        super("Documento con Barra de Desplazamiento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Crear el área de texto y envolverla en un JScrollPane
        areaDeTexto = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(areaDeTexto);

        // Eliminar la barra de desplazamiento horizontal para simplificar
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        // Crear y configurar la barra de desplazamiento vertical como un indicador de progreso
        barraDeDesplazamiento = scrollPane.getVerticalScrollBar();
        barraDeDesplazamiento.setUnitIncrement(16);

        // Crear una etiqueta para mostrar el progreso de lectura
        etiquetaProgreso = new JLabel("Progreso: 0%");
        add(etiquetaProgreso, BorderLayout.SOUTH);

        // Añadir un listener para la barra de desplazamiento que actualice la etiqueta del progreso
        barraDeDesplazamiento.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                actualizarProgreso();
            }
        });

        // Inicializar el contenido del área de texto (ejemplo) CAMBIAR POCO A POCO
        for (int i = 0; i < 100; i++) {
            areaDeTexto.append("Línea " + i + "\n");
        }
    }

    private void actualizarProgreso() {
        int valorMaximo = barraDeDesplazamiento.getMaximum();
        int valorActual = barraDeDesplazamiento.getValue() + barraDeDesplazamiento.getModel().getExtent();

        double progreso = (double) valorActual / valorMaximo * 100;
        etiquetaProgreso.setText(String.format("Progreso: %.2f%%", progreso));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BarraDesplazamiento().setVisible(true));
    }

    public JTextArea getAreaDeTexto() {
        return areaDeTexto;
    }
}

