package tn.esprit.coachingservice.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String goodName;          // nom de la séance / good name
    private LocalDate seanceDate;     // date de la séance
    private LocalTime seanceTime;     // heure de la séance

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public LocalDate getSeanceDate() {
        return seanceDate;
    }

    public void setSeanceDate(LocalDate seanceDate) {
        this.seanceDate = seanceDate;
    }

    public LocalTime getSeanceTime() {
        return seanceTime;
    }

    public void setSeanceTime(LocalTime seanceTime) {
        this.seanceTime = seanceTime;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }




}
