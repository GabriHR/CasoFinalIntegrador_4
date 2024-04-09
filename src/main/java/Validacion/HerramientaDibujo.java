package Validacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import EditorTexto.CreacionAlmacenamiento;

public class HerramientaDibujo extends JPanel {
    private ArrayList<Point> points = new ArrayList<>();


    public HerramientaDibujo() {
        setBackground(Color.WHITE);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when exit button is clicked
                System.exit(0);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });

        // Botón para volver al menú principal
        JButton mainMenuButton = new JButton("Volver al menú principal");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana actual
                SwingUtilities.getWindowAncestor(HerramientaDibujo.this).dispose();

            }
        });

        // Botón para volver al menú principal
        mainMenuButton = new JButton("Volver al menú principal");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana actual
                SwingUtilities.getWindowAncestor(HerramientaDibujo.this).dispose();

                // No crear una nueva instancia de CreacionAlmacenamiento
                // new CreacionAlmacenamiento().setVisible(true);
            }
        });

        // Añadir los botones al panel
        add(mainMenuButton);
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        for (Point point : points) {
            g2d.fillOval(point.x, point.y, 4, 4);
        }
    }
}