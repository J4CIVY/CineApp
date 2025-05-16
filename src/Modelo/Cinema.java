package Modelo;

import java.util.ArrayList;
import java.util.List;

// Clase Cine con asociación a Película
public class Cinema extends CinemaEntity {
    private String address;
    private String phone;
    private List<MovieSchedule> schedules;

    public Cinema(String name, String address, String phone) {
        super(name);
        this.address = address;
        this.phone = phone;
        this.schedules = new ArrayList<>();
    }

    public void addSchedule(MovieSchedule schedule) {
        schedules.add(schedule);
    }

    public List<MovieSchedule> getSchedules() {
        return schedules;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
