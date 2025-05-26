package Vista;

import Controlador.CinemaController;
import Modelo.Cinema;
import Modelo.Movie;
import Modelo.MovieSchedule;
import Modelo.Pricing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class CinemaInformationSystem extends JFrame {

    private List<Cinema> cinemas;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private CinemaController controller;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public CinemaInformationSystem() {
        cinemas = new ArrayList<>();
        loadDataFromFile();

        setTitle("Sistema de Información de Cines");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createLoginPanel(); // Crear panel de inicio de sesión
    }

    private void createLoginPanel() {
        JPanel loginMainPanel = new JPanel(new BorderLayout(10, 10));
        loginMainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        loginMainPanel.setBackground(new Color(240, 240, 240));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(new Color(240, 240, 240));
        try {
            ImageIcon logoIcon = new ImageIcon("logo_universidad.png");
            Image scaledImage = logoIcon.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoPanel.add(logoLabel);

            logoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        } catch (Exception e) {
            JLabel placeholder = new JLabel("UNIVERSIDAD XYZ");
            placeholder.setFont(new Font("Arial", Font.BOLD, 20));
            placeholder.setForeground(new Color(70, 130, 180));
            logoPanel.add(placeholder);
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(70, 130, 180)),
                        " INICIAR SESIÓN ",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(70, 130, 180)
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(userLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        loginButton.addActionListener(e -> validateLogin());
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(60, 110, 160));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(70, 130, 180));
            }
        });
        formPanel.add(loginButton, gbc);

        loginMainPanel.add(logoPanel, BorderLayout.NORTH);
        loginMainPanel.add(formPanel, BorderLayout.CENTER);

        add(loginMainPanel);
        revalidate();
        repaint();
    }

    private void validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.equals("admin") && password.equals("1234")) {
            removeLoginPanel(); // Eliminar el panel de inicio de sesión
            showCaseDescription(); // Mostrar la descripción del caso
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeLoginPanel() {
        for (Component comp : getContentPane().getComponents()) {
            remove(comp);
        }
    }

    private void showCaseDescription() {
        JPanel caseDescriptionPanel = new JPanel(new BorderLayout(10, 10));
        caseDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        caseDescriptionPanel.setBackground(new Color(240, 240, 240));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180)); // Color azul acero
        JLabel titleLabel = new JLabel("SISTEMA DE GESTIÓN DE CINES", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        infoPanel.setBackground(Color.WHITE);

        JLabel projectInfo = new JLabel("<html><div style='text-align:center;'><b>PROYECTO DESARROLLADO POR:</b><br>"
                + "James Andres Cespedes Ibarra<br><br>"
                + "Juan Sebastian Miranda Mejia<br><br>"
                + "<b>PARA LA ASIGNATURA:</b><br>"
                + "Tecnicas De Programacion II<br><br>"
                + "<b>VERSIÓN:</b> 1.0.0</div></html>");
        projectInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        projectInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(projectInfo);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio

        // Área de texto con descripción
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("DESCRIPCIÓN DEL PROYECTO:\n\n"
                + "La asociación de cines de una ciudad requiere un sistema para gestionar información\n"
                + "sobre las películas que se proyectan actualmente. El sistema permitirá:\n\n"
                + "• Consultar en qué cines se proyecta una película específica y sus horarios\n"
                + "• Buscar películas animadas disponibles\n"
                + "• Ver la cartelera completa de un cine específico\n"
                + "• Filtrar películas por género o clasificación\n\n"
                + "CARACTERÍSTICAS TÉCNICAS:\n\n"
                + "• Base de datos relacional para almacenar toda la información\n"
                + "• Interfaz gráfica intuitiva para administradores\n"
                + "• Sistema de autenticación seguro\n"
                + "• Persistencia de datos en archivo local");
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setBackground(Color.WHITE);

        descriptionArea.setMargin(new Insets(10, 50, 10, 50));
        ((DefaultCaret) descriptionArea.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        JButton nextButton = new JButton("Continuar");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(70, 130, 180));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        nextButton.addActionListener(e -> {
            remove(caseDescriptionPanel);
            showDeveloperInfo();
        });
        buttonPanel.add(nextButton);

        caseDescriptionPanel.add(titlePanel, BorderLayout.NORTH);
        caseDescriptionPanel.add(infoPanel, BorderLayout.CENTER);
        caseDescriptionPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(caseDescriptionPanel);
        revalidate();
        repaint();
    }

    private void showDeveloperInfo() {
        JPanel developerInfoPanel = new JPanel(new BorderLayout(10, 10));
        developerInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        developerInfoPanel.setBackground(new Color(240, 240, 240));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(new Color(240, 240, 240));
        try {
            ImageIcon logoIcon = new ImageIcon("logo_universidad.png");
            Image scaledImage = logoIcon.getImage().getScaledInstance(180, -1, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoPanel.add(logoLabel);
            logoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        } catch (Exception e) {
            JLabel logoPlaceholder = new JLabel("KONRAD LORENZ");
            logoPlaceholder.setFont(new Font("Arial", Font.BOLD, 18));
            logoPlaceholder.setForeground(new Color(0, 76, 153)); // Azul institucional
            logoPanel.add(logoPlaceholder);
        }

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 76, 153)),
                        " INFORMACIÓN DEL EQUIPO ",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(0, 76, 153)
                ),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        infoPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addInfoLine(infoPanel, gbc, "Desarollador 1 - James Andres Cespedes Ibarra");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, "Desarollador 2 - Juan Sebastian Miranda Mejia");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, " ");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, "Materia - Técnicas de Programación II");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, " ");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, "Fundación Universitaria Konrad Lorenz");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, " ");
        gbc.gridy++;
        addInfoLine(infoPanel, gbc, " Periodo Academico - 2025-1");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton startAppButton = new JButton("Iniciar Aplicación");
        startAppButton.setFont(new Font("Arial", Font.BOLD, 14));
        startAppButton.setBackground(new Color(0, 76, 153));
        startAppButton.setForeground(Color.WHITE);
        startAppButton.setFocusPainted(false);
        startAppButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(8, 30, 8, 30)
        ));
        startAppButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startAppButton.setBackground(new Color(0, 56, 133));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startAppButton.setBackground(new Color(0, 76, 153));
            }
        });
        startAppButton.addActionListener(e -> {
            remove(developerInfoPanel);
            createUI();
        });
        buttonPanel.add(startAppButton);

        developerInfoPanel.add(logoPanel, BorderLayout.NORTH);
        developerInfoPanel.add(infoPanel, BorderLayout.CENTER);
        developerInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(developerInfoPanel);
        revalidate();
        repaint();
    }

    private void addInfoLine(JPanel panel, GridBagConstraints gbc, String text) {
        JLabel label = new JLabel(text);
        if (text.startsWith("•") || text.equals(" ")) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
        } else {
            label.setFont(new Font("Arial", Font.BOLD, 13));
        }
        panel.add(label, gbc);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Opciones de Búsqueda"));

        JComboBox<String> searchType = new JComboBox<>(new String[]{
            "Todas las Películas", "Películas por Cine", "Películas por Título",
            "Películas por Género", "Películas por Clasificación", "Películas Animadas"
        });
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Buscar");
        JButton showAllButton = new JButton("Mostrar Todos los Cines");
        JButton addCinemaButton = new JButton("Agregar Cine");
        JButton addMovieButton = new JButton("Agregar Película");

        searchPanel.add(new JLabel("Tipo de Búsqueda:"));
        searchPanel.add(searchType);
        searchPanel.add(new JLabel("Término de Búsqueda:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(addCinemaButton);
        searchPanel.add(addMovieButton);

        tableModel = new DefaultTableModel(new Object[]{"Cine", "Película", "Género", "Clasificación", "Horarios", "Precios"}, 0);
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar Datos");
        JButton exitButton = new JButton("Salir");

        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            String selectedSearch = (String) searchType.getSelectedItem();
            controller.performSearch(selectedSearch, searchText);
        });

        showAllButton.addActionListener(e -> controller.showAllCinemas());

        addCinemaButton.addActionListener(e -> openAddCinemaDialog());

        addMovieButton.addActionListener(e -> openAddMovieDialog());

        saveButton.addActionListener(e -> saveDataToFile());

        exitButton.addActionListener(e -> System.exit(0));

        add(mainPanel);
        controller = new CinemaController(cinemas, tableModel);
        revalidate();
        repaint();
    }

    private boolean contieneCaracteresEspeciales(String texto) {
        String regex = "^[a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ.,:;¡!¿?\\-]+$";
        return !texto.matches(regex);
    }

    private boolean esTelefonoValido(String telefono) {
        String regex = "^[0-9\\-() ]+$";
        return telefono.matches(regex);
    }

    private boolean esFormatoHoraValido(String hora) {
        String regex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        return hora.trim().matches(regex);
    }

    private void openAddCinemaDialog() {
        JDialog dialog = new JDialog(this, "Agregar Cine", true);
        dialog.setLayout(new GridLayout(0, 2));

        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();

        dialog.add(new JLabel("Nombre del Cine:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Dirección:"));
        dialog.add(addressField);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(phoneField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(e -> {
            String cinemaName = nameField.getText();
            String cinemaAddress = addressField.getText();
            String cinemaPhone = phoneField.getText();

            if (cinemaName.isEmpty() || cinemaAddress.isEmpty() || cinemaPhone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contieneCaracteresEspeciales(cinemaName)) {
                JOptionPane.showMessageDialog(dialog, "El nombre del cine contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contieneCaracteresEspeciales(cinemaAddress)) {
                JOptionPane.showMessageDialog(dialog, "La dirección contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!esTelefonoValido(cinemaPhone)) {
                JOptionPane.showMessageDialog(dialog, "El teléfono solo puede contener números, guiones y paréntesis.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cinema newCinema = new Cinema(cinemaName, cinemaAddress, cinemaPhone);
            cinemas.add(newCinema);
            controller.showAllCinemas();
            saveDataToFile();
            dialog.dispose();
        });

        dialog.add(addButton);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openAddMovieDialog() {
        JDialog dialog = new JDialog(this, "Agregar Película", true);
        dialog.setLayout(new GridLayout(0, 2));

        JTextField titleField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField actorsField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField showTimesField = new JTextField();
        JTextField standardPriceField = new JTextField();
        JTextField seniorPriceField = new JTextField();
        JTextField studentPriceField = new JTextField();
        JTextField holidayPriceField = new JTextField();

        JComboBox<Cinema> cinemaComboBox = new JComboBox<>();
        for (Cinema cinema : cinemas) {
            cinemaComboBox.addItem(cinema);
        }

        dialog.add(new JLabel("Seleccionar Cine:"));
        dialog.add(cinemaComboBox);
        dialog.add(new JLabel("Título de la Película:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Director:"));
        dialog.add(directorField);
        dialog.add(new JLabel("Actores (separados por comas):"));
        dialog.add(actorsField);
        dialog.add(new JLabel("Género:"));
        dialog.add(genreField);
        dialog.add(new JLabel("Clasificación:"));
        dialog.add(ratingField);
        dialog.add(new JLabel("Horarios (HH:MM, separados por comas):"));
        dialog.add(showTimesField);
        dialog.add(new JLabel("Precio Estándar:"));
        dialog.add(standardPriceField);
        dialog.add(new JLabel("Precio Adulto Mayor:"));
        dialog.add(seniorPriceField);
        dialog.add(new JLabel("Precio Estudiante:"));
        dialog.add(studentPriceField);
        dialog.add(new JLabel("Precio Festivo:"));
        dialog.add(holidayPriceField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String director = directorField.getText();
            String[] actors = actorsField.getText().split(",");
            String genre = genreField.getText();
            String rating = ratingField.getText();
            String[] showTimes = showTimesField.getText().split(",");

            if (title.isEmpty() || director.isEmpty() || actorsField.getText().isEmpty()
                    || genre.isEmpty() || rating.isEmpty() || showTimesField.getText().isEmpty()
                    || standardPriceField.getText().isEmpty() || seniorPriceField.getText().isEmpty()
                    || studentPriceField.getText().isEmpty() || holidayPriceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contieneCaracteresEspeciales(title)) {
                JOptionPane.showMessageDialog(dialog, "El título contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contieneCaracteresEspeciales(director)) {
                JOptionPane.showMessageDialog(dialog, "El director contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (String actor : actors) {
                if (contieneCaracteresEspeciales(actor.trim())) {
                    JOptionPane.showMessageDialog(dialog, "Los actores contienen caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (contieneCaracteresEspeciales(genre)) {
                JOptionPane.showMessageDialog(dialog, "El género contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contieneCaracteresEspeciales(rating)) {
                JOptionPane.showMessageDialog(dialog, "La clasificación contiene caracteres no permitidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (String time : showTimes) {
                if (!esFormatoHoraValido(time.trim())) {
                    JOptionPane.showMessageDialog(dialog, "Formato de hora inválido. Use HH:MM (24 horas).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                double standardPrice = Double.parseDouble(standardPriceField.getText());
                double seniorPrice = Double.parseDouble(seniorPriceField.getText());
                double studentPrice = Double.parseDouble(studentPriceField.getText());
                double holidayPrice = Double.parseDouble(holidayPriceField.getText());

                if (standardPrice <= 0 || seniorPrice <= 0 || studentPrice <= 0 || holidayPrice <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Los precios deben ser mayores que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cinema selectedCinema = (Cinema) cinemaComboBox.getSelectedItem();
                Movie newMovie = new Movie(title, director, List.of(actors), genre, rating);
                Pricing pricing = new Pricing(standardPrice, seniorPrice, studentPrice, holidayPrice);
                MovieSchedule newSchedule = new MovieSchedule(newMovie, List.of(showTimes), pricing);

                if (selectedCinema != null) {
                    selectedCinema.addSchedule(newSchedule);
                }

                controller.showAllCinemas();
                saveDataToFile();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, ingrese precios válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(addButton);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadDataFromFile() {
        File file = new File("cinema_data.txt");
        if (!file.exists()) {
            createSampleData();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Cinema currentCinema = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Cinema:")) {
                    String[] parts = line.split("\\|");
                    String name = parts[0].substring(7).trim();
                    String address = parts[1].trim();
                    String phone = parts[2].trim();
                    currentCinema = new Cinema(name, address, phone);
                    cinemas.add(currentCinema);
                } else if (line.startsWith("Movie:") && currentCinema != null) {
                    String[] parts = line.split("\\|");
                    String title = parts[0].substring(6).trim();
                    String director = parts[1].trim();
                    String[] actors = parts[2].split(",");
                    List<String> actorList = new ArrayList<>();
                    for (String actor : actors) {
                        actorList.add(actor.trim());
                    }
                    String genre = parts[3].trim();
                    String rating = parts[4].trim();
                    String[] times = parts[5].split(",");
                    List<String> showTimes = new ArrayList<>();
                    for (String time : times) {
                        showTimes.add(time.trim());
                    }
                    double standardPrice = Double.parseDouble(parts[6].trim());
                    double seniorPrice = Double.parseDouble(parts[7].trim());
                    double studentPrice = Double.parseDouble(parts[8].trim());
                    double holidayPrice = Double.parseDouble(parts[9].trim());

                    Movie movie = new Movie(title, director, actorList, genre, rating);
                    Pricing pricing = new Pricing(standardPrice, seniorPrice, studentPrice, holidayPrice);
                    MovieSchedule schedule = new MovieSchedule(movie, showTimes, pricing);
                    currentCinema.addSchedule(schedule);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            createSampleData();
        }
    }

    private void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cinema_data.txt"))) {
            for (Cinema cinema : cinemas) {
                writer.println("Cinema:" + cinema.getName() + " | "
                        + cinema.getAddress() + " | " + cinema.getPhone());

                for (MovieSchedule schedule : cinema.getSchedules()) {
                    Movie movie = schedule.getMovie();
                    writer.println("Movie:" + movie.getName() + " | "
                            + movie.getDirector() + " | "
                            + String.join(", ", movie.getActors()) + " | "
                            + movie.getGenre() + " | "
                            + movie.getRating() + " | "
                            + String.join(", ", schedule.getShowTimes()) + " | "
                            + schedule.getPricing().getStandardPrice() + " | "
                            + schedule.getPricing().getSeniorPrice() + " | "
                            + schedule.getPricing().getStudentPrice() + " | "
                            + schedule.getPricing().getHolidayPrice());
                }
            }
            JOptionPane.showMessageDialog(this, "¡Datos guardados exitosamente!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createSampleData() {
        Cinema cinema1 = new Cinema("Cineplex Centro", "123 Calle Principal", "555-0101");

        List<String> actors1 = List.of("Tom Hanks", "Emma Watson", "John Doe");
        Movie movie1 = new Movie("La Gran Aventura", "Steven Spielberg", actors1, "Aventura", "PG-13");
        Pricing pricing1 = new Pricing(12.50, 8.50, 9.50, 15.00);
        MovieSchedule schedule1 = new MovieSchedule(movie1, List.of("10:00", "13:30", "18:00", "21:15"), pricing1);
        cinema1.addSchedule(schedule1);

        List<String> actors2 = List.of("Jennifer Lawrence", "Chris Pratt", "Morgan Freeman");
        Movie movie2 = new Movie("Odisea Espacial", "Ridley Scott", actors2, "Ciencia Ficción", "PG-13");
        Pricing pricing2 = new Pricing(13.50, 9.00, 10.00, 16.00);
        MovieSchedule schedule2 = new MovieSchedule(movie2, List.of("11:00", "14:30", "19:00", "22:15"), pricing2);
        cinema1.addSchedule(schedule2);

        List<String> actors3 = List.of("Actor de Voz 1", "Actor de Voz 2", "Actor de Voz 3");
        Movie movie3 = new Movie("Aventuras de Juguetes", "Director de Animación", actors3, "Animación", "G");
        Pricing pricing3 = new Pricing(10.50, 7.50, 8.50, 12.00);
        MovieSchedule schedule3 = new MovieSchedule(movie3, List.of("09:30", "12:00", "15:30"), pricing3);
        cinema1.addSchedule(schedule3);

        Cinema cinema2 = new Cinema("Teatro Starlight", "456 Avenida Elm", "555-0202");

        List<String> actors4 = List.of("Robert Downey Jr.", "Scarlett Johansson", "Mark Ruffalo");
        Movie movie4 = new Movie("Superhéroes Unidos", "Joss Whedon", actors4, "Acción", "PG-13");
        Pricing pricing4 = new Pricing(14.00, 9.50, 10.50, 17.00);
        MovieSchedule schedule4 = new MovieSchedule(movie4, List.of("10:30", "14:00", "17:30", "21:00"), pricing4);
        cinema2.addSchedule(schedule4);

        List<String> actors5 = List.of("Actor de Voz 4", "Actor de Voz 5");
        Movie movie5 = new Movie("El Bosque Mágico", "Director de Animación 2", actors5, "Animada", "PG");
        Pricing pricing5 = new Pricing(11.00, 8.00, 9.00, 13.00);
        MovieSchedule schedule5 = new MovieSchedule(movie5, List.of("10:00", "13:00", "16:00"), pricing5);
        cinema2.addSchedule(schedule5);

        List<String> actors6 = List.of("Leonardo DiCaprio", "Kate Winslet");
        Movie movie6 = new Movie("Misterio en el Océano", "James Cameron", actors6, "Suspenso", "R");
        Pricing pricing6 = new Pricing(12.00, 8.00, 9.00, 14.00);
        MovieSchedule schedule6 = new MovieSchedule(movie6, List.of("19:30", "22:45"), pricing6);
        cinema2.addSchedule(schedule6);

        cinemas.add(cinema1);
        cinemas.add(cinema2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            CinemaInformationSystem app = new CinemaInformationSystem();
            app.setVisible(true);
        });
    }
}
