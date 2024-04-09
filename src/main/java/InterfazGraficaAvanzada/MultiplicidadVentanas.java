package InterfazGraficaAvanzada;

import javax.swing.*;

public class MultiplicidadVentanas extends JDesktopPane {
    private int ventanaCount = 0;

    public MultiplicidadVentanas() {
        // No se necesita lógica adicional en el constructor por ahora
    }

    public void agregarVentana(JInternalFrame ventana) {
        // Asegurarse de que cada ventana tenga una posición única
        ventana.setLocation(ventanaCount * 20, ventanaCount * 20);
        ventanaCount++;

        add(ventana);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        ventana.setVisible(true);
    }
}