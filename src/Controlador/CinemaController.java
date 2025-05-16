package Controlador;

import Modelo.Cinema;
import Modelo.Movie;
import Modelo.MovieSchedule;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CinemaController {
    private List<Cinema> cinemas;
    private DefaultTableModel tableModel;

    public CinemaController(List<Cinema> cinemas, DefaultTableModel tableModel) {
        this.cinemas = cinemas;
        this.tableModel = tableModel;
    }

    public void performSearch(String searchType, String searchTerm) {
        tableModel.setRowCount(0); // Limpiar tabla

        switch (searchType) {
            case "Todas las Películas":
                showAllMovies();
                break;
            case "Películas por Cine":
                searchMoviesByCinema(searchTerm);
                break;
            case "Películas por Título":
                searchMoviesByTitle(searchTerm);
                break;
            case "Películas por Género":
                searchMoviesByGenre(searchTerm);
                break;
            case "Películas por Clasificación":
                searchMoviesByRating(searchTerm);
                break;
            case "Películas Animadas":
                searchAnimatedMovies();
                break;
        }
    }

    public void showAllCinemas() {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            tableModel.addRow(new Object[]{
                cinema.getName(),
                "",
                "",
                "",
                "",
                ""
            });
            tableModel.addRow(new Object[]{
                "Dirección: " + cinema.getAddress(),
                "Teléfono: " + cinema.getPhone(),
                "",
                "",
                "",
                ""
            });
            tableModel.addRow(new Object[]{"", "", "", "", "", ""}); // Fila vacía para espaciado
        }
    }

    private void showAllMovies() {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            for (MovieSchedule schedule : cinema.getSchedules()) {
                Movie movie = schedule.getMovie();
                tableModel.addRow(new Object[]{
                    cinema.getName(),
                    movie.getName(),
                    movie.getGenre(),
                    movie.getRating(),
                    String.join(", ", schedule.getShowTimes()),
                    String.format("Estándar: $%.2f, Adulto Mayor: $%.2f, Estudiante: $%.2f, Festivo: $%.2f",
                            schedule.getPricing().getStandardPrice(),
                            schedule.getPricing().getSeniorPrice(),
                            schedule.getPricing().getStudentPrice(),
                            schedule.getPricing().getHolidayPrice())
                });
            }
        }
    }

    private void searchMoviesByCinema(String cinemaName) {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            if (cinema.getName().toLowerCase().contains(cinemaName.toLowerCase())) {
                for (MovieSchedule schedule : cinema.getSchedules()) {
                    Movie movie = schedule.getMovie();
                    tableModel.addRow(new Object[]{
                        cinema.getName(),
                        movie.getName(),
                        movie.getGenre(),
                        movie.getRating(),
                        String.join(", ", schedule.getShowTimes()),
                        String.format("Estándar: $%.2f", schedule.getPricing().getStandardPrice())
                    });
                }
            }
        }
    }

    private void searchMoviesByTitle(String title) {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            for (MovieSchedule schedule : cinema.getSchedules()) {
                Movie movie = schedule.getMovie();
                if (movie.getName().toLowerCase().contains(title.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        cinema.getName(),
                        movie.getName(),
                        movie.getGenre(),
                        movie.getRating(),
                        String.join(", ", schedule.getShowTimes()),
                        String.format("Estándar: $%.2f", schedule.getPricing().getStandardPrice())
                    });
                }
            }
        }
    }

    private void searchMoviesByGenre(String genre) {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            for (MovieSchedule schedule : cinema.getSchedules()) {
                Movie movie = schedule.getMovie();
                if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        cinema.getName(),
                        movie.getName(),
                        movie.getGenre(),
                        movie.getRating(),
                        String.join(", ", schedule.getShowTimes()),
                        String.format("Estándar: $%.2f", schedule.getPricing().getStandardPrice())
                    });
                }
            }
        }
    }

    private void searchMoviesByRating(String rating) {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            for (MovieSchedule schedule : cinema.getSchedules()) {
                Movie movie = schedule.getMovie();
                if (movie.getRating().toLowerCase().contains(rating.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        cinema.getName(),
                        movie.getName(),
                        movie.getGenre(),
                        movie.getRating(),
                        String.join(", ", schedule.getShowTimes()),
                        String.format("Estándar: $%.2f", schedule.getPricing().getStandardPrice())
                    });
                }
            }
        }
    }

    private void searchAnimatedMovies() {
        tableModel.setRowCount(0);
        for (Cinema cinema : cinemas) {
            for (MovieSchedule schedule : cinema.getSchedules()) {
                Movie movie = schedule.getMovie();
                if (movie.getGenre().toLowerCase().contains("animation") || 
                   movie.getGenre().toLowerCase().contains("animated")) {
                    tableModel.addRow(new Object[]{
                        cinema.getName(),
                        movie.getName(),
                        movie.getGenre(),
                        movie.getRating(),
                        String.join(", ", schedule.getShowTimes()),
                        String.format("Estándar: $%.2f", schedule.getPricing().getStandardPrice())
                    });
                }
            }
        }
    }
}
