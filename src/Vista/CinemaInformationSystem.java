package Vista;

import Controlador.CinemaController;
import Modelo.Cinema;
import Modelo.Movie;
import Modelo.MovieSchedule;
import Modelo.Pricing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Clase principal de la aplicación con GUI
public class CinemaInformationSystem extends JFrame {
    private List<Cinema> cinemas;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private CinemaController controller;

    public CinemaInformationSystem() {
        cinemas = new ArrayList<>();
        loadDataFromFile();

        setTitle("Sistema de Información de Cines");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        controller = new CinemaController(cinemas, tableModel);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de búsqueda
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
        JButton addMovieButton = new JButton("Agregar Película"); // Nuevo botón para agregar película

        searchPanel.add(new JLabel("Tipo de Búsqueda:"));
        searchPanel.add(searchType);
        searchPanel.add(new JLabel("Término de Búsqueda:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(addCinemaButton); // Agregar botón para agregar cine
        searchPanel.add(addMovieButton); // Agregar botón para agregar película

        // Tabla de resultados
        tableModel = new DefaultTableModel(new Object[]{"Cine", "Película", "Género", "Clasificación", "Horarios", "Precios"}, 0);
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar Datos");
        JButton exitButton = new JButton("Salir");

        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);

        // Agregar componentes al panel principal
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar listeners de acción
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            String selectedSearch = (String) searchType.getSelectedItem();
            controller.performSearch(selectedSearch, searchText);
        });

        showAllButton.addActionListener(e -> controller.showAllCinemas());

        addCinemaButton.addActionListener(e -> openAddCinemaDialog()); // Abrir diálogo para agregar cine

        addMovieButton.addActionListener(e -> openAddMovieDialog()); // Abrir diálogo para agregar película

        saveButton.addActionListener(e -> saveDataToFile());

        exitButton.addActionListener(e -> System.exit(0));

        add(mainPanel);
    }

    private void openAddCinemaDialog() {
        // Crear un diálogo para agregar un nuevo cine
        JDialog dialog = new JDialog(this, "Agregar Cine", true);
        dialog.setLayout(new GridLayout(0, 2));

        // Campos para ingresar datos
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
            // Lógica para agregar el cine
            String cinemaName = nameField.getText();
            String cinemaAddress = addressField.getText();
            String cinemaPhone = phoneField.getText();

            // Crear objeto Cinema y agregar a la lista
            Cinema newCinema = new Cinema(cinemaName, cinemaAddress, cinemaPhone);
            cinemas.add(newCinema);

            // Actualizar la tabla
            controller.showAllCinemas();

            // Guardar el nuevo cine en el archivo
            saveDataToFile();

            dialog.dispose(); // Cerrar el diálogo
        });

        dialog.add(addButton);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openAddMovieDialog() {
    // Crear un diálogo para agregar una nueva película
    JDialog dialog = new JDialog(this, "Agregar Película", true);
    dialog.setLayout(new GridLayout(0, 2));

    // Campos para ingresar datos
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

    // JComboBox para seleccionar el cine
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
    dialog.add(new JLabel("Horarios (separados por comas):"));
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
        // Lógica para agregar la película
        String title = titleField.getText();
        String director = directorField.getText();
        String[] actors = actorsField.getText().split(",");
        String genre = genreField.getText();
        String rating = ratingField.getText();
        String[] showTimes = showTimesField.getText().split(",");
        double standardPrice = Double.parseDouble(standardPriceField.getText());
        double seniorPrice = Double.parseDouble(seniorPriceField.getText());
        double studentPrice = Double.parseDouble(studentPriceField.getText());
        double holidayPrice = Double.parseDouble(holidayPriceField.getText());

        // Obtener el cine seleccionado
        Cinema selectedCinema = (Cinema) cinemaComboBox.getSelectedItem();

        // Crear objeto Movie y agregar a la lista del cine correspondiente
        Movie newMovie = new Movie(title, director, List.of(actors), genre, rating);
        Pricing pricing = new Pricing(standardPrice, seniorPrice, studentPrice, holidayPrice);
        MovieSchedule newSchedule = new MovieSchedule(newMovie, List.of(showTimes), pricing);

        // Agregar la película al cine seleccionado
        if (selectedCinema != null) {
            selectedCinema.addSchedule(newSchedule);
        }

        // Actualizar la tabla
        controller.showAllCinemas();

        // Guardar la nueva película en el archivo
        saveDataToFile();

        dialog.dispose(); // Cerrar el diálogo
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
                writer.println("Cinema:" + cinema.getName() + " | " + 
                        cinema.getAddress() + " | " + cinema.getPhone());

                for (MovieSchedule schedule : cinema.getSchedules()) {
                    Movie movie = schedule.getMovie();
                    writer.println("Movie:" + movie.getName() + " | " + 
                            movie.getDirector() + " | " + 
                            String.join(", ", movie.getActors()) + " | " + 
                            movie.getGenre() + " | " + 
                            movie.getRating() + " | " + 
                            String.join(", ", schedule.getShowTimes()) + " | " + 
                            schedule.getPricing().getStandardPrice() + " | " + 
                            schedule.getPricing().getSeniorPrice() + " | " + 
                            schedule.getPricing().getStudentPrice() + " | " + 
                            schedule.getPricing().getHolidayPrice());
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
        // Cine de ejemplo 1
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

        // Cine de ejemplo 2
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
