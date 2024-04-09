    package EditorTexto;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.io.*;
    import java.util.stream.Collectors;

    import BusquedaPalabras_AgendaContactos.AgendaContactos;
    import BusquedaPalabras_AgendaContactos.BuscadorPalabras;
    import BusquedaPalabras_AgendaContactos.Contactos;
    import InterfazGraficaAvanzada.BarraDesplazamiento;
    import InterfazGraficaAvanzada.MultiplicidadVentanas;
    import Validacion.HerramientaDibujo;
    import Validacion.ValidadorEmail;

    public class CreacionAlmacenamiento extends JFrame {
        private JTextArea textArea;
        private JButton saveButton;
        private JButton countContentButton;
        private JButton compareContentButton;
        private JButton exitButton;
        private JButton contactosButton;
        private JButton buscarPalabrasButton;
        private JButton multiplicityButton;
        private File directory;
        private AgendaContactos agendaContactos;
        private JLabel labelX;
        private JLabel labelY;
        private BarraDesplazamiento barraDesplazamiento;

        public CreacionAlmacenamiento() {
            setTitle("Editor de Texto");
            setSize(500, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Panel principal
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(Box.createVerticalGlue());
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Componentes existentes
            textArea = new JTextArea();
            saveButton = new JButton("Guardar");
            countContentButton = new JButton("Contar contenido");
            compareContentButton = new JButton("Comparar contenido");
            exitButton = new JButton("Salir");
            contactosButton = new JButton("Agenda de Contactos");
            buscarPalabrasButton = new JButton("Buscador de Palabras");

            // Panel de botones
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(countContentButton);
            buttonPanel.add(compareContentButton);
            buttonPanel.add(exitButton);
            buttonPanel.add(contactosButton);
            buttonPanel.add(buscarPalabrasButton);

            mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Botón de Multiplicidad de Ventanas
            multiplicityButton = new JButton("Multiplicidad de Ventanas");
            buttonPanel.add(multiplicityButton);

            setLayout(new BorderLayout());
            add(mainPanel, BorderLayout.NORTH);

            directory = new File(".");
            agendaContactos = new AgendaContactos();
            solicitarDatosUsuario();
            JOptionPane.showMessageDialog(this, "Bienvenido al editor de texto!");
            showMainMenu();

            // Crear una instancia de BarraDesplazamiento
            barraDesplazamiento = new BarraDesplazamiento();

            // Reemplazar el JTextArea existente con el JTextArea de BarraDesplazamiento
            textArea = barraDesplazamiento.getAreaDeTexto();

            // Agrega un KeyListener al JFrame
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    // Si la tecla presionada es Escape, no hagas nada
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        e.consume();
                    }
                }
            });
            setFocusable(true);

            // ActionListener para el botón de Salir
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selection = JOptionPane.showConfirmDialog(CreacionAlmacenamiento.this, "¿Estás seguro de que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
                    if (selection == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            // Seguimiento del ratón
            labelX = new JLabel("X: ");
            labelY = new JLabel("Y: ");
            mainPanel.add(labelX);
            mainPanel.add(labelY);
            mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    labelX.setText("X: " + e.getX());
                    labelY.setText("Y: " + e.getY());
                }
            });



            multiplicityButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create a new instance of MultiplicidadVentanas each time the button is pressed
                    new MultiplicidadVentanas().setVisible(true);
                }
            });

            // ActionListener para el botón de Guardar
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveTextToFile();
                }
            });

            // ActionListener para el botón de Contar contenido
            countContentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    countContent();
                }
            });

            // ActionListener para el botón de Comparar contenido
            compareContentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    compareContent();
                }
            });

        // ActionListener para el botón de Salir
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selection = JOptionPane.showConfirmDialog(CreacionAlmacenamiento.this, "¿Estás seguro de que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
                    if (selection == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });


            // ActionListener para el botón de Agenda de Contactos
            contactosButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarAgendaContactos();
                }
            });

            // ActionListener para el botón de Buscador de Palabras
            buscarPalabrasButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buscarPalabras();
                }
            });

            // Seguimiento del ratón
            labelX = new JLabel("X: ");
            labelY = new JLabel("Y: ");
            mainPanel.add(labelX);
            mainPanel.add(labelY);
            mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    labelX.setText("X: " + e.getX());
                    labelY.setText("Y: " + e.getY());
                }
            });
        }

        private void showMainMenu() {
            Object[] options = {"Guardar texto", "Contar contenido", "Comparar contenido", "Agenda de Contactos",
                    "Buscador de Palabras", "Multiplicidad de Ventanas", "Herramienta de Dibujo", "Navegación y Listado", "Salir"};
            int selection = JOptionPane.showOptionDialog(this, "¿Qué desea hacer?", "Menú principal",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selection == 0) {
                // No solicitar los datos del usuario aquí
                // solicitarDatosUsuario();
                saveTextToFile();
            } else if (selection == 1) {
                countContent();
            } else if (selection == 2) {
                compareContent();
            } else if (selection == 3) {
                mostrarAgendaContactos();
            } else if (selection == 4) {
                buscarPalabras();
            } else if (selection == 5) {
                showMultiplicityOptions();
            } else if (selection == 6) {
                showDrawingTool();
            } else if (selection == 7) {
                // Crear y mostrar la ventana de Navegación y Listado
                NavegacionListado navegacionListado = new NavegacionListado();
                navegacionListado.setVisible(true);
            } else if (selection == 8) {
                System.exit(0);
            }
        }
        private void showMultiplicityOptions() {
            new MultiplicidadVentanas().setVisible(true);
        }

        private void saveTextToFile() {
            JTextArea textArea = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(this, scrollPane, "Ingrese el texto que desea guardar:", JOptionPane.PLAIN_MESSAGE);
            Object[] options = {"Archivo nuevo", "Archivo existente"};
            int selection = JOptionPane.showOptionDialog(this, "¿Desea guardar el texto en un archivo nuevo o en uno existente?",
                    "Guardar texto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            String fileName;
            if (selection == 0) {
                fileName = JOptionPane.showInputDialog(this, "Ingrese el nombre del archivo:");
                if (fileName != null) {
                    fileName += ".txt";
                }
            } else {
                File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".txt"));
                if (files != null) {
                    String[] fileNames = new String[files.length];
                    for (int i = 0; i < files.length; i++) {
                        fileNames[i] = files[i].getName();
                    }
                    fileName = (String) JOptionPane.showInputDialog(this, "Seleccione un archivo:", "Guardar en archivo existente",
                            JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);
                } else {
                    return;
                }
            }
            if (fileName != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(textArea.getText());
                    JOptionPane.showMessageDialog(this, "Texto guardado con éxito!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar el texto.");
                }
            }
            // Preguntar si desea volver al menú principal
            Object[] returnOptions = {"Volver al menú principal", "Salir"};
            int returnSelection = JOptionPane.showOptionDialog(this, "¿Qué desea hacer?", "Volver al menú principal",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, returnOptions, returnOptions[0]);
            if (returnSelection == 0) {
                showMainMenu();
            } else {
                System.exit(0);
            }
        }

        private void countContent() {
            File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                String[] fileNames = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                String fileName = (String) JOptionPane.showInputDialog(this, "Seleccione un archivo para contar su contenido:",
                        "Contar contenido", JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);
                if (fileName != null) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        int lines = content.toString().split("\n").length;
                        int words = content.toString().split("\\s+").length;
                        int characters = content.toString().length();
                        String statistics = "Estadísticas del archivo " + fileName + ":\n" + "Contenido:\n" + content.toString() + "\n\n" +
                                "Líneas: " + lines + "\n" + "Palabras: " + words + "\n" + "Caracteres: " + characters;
                        JOptionPane.showMessageDialog(this, statistics);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al leer el archivo.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos .txt.");
            }
            // Vuelve al menú principal
            showMainMenu();
        }

        private void mostrarMensajeBienvenida() {
            // Create a JDialog
            JDialog dialog = new JDialog(this, "Bienvenida", true);

            // Create a JLabel for the welcome message
            JLabel messageLabel = new JLabel("Bienvenida al programa de gestor de sistema de aplicaciones");
            messageLabel.setHorizontalAlignment(JLabel.CENTER);

            // Create a JLabel for the background image
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            // Load the image
            ImageIcon imageIcon = new ImageIcon("C:/Users/ghern/IdeaProjects/CasoFinalIntegrador_4/src/main/java/Imagen de WhatsApp 2024-04-09 a las 21.06.22_379a5401.jpg"); // Replace this with the path to your image
            imageLabel.setIcon(imageIcon);

            // Add the JLabels to the JDialog
            dialog.setLayout(new BorderLayout());
            dialog.add(messageLabel, BorderLayout.NORTH);
            dialog.add(imageLabel, BorderLayout.CENTER);

            // Configure the size of the JDialog and make it visible
            dialog.setSize(300, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        private void compareContent() {
            File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                String[] fileNames = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                String fileName1 = (String) JOptionPane.showInputDialog(this, "Seleccione el primer archivo para comparar:",
                        "Comparar contenido", JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);
                String fileName2 = (String) JOptionPane.showInputDialog(this, "Seleccione el segundo archivo para comparar:",
                        "Comparar contenido", JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);
                if (fileName1 != null && fileName2 != null) {
                    try (BufferedReader reader1 = new BufferedReader(new FileReader(fileName1));
                         BufferedReader reader2 = new BufferedReader(new FileReader(fileName2))) {
                        String content1 = reader1.lines().collect(Collectors.joining("\n"));
                        String content2 = reader2.lines().collect(Collectors.joining("\n"));
                        if (content1.equals(content2)) {
                            JOptionPane.showMessageDialog(this, "Los archivos " + fileName1 + " y " + fileName2 + " son iguales.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Los archivos " + fileName1 + " y " + fileName2 + " son diferentes.");
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al leer los archivos.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos .txt.");
            }
            // Vuelve al menú principal
            showMainMenu();
        }

        private void solicitarDatosUsuario() {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
            String email = "";
            boolean emailValido = false;
            while (!emailValido) {
                email = JOptionPane.showInputDialog(this, "Ingrese su e-mail:");
                ValidadorEmail validadorEmail = new ValidadorEmail();
                emailValido = validadorEmail.esEmailValido(email);
                if (!emailValido) {
                    JOptionPane.showMessageDialog(this, "El correo electrónico ingresado no es válido. Por favor, ingrese un correo electrónico válido.");
                }
            }
            String telefono = JOptionPane.showInputDialog(this, "Ingrese su número de teléfono:");
            agendaContactos.agregarContacto(nombre, email, telefono);
        }

        private void mostrarAgendaContactos() {
            Object[] options = {"Ver contactos", "Guardar nuevo contacto", "Volver al menú principal"};
            int selection = JOptionPane.showOptionDialog(this, "¿Qué desea hacer?", "Agenda de Contactos",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selection == 0) {
                StringBuilder contactos = new StringBuilder();
                for (Contactos contacto : agendaContactos.getContactos().values()) {
                    contactos.append("Nombre: ").append(contacto.getNombre()).append("\n")
                            .append("Email: ").append(contacto.getEmail()).append("\n")
                            .append("Teléfono: ").append(contacto.getTelefono()).append("\n\n");
                }
                JOptionPane.showMessageDialog(this, contactos.toString());
                showMainMenu(); // Mostrar el menú principal después de ver los contactos
            } else if (selection == 1) {
                solicitarDatosUsuario();
            } else if (selection == 2) {
                showMainMenu();
            }
        }

        private void buscarPalabras() {
            File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                String[] fileNames = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                String fileName = (String) JOptionPane.showInputDialog(this, "Seleccione un archivo para buscar palabras:",
                        "Buscador de Palabras", JOptionPane.PLAIN_MESSAGE, null, fileNames, fileNames[0]);
                String palabra = JOptionPane.showInputDialog(this, "Ingrese la palabra que desea buscar:");
                if (fileName != null && palabra != null) {
                    try {
                        BuscadorPalabras buscador = new BuscadorPalabras();
                        BuscadorPalabras.ResultadoBusqueda resultado = buscador.buscarPalabraEnArchivo(fileName, palabra);
                        JOptionPane.showMessageDialog(this, "La palabra '" + palabra + "' aparece " +
                                resultado.getCantidad() + " veces en las líneas " + resultado.getLineas());
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error al buscar la palabra.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos .txt.");
            }
            // Vuelve al menú principal
            showMainMenu();
        }

        private void showDrawingTool() {
            // Cerrar la ventana actual
            this.dispose();

            // Crear y mostrar la herramienta de dibujo
            HerramientaDibujo herramientaDibujo = new HerramientaDibujo();
            JFrame frame = new JFrame("Herramienta de Dibujo");
            frame.setSize(600, 600);
            frame.add(herramientaDibujo);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Solicitar los datos del usuario al inicio de la aplicación
                    CreacionAlmacenamiento creacionAlmacenamiento = new CreacionAlmacenamiento();
                    creacionAlmacenamiento.solicitarDatosUsuario();

                    // No mostrar el editor de texto automáticamente
                    // creacionAlmacenamiento.setVisible(true);

                    // Mostrar el menú principal directamente
                    creacionAlmacenamiento.showMainMenu();

                    ValidadorEmail validadorEmail = new ValidadorEmail();
                    validadorEmail.setVisible(true);

                    JFrame frame = new JFrame("Herramienta de Dibujo");
                    frame.setSize(600, 600);
                    frame.add(new HerramientaDibujo());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
        }
    }