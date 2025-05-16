package Modelo;

import java.util.List;

// Clase Película con encapsulación
public class Movie extends CinemaEntity {
    private String director;
    private List<String> actors;
    private String genre;
    private String rating;

    public Movie(String title, String director, List<String> actors, String genre, String rating) {
        super(title);
        this.director = director;
        this.actors = actors;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters para campos encapsulados
    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return name + " (" + genre + ")";
    }
}
