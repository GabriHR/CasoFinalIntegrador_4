package Validacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorEmail extends JFrame {
    private JTextField textField;
    private JLabel validLabel;

    public ValidadorEmail() {
        setTitle("Validador de Email");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField(20);
        validLabel = new JLabel();

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(textField.getText());

                if (matcher.matches()) {
                    validLabel.setText("Email válido");
                    validLabel.setForeground(Color.GREEN);
                } else {
                    validLabel.setText("Email no válido");
                    validLabel.setForeground(Color.RED);
                }
            }
        });

        add(textField, BorderLayout.NORTH);
        add(validLabel, BorderLayout.SOUTH);
    }

    public boolean esEmailValido(String email) {
        String regex = "^[\\w-]+(\\.[\\w-]+)*@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}