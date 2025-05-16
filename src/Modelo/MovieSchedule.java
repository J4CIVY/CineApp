package Modelo;

import java.util.List;

// Clase HorarioPelicula que muestra asociación entre Cine y Película
public class MovieSchedule {
    private Movie movie;
    private List<String> showTimes;
    private Pricing pricing;

    public MovieSchedule(Movie movie, List<String> showTimes, Pricing pricing) {
        this.movie = movie;
        this.showTimes = showTimes;
        this.pricing = pricing;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<String> getShowTimes() {
        return showTimes;
    }

    public Pricing getPricing() {
        return pricing;
    }
}
