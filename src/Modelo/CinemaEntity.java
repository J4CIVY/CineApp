package Modelo;

public class CinemaEntity {
    protected String name;

    public CinemaEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
