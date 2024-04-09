package InterfazGraficaAvanzada;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeguimientoRaton extends JFrame {
    private JLabel etiquetaPosicion;

    public SeguimientoRaton() {
        super("Seguimiento del Ratón");
        setLayout(new BorderLayout());

        // Crear el panel que capturará los movimientos del ratón
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 300));
        add(panel, BorderLayout.CENTER);

        // Crear la etiqueta para mostrar la posición del ratón
        etiquetaPosicion = new JLabel("Posición del ratón: ");
        add(etiquetaPosicion, BorderLayout.SOUTH);

        // Añadir listeners para el seguimiento del ratón
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                etiquetaPosicion.setText("Posición del ratón: ");
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                etiquetaPosicion.setText("Posición del ratón: " + e.getX() + ", " + e.getY());
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeguimientoRaton());
    }
}